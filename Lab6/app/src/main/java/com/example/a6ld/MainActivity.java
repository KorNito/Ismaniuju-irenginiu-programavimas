package com.example.a6ld;

import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;

import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Timer;

public class MainActivity extends AppCompatActivity {

    public TextView firstTextView;
    public TextView secondTextView2;
    public TextView thirdTextView3;
    public TextView selectedMenuItem;
    public Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        firstTextView = findViewById(R.id.textView1);
        secondTextView2 = findViewById(R.id.textView2);
        thirdTextView3 = findViewById(R.id.textView3);

        setSupportActionBar(toolbar);
        registerForContextMenu(firstTextView);
        registerForContextMenu(secondTextView2);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_context, menu);
        selectedMenuItem = (TextView)v;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        String text = (String) selectedMenuItem.getText();
        switch (item.getItemId()) {
            case R.id.action_symbol_count:
                String message = "Teskte yra " + text.replace(" ", "").length() + " simbolių";
                showAlert(getString(R.string.alert_title_count), message);
                secondTextView2.setText(message);
                return true;
            case R.id.action_symbol_spelling:
                printLettersThread(text);
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_exit:
                this.finish();
                System.exit(0);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onClickShowPicker(MenuItem item) {
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getSupportFragmentManager(), getString(R.string.timepicker));
    }

    public void processTimePickerResult(int selectedHour, int selectedMinute) {
        final Calendar c = Calendar.getInstance();
        int currentHour = c.get(Calendar.HOUR_OF_DAY);
        int currentMinute = c.get(Calendar.MINUTE);

        int timeDifference = (selectedHour * 60 + selectedMinute) - (currentHour * 60 + currentMinute);

        String message = "Skirtumas tarp dabar ir nurodyto laiko yra " + timeDifference + " minutes";

        firstTextView.setText(message);
        showAlert(getString(R.string.alert_title_picker), message);
    }

    public void showAlert (String title, String message) {
        AlertDialog.Builder myAlertBuilder = new AlertDialog.Builder(MainActivity.this);
        myAlertBuilder.setTitle(title);
        myAlertBuilder.setMessage(message);
        myAlertBuilder.show();
    }

    public void printLettersThread(final String text) {
        Thread th = new Thread(new Runnable() {
            public void run() {
                for (int i=0; i < text.length(); i++) {
                    final int letterIndex = i;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            char letter = text.charAt(letterIndex);
                            thirdTextView3.setText(String.valueOf(letter));
                        }
                    });
                    try {
                        Thread.sleep(1000);
                    }
                    catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        th.start();
    }
}