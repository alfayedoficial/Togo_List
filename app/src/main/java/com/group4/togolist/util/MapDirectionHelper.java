package com.group4.togolist.util;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

import java.util.Locale;

public class MapDirectionHelper {

    private Activity activity;
    private Intent tripIntent;
    public MapDirectionHelper(Activity activity, double startLatitude, double startLongitude, double destLatitude, double destLongitude){
        this.activity = activity;
        String uri = String.format(Locale.ENGLISH, "http://maps.google.com/maps?saddr=%f,%f&daddr=%f,%f",
                startLatitude,startLongitude,
                destLatitude,destLongitude);
        // Create an Intent from uri String. Set the action to ACTION_VIEW
        tripIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
        // Make the Intent explicit by setting the Google Maps package
        tripIntent.setPackage("com.google.android.apps.maps");

    }

    public void startNavigation(){
        activity.startActivity(tripIntent);
    }
}
