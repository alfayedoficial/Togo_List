package com.group4.togolist.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.group4.togolist.R;
import com.group4.togolist.model.Trip;
import com.group4.togolist.viewmodel.DetailsTripViewModel;
import com.group4.togolist.viewmodel.PastTripsDetailsViewModel;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class PastTripDetailsActivity extends AppCompatActivity  implements View.OnClickListener {

    private ImageButton imgBtnHome , imgBtnProfile;
    private com.google.android.material.floatingactionbutton.FloatingActionButton fltBtnAdd;
    private PastTripsDetailsViewModel pastTripsDetailsViewModel;

    private TextView txtTripName  , txtStartDate , txtStartTime , txtNotes;
    private TextView   txtRepetition , txtTripType , txtStartPoint , txtEndPoint ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_past_trip_details);
        initComponent();

        pastTripsDetailsViewModel = ViewModelProviders.of(this , new MyViewModelFactory(PastTripDetailsActivity.this)).get(PastTripsDetailsViewModel.class);

    }

    private void initComponent() {

        txtTripName = findViewById(R.id.textViewPTDStatusNameTrip);
        txtStartPoint = findViewById(R.id.textViewPTDStatusStartPoint);
        txtEndPoint = findViewById(R.id.textViewPTDStatusEndPoint);

        txtStartDate = findViewById(R.id.textViewPTDStatusStartDate);
        txtStartTime = findViewById(R.id.textViewPTDStatusStartTime);

        txtRepetition = findViewById(R.id.textViewPTDStatusRepetition);
        txtTripType = findViewById(R.id.textViewPTDStatusTripType);

        txtNotes = findViewById(R.id.textViewPTDStatusNote);;

        imgBtnHome = findViewById(R.id.imageBtnHome);
        imgBtnProfile = findViewById(R.id.imageBtnProfile);
        fltBtnAdd = findViewById(R.id.fABtnAddNote);


        imgBtnHome.setOnClickListener(this);
        imgBtnProfile.setOnClickListener(this);
        fltBtnAdd.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.imageBtnHome:
                pastTripsDetailsViewModel.goToHome();
                break;
            case R.id.imageBtnProfile:
                pastTripsDetailsViewModel.goToProfile();
                break;
            case R.id.fABtnAddNote:
                pastTripsDetailsViewModel.goToAddForm();
                break;
        }
    }


    public  void setTripDetails(Trip trip){

        txtTripName.setText(trip.getTripName());
        String repetitionString = "" ;

        switch (trip.getRepetition()){
            case Trip.NOT_REPEATED:
                repetitionString   = "NOT_REPEATED" ;
                break;

            case Trip.DAILY:
                repetitionString = "DAILY";
                break;
            case Trip.WEEKLY:
                repetitionString = "WEEKLY";
                break;
            case Trip.MONTHLY:
                break;
        }
        txtRepetition.setText(repetitionString);

        if (trip.isRoundTrip()){
            txtTripType.setText("Round Trip");
        }else
            txtTripType.setText("One Direction");

        txtStartPoint.setText(getTripPlace(trip.getStartLocationLatitude() , trip.getStartLocationLongitude()));
        txtEndPoint.setText(getTripPlace(trip.getEndLocationLatitude() , trip.getEndLocationLongitude()));

        txtStartDate.setText(trip.getTripDate());
        txtStartTime.setText(trip.getTripTime());
        txtNotes.setText(trip.getNotes());
    }


    private String getTripPlace( double latitude , double longitude) {
        String tripLocation = "";
        try {
            Geocoder geocoder = new Geocoder(this, Locale.getDefault());
            List<Address> address = geocoder.getFromLocation(latitude, longitude, 1);
            tripLocation = address.get(0).getAddressLine(0);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return tripLocation;
    }


    /**
     *
     */
    class MyViewModelFactory implements ViewModelProvider.Factory {
        private PastTripDetailsActivity mActivity;


        public MyViewModelFactory(PastTripDetailsActivity activity) {
            mActivity = activity;
        }


        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            return (T) new PastTripsDetailsViewModel(mActivity);
        }
    }
}
