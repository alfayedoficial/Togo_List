package com.group4.togolist.viewmodel;

import android.annotation.TargetApi;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.lifecycle.ViewModel;

import com.group4.togolist.R;
import com.group4.togolist.repository.DatabaseHandler;
import com.group4.togolist.model.Trip;
import com.group4.togolist.repository.TripAlarm;
import com.group4.togolist.view.DetailsTripActivity;
import com.group4.togolist.view.DialogActivity;
import com.group4.togolist.view.HomeActivity;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

public class DialogViewModel extends ViewModel {

    private String textTitle;
    private String textContent;
    Trip currentTrip;

    DatabaseHandler databaseHandler;

    DialogActivity activity;

    /**
     * Class Consturcotr
     */
    public DialogViewModel(DialogActivity activity){
        this.activity = activity;
        databaseHandler = new DatabaseHandler(activity);
        String tripName = activity.getIntent().getExtras().getString(TripAlarm.TRIP_NAME);
        try {
            currentTrip = databaseHandler.getTripByName(tripName);
            Geocoder geocoder = new Geocoder(activity, Locale.getDefault());
            List<Address> address = geocoder.getFromLocation(currentTrip.getEndLocationLatitude(),currentTrip.getEndLocationLongitude(),1);
            String tripLocation =address.get(0).getAddressLine(0);
            activity.setDialogTripData(currentTrip.getTripName(),tripLocation,currentTrip.getTripDate(),currentTrip.getTripTime());

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Send Dialog to Details Trip Activity
     */
    public void showDetails(){
        Intent intent = new Intent(activity, DetailsTripActivity.class);
        intent.putExtra(HomeViewModel.TRIP_NAME,currentTrip.getTripName());
        activity.startActivity(intent);
        activity.finish();
    }

    /**
     *  Set Dialog to Notification Bar
     */
    public void waitForLater(){
        NotificationHelper notificationHelper = new NotificationHelper(activity);
        NotificationCompat.Builder nb = notificationHelper.getChannelNotification();
        notificationHelper.getManager().notify(1, nb.build());
        activity.finish();
    }

    /**
     * sent Activity to Google Maps with Location
     * still need to open notes on widget
     */
    public void startTrip(){
        // Create a String Uri Use the result to create an Intent.
        String uri = String.format(Locale.ENGLISH, "geo:%f,%f", currentTrip.getEndLocationLatitude(), currentTrip.getEndLocationLongitude());
        // Create an Intent from uri String. Set the action to ACTION_VIEW
        Intent tripIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
        // Make the Intent explicit by setting the Google Maps package
        tripIntent.setPackage("com.google.android.apps.maps");
        currentTrip.setStatus(Trip.ENDED);
        // Attempt to start an activity that can handle the Intent
        activity.startActivity(tripIntent);
        activity.finish();
    }

    /**
     * Cancel Trip and finish Dialog Activity
     */
    public void cancelTrip(){
        currentTrip.setStatus(Trip.CANCELED);
        databaseHandler.updateTrip(currentTrip);
        activity.finish();
    }

    /**
     * Class Created to handle the creation of Notification
     */

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
