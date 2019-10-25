package com.group4.togolist.util;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.app.TaskStackBuilder;

import com.group4.togolist.R;
import com.group4.togolist.model.Trip;
import com.group4.togolist.repository.TripAlarm;
import com.group4.togolist.view.activities.DialogActivity;

public class NotificationHelper extends ContextWrapper {
    public static final String channelID = "channelID";
    public static final String channelName = "Channel Name";

    private NotificationManager mManager;
    private Trip currentTrip;
    Context context;
    public NotificationHelper(Context base, Trip currentTrip) {
        super(base);
        this.context = base;
        this.currentTrip = currentTrip;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createChannel();
        }
    }

    @TargetApi(Build.VERSION_CODES.O)
    private void createChannel() {
        NotificationChannel channel = new NotificationChannel(channelID, channelName, NotificationManager.IMPORTANCE_HIGH);

        getManager().createNotificationChannel(channel);
    }

    public NotificationManager getManager() {
        if (mManager == null) {
            mManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }

        return mManager;
    }

    public NotificationCompat.Builder getChannelNotification() {

        Intent notificationIntent = new Intent(getApplicationContext(), DialogActivity. class ) ;
        notificationIntent.putExtra(TripAlarm.TRIP_NAME,currentTrip.getTripName());
        notificationIntent.putExtra( "NotificationMessage" , currentTrip.getTripName() ) ;
        notificationIntent.addCategory(Intent. CATEGORY_LAUNCHER ) ;
        notificationIntent.setAction(Intent. ACTION_MAIN ) ;
        notificationIntent.setFlags(Intent. FLAG_ACTIVITY_CLEAR_TOP | Intent. FLAG_ACTIVITY_SINGLE_TOP ) ;
        PendingIntent resultIntent = PendingIntent. getActivity (getApplicationContext(), currentTrip.getId() , notificationIntent , 0 ) ;


        return new NotificationCompat.Builder
                (getApplicationContext(), channelID )
                .setSmallIcon(R.mipmap.ic_launcher_round )
                .setContentTitle( currentTrip.getTripName() )
                .setContentText( "Remember you Trip" )
                .setContentIntent(resultIntent) ;
    }
}
