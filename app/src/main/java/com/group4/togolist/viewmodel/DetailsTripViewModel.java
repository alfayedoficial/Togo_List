package com.group4.togolist.viewmodel;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.ViewModel;

import com.group4.togolist.R;
import com.group4.togolist.repository.DatabaseHandler;
import com.group4.togolist.model.Trip;
import com.group4.togolist.repository.TripAlarm;
import com.group4.togolist.util.FloatingWidgetService;
import com.group4.togolist.util.MapDirectionHelper;
import com.group4.togolist.view.activities.AddFormActivity;
import com.group4.togolist.view.activities.DetailsTripActivity;
import com.group4.togolist.view.activities.HomeActivity;
import com.group4.togolist.view.activities.ProfileActivity;
import com.shashank.sony.fancytoastlib.FancyToast;

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
        cancelAlarm();
        currentTrip.setStatus(Trip.ENDED);
        databaseHandler.updateTrip(currentTrip);
        new MapDirectionHelper(activity,currentTrip.getStartLocationLatitude()
                ,currentTrip.getStartLocationLongitude()
                ,currentTrip.getEndLocationLatitude()
                ,currentTrip.getEndLocationLongitude())
                .startNavigation();
        startFloatingWidgetService();
    }

    public void deleteTrip(){
        databaseHandler.deleteTrip(currentTrip);
        activity.startActivity(new Intent(activity, HomeActivity.class));
    }

    public void editTrip(String tripName, Calendar startDate, String notes){
        Log.i("edit",Calendar.getInstance().getTime().toString());
        Log.i("edit",startDate.getTime().toString());
        currentTrip.setTripName(tripName);
        currentTrip.setStartTime(startDate);
        startDate.set(Calendar.MONTH,startDate.get(Calendar.MONTH)-1);
        startDate.set(Calendar.SECOND,0);
        currentTrip.setNotes(notes);
        setAlarm(currentTrip);
    }


    public void setAlarm(Trip newTrip){


        Calendar startDate = newTrip.getStartTime();
        Log.i("trip date",newTrip.getStartTime().getTime().toString());
        startDate.set(Calendar.MONTH,startDate.get(Calendar.MONTH)-1);
        if(startDate.after(Calendar.getInstance())) {
            databaseHandler.updateTrip(newTrip);
            AlarmManager alarmManager = (AlarmManager) activity.getSystemService(Context.ALARM_SERVICE);
            Intent intent = new Intent(activity, TripAlarm.class);
            intent.putExtra(TripAlarm.TRIP_NAME, newTrip.getTripName());
            PendingIntent pendingIntent = PendingIntent.getBroadcast(activity, newTrip.getId(), intent, 0);
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, startDate.getTimeInMillis(), pendingIntent);
            activity.startActivity(new Intent(activity, HomeActivity.class));
        }
        else {
            FancyToast.makeText(activity,activity.getString(R.string.wrong_start_time),FancyToast.LENGTH_SHORT,FancyToast.ERROR,true).show();
            try {
                newTrip = databaseHandler.getTripByName(newTrip.getTripName());
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            activity.setTripDetails(newTrip);
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
