package com.group4.togolist.viewmodel;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.lifecycle.ViewModel;

import com.group4.togolist.repository.DatabaseHandler;
import com.group4.togolist.model.Trip;
import com.group4.togolist.util.TripAlarm;
import com.group4.togolist.util.FloatingWidgetService;
import com.group4.togolist.util.MapDirectionHelper;
import com.group4.togolist.util.MediaplayerHelper;
import com.group4.togolist.util.NotificationHelper;
import com.group4.togolist.view.activities.DetailsTripActivity;
import com.group4.togolist.view.activities.DialogActivity;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

public class DialogViewModel extends ViewModel {

    private String textTitle;
    private String textContent;
    private Trip currentTrip;
    private static final int DRAW_OVER_OTHER_APP_PERMISSION = 123;
    public static final String TRIP_NAME_TAG = "tripName";
    public static final String TRIP_NOTES_TAG = "tripNotes";

    private DatabaseHandler databaseHandler;
    private DialogActivity activity;
    private MediaplayerHelper mediaplayerHelper;


    /**
     * Class Consturcotr
     */
    public DialogViewModel(DialogActivity activity){

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
        mediaplayerHelper = new MediaplayerHelper(activity);
        mediaplayerHelper.playAlarm();
        Geocoder geocoder = new Geocoder(activity, Locale.getDefault());
        List<Address> address = null;
        try {
            address = geocoder.getFromLocation(currentTrip.getEndLocationLatitude(), currentTrip.getEndLocationLongitude(), 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String tripLocation = "";
        if(address.get(0).getAddressLine(0) != null) {
            tripLocation = address.get(0).getAddressLine(0);
        }
            activity.setDialogTripData(currentTrip.getTripName(), tripLocation, currentTrip.getTripDate(), currentTrip.getTripTime());

    }

    /**
     * Send Dialog to Details Trip Activity
     */
    public void showDetails(){
        mediaplayerHelper.releaseMediaPlayer();
        Intent intent = new Intent(activity, DetailsTripActivity.class);
        intent.putExtra(HomeViewModel.TRIP_NAME,currentTrip.getTripName());
        activity.startActivity(intent);
        activity.finish();
    }

    /**
     *  Set Dialog to Notification Bar
     */
    public void waitForLater(){
        mediaplayerHelper.releaseMediaPlayer();
        NotificationHelper notificationHelper = new NotificationHelper(activity,currentTrip);
        NotificationCompat.Builder nb = notificationHelper.getChannelNotification();
        notificationHelper.getManager().notify(1, nb.build());
        activity.finish();
    }

    /**
     * sent Activity to Google Maps with Location
     * still need to open notes on widget
     */
    public void startTrip(){
        mediaplayerHelper.releaseMediaPlayer();
        new MapDirectionHelper(activity,currentTrip.getStartLocationLatitude()
                ,currentTrip.getStartLocationLongitude()
                ,currentTrip.getEndLocationLatitude()
                ,currentTrip.getEndLocationLongitude())
                .startNavigation();
        startFloatingWidgetService();
        currentTrip.setStatus(Trip.ENDED);
        databaseHandler.updateTrip(currentTrip);
        activity.finish();
    }

    /**
     * Cancel Trip and finish Dialog Activity
     */
    public void cancelTrip(){
        currentTrip.setStatus(Trip.ENDED);
        databaseHandler.updateTrip(currentTrip);
        activity.finish();
    }

    public void askForSystemOverlayPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(activity)) {

            //If the draw over permission is not available to open the settings screen
            //to grant the permission.
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:" + activity.getPackageName()));
            activity.startActivityForResult(intent, DRAW_OVER_OTHER_APP_PERMISSION);
        }
    }

    public void startFloatingWidgetService(){
        Intent intent = new Intent(activity, FloatingWidgetService.class);
        intent.putExtra(TRIP_NAME_TAG,currentTrip.getTripName());
        intent.putExtra(TRIP_NOTES_TAG,currentTrip.getNotes());

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            activity.startService(intent);
            activity.finish();
        } else if (Settings.canDrawOverlays(activity)) {
            activity.startService(intent);
            activity.finish();
        } else {
            askForSystemOverlayPermission();
            Toast.makeText(activity, "You need System Alert Window Permission to do this", Toast.LENGTH_SHORT).show();
        }
    }

    public void releaseMediaPlayer(){
        mediaplayerHelper.releaseMediaPlayer();
    }

}
