package com.example.a8ld;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GestureDetectorCompat;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new MyView(this));
    }

    public class MyView extends View {

        private GestureDetectorCompat Detector;
        private ScaleGestureDetector ScaleDetector;
        private float mScaleFactor = 1.f;
        private int x;
        private int y;
        private int shape;
        private int radius;

        public MyView(Context context) {
            super(context);
            ScaleDetector = new ScaleGestureDetector(context, new ScaleListener());
            Detector = new GestureDetectorCompat(context, new MyGestureListener());
        }

        @Override
        public boolean onTouchEvent(MotionEvent ev) {
            ScaleDetector.onTouchEvent(ev);
            Detector.onTouchEvent(ev);
            return true;
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);

            Paint paint = new Paint();
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(Color.WHITE);
            canvas.drawPaint(paint);
            paint.setColor(Color.parseColor("#da4747"));

            switch (shape) {
                case 0:
                    canvas.drawRect(x - radius, y - radius, x + radius, y + radius, paint);
                    break;
                case 1:
                    Path path = new Path();
                    path.setFillType(Path.FillType.EVEN_ODD);
                    path.moveTo(x - radius, y - radius);
                    path.lineTo(x, y + radius);
                    path.lineTo(x + radius, y - radius);
                    path.lineTo(x - radius, y - radius);
                    path.close();

                    paint.setStyle(Paint.Style.FILL);

                    canvas.drawPath(path, paint);
                    break;
                case 2:
                    canvas.drawCircle(x, y, radius, paint);
                    break;
                case 3:
                    canvas.drawCircle(getWidth() / 2, getHeight() / 2, radius * mScaleFactor, paint);
                    break;
            }
        }

        private class ScaleListener
                extends ScaleGestureDetector.SimpleOnScaleGestureListener {
            @Override
            public boolean onScale(ScaleGestureDetector detector) {
                mScaleFactor *= detector.getScaleFactor();

                // Don't let the object get too small or too large.
                mScaleFactor = Math.max(0.1f, Math.min(mScaleFactor, 5.0f));

                shape = 3;

                invalidate();
                return true;
            }
        }

        private class MyGestureListener extends GestureDetector.SimpleOnGestureListener {

            private static final int SWIPE_DISTANCE_THRESHOLD = 100;
            private static final int SWIPE_VELOCITY_THRESHOLD = 100;

            @Override
            public boolean onDown(MotionEvent event) {
                return true;
            }

            @Override
            public boolean onFling(MotionEvent event1, MotionEvent event2,
                                   float velocityX, float velocityY) {
                float distanceX = event2.getX() - event1.getX();
                float distanceY = event2.getY() - event1.getY();
                if (Math.abs(distanceX) > Math.abs(distanceY) && Math.abs(distanceX) > SWIPE_DISTANCE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                    if (distanceX > 0) {
                        onSwipeRight();
                    } else {
                        onSwipeLeft();
                    }
                    return true;
                }
                return false;
            }

            public void onSwipeLeft() {
                DisplayMetrics metrics = new DisplayMetrics();
                getWindowManager().getDefaultDisplay().getMetrics(metrics);

                int xSize = metrics.widthPixels;
                int ySize = metrics.heightPixels;

                int min = 1;

                int max = xSize;
                x = new Random().nextInt((max - min) + 1) + min;

                max = ySize;
                y = new Random().nextInt((max - min) + 1) + min;

                shape = new Random().nextInt(3);

                radius = new Random().nextInt((200 - 50) + 1) + 50;
                invalidate();

            }

            public void onSwipeRight() {
            }
        }

    }
}

