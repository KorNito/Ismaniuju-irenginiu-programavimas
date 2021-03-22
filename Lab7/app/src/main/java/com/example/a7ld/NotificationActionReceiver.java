package com.example.a7ld;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class NotificationActionReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String intentAction = intent.getAction();
        String message = "";
        if(intentAction == Intent.ACTION_BATTERY_LOW) {
            message = "Battery low";
        }
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}
