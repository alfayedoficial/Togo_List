package com.group4.togolist.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;
import android.util.Log;

import com.group4.togolist.view.activities.DialogActivity;

public class TripAlarm extends BroadcastReceiver {

    public static final String TRIP_NAME = "TripName";

    /**
     *
     * @param context
     * @param intent
     */
    @Override
    public void onReceive(Context context, Intent intent) {

        String tripName = intent.getExtras().getString(TRIP_NAME);
        Intent dialogIntent = new Intent(context, DialogActivity.class);
        dialogIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Log.i("BrodcastReceiver","Recieve Request");
        dialogIntent.putExtra(TRIP_NAME,tripName);
        context.startActivity(dialogIntent);
    }
}
