package com.group4.togolist.view;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.TypeFilter;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;

import com.group4.togolist.R;
import com.group4.togolist.model.Trip;
import com.group4.togolist.viewmodel.AddFormViewModel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class AddFormActivity extends AppCompatActivity implements View.OnClickListener , View.OnFocusChangeListener {
   private List<Place.Field> fields;
    private List<Place.Field> fieldsEnd;
    private LatLng latLangStartPoint;
   private LatLng latLangEndPoint;
   private Double latStartPoint = 20.0;
    private Double latEndPoint = 30.0;
   private Double longStartPoint = 20.0;
   private Double longEndPoint = 30.0;
   private String placeEndPoint;
   private String placeStartPoint;
    private String strMinute = "";
    private String strHour = "";
    private int repetition = Trip.NOT_REPEATED;
    private boolean roundTrip = false;
    private AddFormViewModel addFormViewModel;
    private Button btnAdd , btnCancel ;
    private RadioButton rdnBtnDaily , rdnBtnWeekly , rdnBtnDays , rdnBtnOneDirection , rdnBtnRoundTrip;
    private EditText eTxtTripName  , eTxtStartDate , eTxtStartTime , eTxtNotes;
    private TextView txtViewStartPoint , txtViewEndPoint ;

    private    final Calendar myCalendar = Calendar.getInstance();
    private Calendar currentCalendar = Calendar.getInstance();
    private Calendar tripCalendar = Calendar.getInstance();

    private ImageButton imgBtnHome , imgBtnProfile;
    private com.google.android.material.floatingactionbutton.FloatingActionButton fltBtnAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_form);

        initComponent();
        autocompletePlace();

        addFormViewModel = ViewModelProviders.of(this , new MyViewModelFactory(AddFormActivity.this)).get(AddFormViewModel.class);

        myCalendar.setTimeInMillis(System.currentTimeMillis());
        currentCalendar.setTimeInMillis(System.currentTimeMillis());

    }

    private void initComponent() {
        eTxtTripName = findViewById(R.id.editText_nameTrip);
        eTxtStartDate =findViewById(R.id.editText_startDate);
        eTxtStartTime = findViewById(R.id.editText_startTime);
        eTxtNotes = findViewById(R.id.editTextNote);

        txtViewStartPoint = findViewById(R.id.TextViewStartPoint);
        txtViewEndPoint = findViewById(R.id.TextViewEndPoint);

        btnAdd = findViewById(R.id.btnAdd);
        btnCancel = findViewById(R.id.btnCancel);

        rdnBtnDaily = findViewById(R.id.radioBtnDaily);
        rdnBtnWeekly = findViewById(R.id.radioBtnWeakly);
        rdnBtnDays = findViewById(R.id.radioBtnDays);
        rdnBtnOneDirection = findViewById(R.id.radioBtnOneDirection);
        rdnBtnRoundTrip = findViewById(R.id.radioBtnRoundTrip);

       eTxtStartDate.setOnFocusChangeListener(this);
       eTxtStartTime.setOnFocusChangeListener(this);

        eTxtStartDate.setOnClickListener(this);
        eTxtStartTime.setOnClickListener(this);

        imgBtnHome = findViewById(R.id.imageBtnHome);
        imgBtnProfile = findViewById(R.id.imageBtnProfile);
        fltBtnAdd = findViewById(R.id.fABtnAddNote);


        rdnBtnDaily.setOnClickListener(this);
       rdnBtnWeekly.setOnClickListener(this);
       rdnBtnDays.setOnClickListener(this);
       rdnBtnOneDirection.setOnClickListener(this);
       rdnBtnRoundTrip.setOnClickListener(this);

        imgBtnHome.setOnClickListener(this);
        imgBtnProfile.setOnClickListener(this);

       btnAdd.setOnClickListener(this);
       btnCancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.editText_startDate:
                showDate();
                break;
            case R.id.editText_startTime:
                showTime();
                break;
            case R.id.radioBtnDaily:
                boolean checked = ((RadioButton) v).isChecked();
                if (checked){
                    repetition = Trip.DAILY;
                }
                break;
            case R.id.radioBtnWeakly:
                boolean checked1 = ((RadioButton) v).isChecked();
                if (checked1){
                    repetition = Trip.WEEKLY;
                }
                break;
            case R.id.radioBtnDays:   // monthly
                boolean checked2 = ((RadioButton) v).isChecked();
                if (checked2){
                    repetition = Trip.MONTHLY;
                }
                break;
            case R.id.radioBtnOneDirection:
                boolean checked3 = ((RadioButton) v).isChecked();
                if (checked3){
                    roundTrip = false ;
                }
                break;
            case R.id.radioBtnRoundTrip:
                boolean checked4 = ((RadioButton) v).isChecked();
                if (checked4){
                    roundTrip = true ;
                }
                break;
            case R.id.btnAdd:
                addTrip();
                break;

            case R.id.btnCancel:
                addFormViewModel.goToHome();
                break;

            case R.id.imageBtnHome:
                addFormViewModel.goToHome();
                break;

            case R.id.imageBtnProfile:
                addFormViewModel.goToProfile();
                break;

        }
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if(hasFocus) {
            switch (v.getId()) {

                case R.id.editText_startDate:
                    showDate();
                    break;
                case R.id.editText_startTime:
                    showTime();
                    break;
            }
        }
    }

    private void addTrip() {
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

       addFormViewModel.createNewTrip(eTxtTripName.getText().toString() , longStartPoint, latStartPoint, longEndPoint, latEndPoint, tripCalendar, repetition, roundTrip, eTxtNotes.getText().toString());

    }

    private void showTime() {

        TimePickerDialog timePickerDialog = new TimePickerDialog(AddFormActivity.this, new TimePickerDialog.OnTimeSetListener() {
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

                String myFormat = "MM/dd/yy"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

                eTxtStartDate.setText(sdf.format(myCalendar.getTime()));

            }

        };

                // TODO Auto-generated method stub
                new DatePickerDialog(AddFormActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();

    }

    private void autocompletePlace() {

        if (!Places.isInitialized()) {
            Places.initialize(AddFormActivity.this, "AIzaSyDZYB_DYK3-5xAkI1c3ioMAZ6I_Gm_wqX8",Locale.getDefault());
            PlacesClient placesClient = Places.createClient(AddFormActivity.this);
        }

        fields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG);

        AutocompleteSupportFragment autocompleteFragStartPoint = (AutocompleteSupportFragment)
                getSupportFragmentManager().findFragmentById((R.id.FragmentStartPoint));

        autocompleteFragStartPoint.setPlaceFields(fields);
        autocompleteFragStartPoint.setCountry("EG");
        autocompleteFragStartPoint.setTypeFilter(TypeFilter.ADDRESS);

        autocompleteFragStartPoint.setOnPlaceSelectedListener(new PlaceSelectionListener() {
                @Override
                public void onPlaceSelected(Place place) {
                    // TODO: Get info about the selected place.

                    latLangStartPoint = place.getLatLng();
                    placeStartPoint = (String) place.getName();
                    longStartPoint = latLangStartPoint.longitude;
                    latStartPoint = latLangStartPoint.latitude;
                    //txtViewStartPoint.setText(placeStartPoint);
                    //Toast.makeText(AddFormActivity.this, placeStartPoint + ": " + longStartPoint + " : " + latStartPoint, Toast.LENGTH_SHORT).show();
                }
                @Override
                public void onError(Status status) {
                    // TODO: Handle the error.
                  //  txtViewStartPoint.setText(status.toString());
                    System.out.println(status.toString());
                    Toast.makeText(AddFormActivity.this, status.toString(), Toast.LENGTH_SHORT).show();
                }
            });

        if (!Places.isInitialized()) {
            Places.initialize(AddFormActivity.this, getString(R.string.google_maps_key));
        }

        fieldsEnd = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG);

            /**
             * Initialize the AutocompleteSupportFragment End point.
             */
            AutocompleteSupportFragment autocompleteFragEndPoint = (AutocompleteSupportFragment)
                    getSupportFragmentManager().findFragmentById((R.id.FragmentEndPoint));

            /**
             * Specify the types of place data to return.
             */

            autocompleteFragEndPoint.setPlaceFields(fieldsEnd);

            autocompleteFragEndPoint.setCountry("EG");
           autocompleteFragEndPoint.setTypeFilter(TypeFilter.ADDRESS);

            /**
             * Set up a PlaceSelectionListener to handle the response.
             */

            autocompleteFragEndPoint.setOnPlaceSelectedListener(new PlaceSelectionListener() {
                @Override
                public void onPlaceSelected(Place placeÈnd) {
                    // TODO: Get info about the selected place.

                    latLangEndPoint = placeÈnd.getLatLng();
                    placeEndPoint = (String) placeÈnd.getName();
                    longEndPoint = latLangEndPoint.longitude;
                    latEndPoint = latLangEndPoint.latitude;

                    //txtViewEndPoint.setText(placeEndPoint);

                    //Toast.makeText(AddFormActivity.this, placeEndPoint + ": " + longEndPoint + " : " + latEndPoint, Toast.LENGTH_SHORT).show();

                }

                @Override
                public void onError(Status status) {
                    // TODO: Handle the error.
                    System.out.println(status.toString());
                    Toast.makeText(AddFormActivity.this, status.toString(), Toast.LENGTH_SHORT).show();
                }
            });

    }


    /**
     *  AddFormViewModelFactory
     */

    class MyViewModelFactory implements ViewModelProvider.Factory {
        private Activity mActivity;


        public MyViewModelFactory(Activity activity) {
            mActivity = activity;
        }


        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            return (T) new AddFormViewModel(mActivity);
        }
    }

}
