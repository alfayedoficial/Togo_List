package com.group4.togolist.view;

import android.graphics.Color;
import android.os.Bundle;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.model.LatLng;
import com.group4.togolist.R;

import androidx.appcompat.app.AppCompatActivity;

public class AddFormActivity extends AppCompatActivity {
    LatLng latLang1;
    LatLng latLang2;
    Double lat1;
    Double lat2;
    Double long2;
    Double long1;
    String placeDestination;
    String placeName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_form);

        final PlaceAutocompleteFragment autocompleteFragment1 = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.editText_startPoint);
        autocompleteFragment1.getView().setBackgroundColor(getResources().getColor(R.color.background_offwhite));
//        autocompleteFragment1.setText("Start Point");
        autocompleteFragment1.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                latLang1 = place.getLatLng();
                placeName = (String) place.getName();
                long1 = latLang1.longitude;
                lat1 = latLang1.latitude;
            }

            @Override
            public void onError(Status status) {

                final PlaceAutocompleteFragment autocompleteFragment2 = (PlaceAutocompleteFragment)
                        getFragmentManager().findFragmentById(R.id.editText_endPoint);
                autocompleteFragment2.getView().setBackgroundColor(getResources().getColor(R.color.background_offwhite));
//                autocompleteFragment2.setText("End Point");
                autocompleteFragment2.setOnPlaceSelectedListener(new PlaceSelectionListener() {
                    @Override
                    public void onPlaceSelected(Place place) {
                        // TODO: Get info about the selected place.
                        latLang2 = place.getLatLng();
                        placeDestination = (String) place.getName();
                        long2 = latLang2.longitude;
                        lat2 = latLang2.latitude;

                    }

                    @Override
                    public void onError(Status status) {

                    }
                });
            }
        });
    }

}
