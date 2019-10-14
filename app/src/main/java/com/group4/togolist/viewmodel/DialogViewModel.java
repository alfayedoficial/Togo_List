package com.group4.togolist.viewmodel;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.lifecycle.ViewModel;

import com.group4.togolist.R;
import com.group4.togolist.db.DatabaseHandler;
import com.group4.togolist.model.Trip;
import com.group4.togolist.repository.TripAlarm;

import java.util.Locale;
import java.util.concurrent.ExecutionException;

public class DialogViewModel extends ViewModel {

    private String textTitle;
    private String textContent;
    Trip currentTrip;

    DatabaseHandler databaseHandler;

    Activity activity;
    public DialogViewModel(Activity activity){
        this.activity = activity;
        databaseHandler = new DatabaseHandler(activity);
        String tripName = activity.getIntent().getExtras().getString(TripAlarm.TRIP_NAME);
        try {
            currentTrip = databaseHandler.getTripByName(tripName);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void showDetails(){

    }

    public void waitForLater(){
        NotificationHelper notificationHelper = new NotificationHelper(activity);
        NotificationCompat.Builder nb = notificationHelper.getChannelNotification();
        notificationHelper.getManager().notify(1, nb.build());
    }

    public void startTrip(){
        // Create a String Uri Use the result to create an Intent.
        String uri = String.format(Locale.ENGLISH, "geo:%f,%f", currentTrip.getEndLocationLatitude(), currentTrip.getEndLocationLongitude());
        // Create an Intent from uri String. Set the action to ACTION_VIEW
        Intent tripIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
        // Make the Intent explicit by setting the Google Maps package
        tripIntent.setPackage("com.google.android.apps.maps");

        // Attempt to start an activity that can handle the Intent
        activity.startActivity(tripIntent);
    }

    public void cancelTrip(){
        currentTrip.setStatus(Trip.CANCELED);
        databaseHandler.updateTrip(currentTrip);
        activity.finish();
    }

    class NotificationHelper extends ContextWrapper {
        public static final String channelID = "channelID";
        public static final String channelName = "Channel Name";

        private NotificationManager mManager;

        public NotificationHelper(Context base) {
            super(base);
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
            return new NotificationCompat.Builder(getApplicationContext(), channelID)
                    .setContentTitle(getString(R.string.app_name))
                    .setContentText(currentTrip.getTripName())
                    .setSmallIcon(R.drawable.logo);
        }
    }
}
