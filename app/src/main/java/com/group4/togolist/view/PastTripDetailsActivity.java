package com.group4.togolist.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.group4.togolist.R;
import com.group4.togolist.viewmodel.DetailsTripViewModel;
import com.group4.togolist.viewmodel.PastTripsDetailsViewModel;

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

        pastTripsDetailsViewModel = ViewModelProviders.of(this , new MyViewModelFactory(PastTripDetailsActivity.this)).get(PastTripsDetailsViewModel.class);

        initComponent();
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
