package com.group4.togolist.view.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;

import com.group4.togolist.R;
import com.group4.togolist.model.Trip;
import com.group4.togolist.viewmodel.DetailsTripViewModel;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class DetailsTripActivity extends AppCompatActivity implements View.OnClickListener , View.OnFocusChangeListener {


    private EditText eTxtTripName  , eTxtStartDate , eTxtStartTime , eTxtNotes;
    private Button btnEdit , btnStart , btnDelete ;
    private TextView txtStatus , txtRepetition , txtTripType , txtStartPoint , txtEndPoint ;
    private DetailsTripViewModel detailsTripViewModel;
    private ImageButton imgBtnHome , imgBtnProfile;
    private com.google.android.material.floatingactionbutton.FloatingActionButton fltBtnAdd;
    private String strMinute = "";
    private String strHour = "";
    private boolean editBtnEnable = true ;
;
    private    final Calendar myCalendar = Calendar.getInstance();
    private Calendar tripCalendar = Calendar.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_trip);

        initComponent();

        detailsTripViewModel = ViewModelProviders.of(this , new MyViewModelFactory(DetailsTripActivity.this)).get(DetailsTripViewModel.class);
        detailsTripViewModel.askForSystemOverlayPermission();
        myCalendar.setTimeInMillis(System.currentTimeMillis());

    }


    private void initComponent() {

        eTxtTripName = findViewById(R.id.editText_nameTrip2);
        eTxtStartDate = findViewById(R.id.editTextStatusStartDate);
        eTxtStartTime = findViewById(R.id.editTextStatusStartTime);
        eTxtNotes = findViewById(R.id.editTextStatusNote);

        setEditTextEnablity(false);

        txtStatus = findViewById(R.id.textViewStatusShow);


        txtRepetition = findViewById(R.id.TextViewStatusRepetitionShow);
        txtTripType = findViewById(R.id.textViewStatusTripTypeShow);

        txtStartPoint = findViewById(R.id.textViewStatusStartPoint);
        txtEndPoint = findViewById(R.id.textViewStatusEndPoint);

        btnEdit = findViewById(R.id.btnStatusLater);
        btnStart = findViewById(R.id.btnStatusStart);
        btnDelete = findViewById(R.id.btnStatusDelete);



        imgBtnHome = findViewById(R.id.imageBtnHome);
        imgBtnProfile = findViewById(R.id.imageBtnProfile);
        fltBtnAdd = findViewById(R.id.fABtnAddNote);

        eTxtStartDate.setOnFocusChangeListener(this);
        eTxtStartTime.setOnFocusChangeListener(this);

        eTxtStartDate.setOnClickListener(this);
        eTxtStartTime.setOnClickListener(this);

        btnEdit.setOnClickListener(this);
        btnStart.setOnClickListener(this);
        btnDelete.setOnClickListener(this);

        imgBtnHome.setOnClickListener(this);
        imgBtnProfile.setOnClickListener(this);
        fltBtnAdd.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnStatusLater:  // edit Button
                if (editBtnEnable){
                    setEditTextEnablity(true);
                    btnEdit.setText(R.string.save);
                    editBtnEnable = false ;
                }else {
                    setEditTextEnablity(false);
                    btnEdit.setText(R.string.edit);
                    editBtnEnable = true ;

                    setUpCalender();
                   detailsTripViewModel.editTrip( eTxtTripName.getText().toString() , tripCalendar , eTxtNotes.getText().toString());
                }

                break;
            case R.id.btnStatusStart:
                detailsTripViewModel.startTrip();
                break;
            case R.id.btnStatusDelete:
                 deleteTrip();

                break;

            case R.id.imageBtnHome:
                detailsTripViewModel.goToHome();
                break;
            case R.id.imageBtnProfile:
                detailsTripViewModel.goToProfile();
                break;
            case R.id.fABtnAddNote:
                detailsTripViewModel.goToAddForm();
                break;
            case R.id.editTextStatusStartDate:
                if (! editBtnEnable){
                    showDate();
                }

                break;
            case R.id.editTextStatusStartTime:
                if (! editBtnEnable) {
                    showTime();
                }
                break;
        }
    }

    private void deleteTrip(){
        AlertDialog.Builder builder = new AlertDialog.Builder(
                DetailsTripActivity.this);
        // set message
        builder.setTitle(R.string.titledelete);
        builder.setMessage(R.string.messagedelete);
        builder.setCancelable(false);
        builder.setPositiveButton(R.string.yes,
                new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog,
                                        int which) {
                        detailsTripViewModel.deleteTrip();
                    }
                });

        builder.setNegativeButton(R.string.no,
                new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog,
                                        int which) {
                        // TODO Auto-generated method stub
                        // close the dialog box
                        dialog.cancel();
                    }
                });

        //create instance of alert dialogand assign configuration of
        //builderto alert dialog instance

        AlertDialog alert = builder.create();
        alert.setCanceledOnTouchOutside(false);
        // Show Alert Dialog
        alert.show();
    }

    private void setUpCalender() {
        int month = 0;
        int day = 0;
        int year = 0;
        int hour = 0;
        int minute  =0 ;

        String startDate = eTxtStartDate.getText().toString();
        String startTime = eTxtStartTime.getText().toString();

        /**
         *  getting date and time from editText and split them to the required data
         */
        if (startDate != null ) {
            if(!startDate.isEmpty()) {
                String[] startDateArr = startDate.split("/");
                month = Integer.parseInt(startDateArr[0]);
                day = Integer.parseInt(startDateArr[1]);
                year = Integer.parseInt(startDateArr[2]);
            }
        }
        if (startTime != null){
            if (!startTime.isEmpty()) {
                String[] startTimeArr = startTime.split(":");
                hour = Integer.parseInt(startTimeArr[0]);
                minute = Integer.parseInt(startTimeArr[1]);
            }
        }

        tripCalendar.set(year , month , day , hour , minute);
    }


    public  void setTripDetails(Trip trip){

        eTxtTripName.setText(trip.getTripName());
        txtStatus.setText(trip.getStatus());

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
            txtTripType.setText(getString(R.string.round_trip));
        }else
            txtTripType.setText(getString(R.string.one_direction));

        txtStartPoint.setText(getTripPlace(trip.getStartLocationLatitude() , trip.getStartLocationLongitude()));
        txtEndPoint.setText(getTripPlace(trip.getEndLocationLatitude() , trip.getEndLocationLongitude()));

        eTxtStartDate.setText(trip.getTripDate());
        eTxtStartTime.setText(trip.getTripTime());
        eTxtNotes.setText(trip.getNotes());
    }


    private String getTripPlace( double latitude , double logitude) {
        String tripLocation = "";
        try {
            Geocoder geocoder = new Geocoder(this, Locale.getDefault());
            List<Address> address = geocoder.getFromLocation(latitude, logitude, 1);
            tripLocation = address.get(0).getAddressLine(0);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return tripLocation;
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if(hasFocus) {
            switch (v.getId()) {

                case R.id.editTextStatusStartDate:
                    if (! editBtnEnable) {
                        showDate();
                    }
                    break;
                case R.id.editTextStatusStartTime:
                    if (! editBtnEnable) {
                        showTime();
                    }
                    break;
            }
        }
    }

    private void showTime() {

        TimePickerDialog timePickerDialog = new TimePickerDialog(DetailsTripActivity.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hourOfDay, int minutes) {
                if(minutes < 10){
                    strMinute = "0"+minutes;
                }else
                    strMinute = minutes +"";

                if (hourOfDay <10){
                    strHour = "0"+hourOfDay;
                }else
                    strHour = hourOfDay +"";
                eTxtStartTime.setText(strHour + ":" + strMinute);
            }
        }, 0, 0, false);
        timePickerDialog.show();
    }
    private void showDate() {
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                String myFormat = "MM / dd / yy"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

                eTxtStartDate.setText(sdf.format(myCalendar.getTime()));

            }

        };

        // TODO Auto-generated method stub
        new DatePickerDialog(DetailsTripActivity.this, date, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show();

    }

    /**
     *
     */
    class MyViewModelFactory implements ViewModelProvider.Factory {
        private DetailsTripActivity mActivity;


        public MyViewModelFactory(DetailsTripActivity activity) {
            mActivity = activity;
        }


        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            return (T) new DetailsTripViewModel(mActivity);
        }
    }

    private void setEditTextEnablity( boolean enable){
        eTxtTripName.setEnabled(enable);
        eTxtTripName.setClickable(enable);
        eTxtStartTime.setEnabled(enable);
        eTxtStartDate.setEnabled(enable);
        eTxtNotes.setClickable(enable);
        eTxtNotes.setEnabled(enable);
    }



}
