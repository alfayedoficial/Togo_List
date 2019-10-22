package com.group4.togolist.viewmodel;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.widget.Toast;

import androidx.lifecycle.ViewModel;

import com.group4.togolist.repository.DatabaseHandler;
import com.group4.togolist.model.Trip;
import com.group4.togolist.repository.TripAlarm;
import com.group4.togolist.util.FloatingWidgetService;
import com.group4.togolist.view.AddFormActivity;
import com.group4.togolist.view.DetailsTripActivity;
import com.group4.togolist.view.HomeActivity;
import com.group4.togolist.view.ProfileActivity;

import java.util.Calendar;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

/**
 * Class created to handle Details Activity
 */

public class DetailsTripViewModel extends ViewModel {
    private DetailsTripActivity activity;
    private DatabaseHandler databaseHandler;
    private Trip currentTrip;
    private static final int DRAW_OVER_OTHER_APP_PERMISSION = 123;
    public static final String TRIP_NAME_TAG = "tripName";
    public static final String TRIP_NOTES_TAG = "tripNotes";

    /**
     * Class Constructor
     */
    public DetailsTripViewModel(DetailsTripActivity activity){
        this.activity = activity;
        String tripName = activity.getIntent().getExtras().getString(HomeViewModel.TRIP_NAME);
        databaseHandler = new DatabaseHandler(activity);
        try {
            currentTrip = databaseHandler.getTripByName(tripName);
            this.activity.setTripDetails(currentTrip);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // need to set Data in details form using currentTrip Data
    }

    public void startTrip(){
        String uri = String.format(Locale.ENGLISH, "geo:%f,%f", currentTrip.getEndLocationLatitude(), currentTrip.getEndLocationLongitude());
        Intent tripIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
        tripIntent.setPackage("com.google.android.apps.maps");
        activity.startActivity(tripIntent);
        currentTrip.setStatus(Trip.ENDED);
        databaseHandler.updateTrip(currentTrip);
        startFloatingWidgetService();
    }

    public void deleteTrip(){
        databaseHandler.deleteTrip(currentTrip);
        activity.startActivity(new Intent(activity, HomeActivity.class));
    }

    public void editTrip(String tripName, Calendar startDate, String notes){
        currentTrip.setTripName(tripName);
//        currentTrip.setStartLocationLongitude(startLocationLongitude);
//        currentTrip.setStartLocationLatitude(startLocationLatitude);
//        currentTrip.setEndLocationLongitude(endLocationLongitude);
//        currentTrip.setEndLocationLatitude(endLocationLatitude);
        currentTrip.setStartTime(startDate);
//        currentTrip.setRepetition(repetition);
//        currentTrip.setRoundTrip(isRoundTrip);
        currentTrip.setNotes(notes);
        databaseHandler.updateTrip(currentTrip);
        startAlarm(startDate);
    }


    private void startAlarm(Calendar c) {
        AlarmManager alarmManager = (AlarmManager) activity.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(activity, TripAlarm.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(activity, currentTrip.getId(), intent, PendingIntent.FLAG_UPDATE_CURRENT);

        if (c.before(Calendar.getInstance())) {
            c.add(Calendar.DATE, 1);
            Toast.makeText(activity, "Please Check your Starting Time", Toast.LENGTH_SHORT).show();
        }else {
            c.set(Calendar.MONTH, c.get(Calendar.MONTH) - 1);
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pendingIntent);
        }
    }

    private void cancelAlarm() {
        AlarmManager alarmManager = (AlarmManager) activity.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(activity, TripAlarm.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(activity, currentTrip.getId(), intent, PendingIntent.FLAG_CANCEL_CURRENT);

        alarmManager.cancel(pendingIntent);
    }

    /**
     * SnakeBarHandler
     */
    public void goToHome(){
        activity.startActivity(new Intent(activity, HomeActivity.class));
    }

    public void goToProfile(){
        activity.startActivity(new Intent(activity, ProfileActivity.class));
    }

    public void goToAddForm(){
        activity.startActivity(new Intent(activity, AddFormActivity.class));
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
}
