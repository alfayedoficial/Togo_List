package com.group4.togolist.repository;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.group4.togolist.view.DialogActivity;

public class TripAlarm extends BroadcastReceiver {

    public static final String TRIP_NAME = "TripName";

    @Override
    public void onReceive(Context context, Intent intent) {
        String tripName = intent.getExtras().getString(TRIP_NAME);
        Intent dialogIntent = new Intent(context, DialogActivity.class);
        dialogIntent.putExtra(TRIP_NAME,tripName);
        context.startActivity(dialogIntent);
    }
}
