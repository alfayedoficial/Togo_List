package com.group4.togolist.view;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.model.LatLng;
import com.group4.togolist.R;
import com.group4.togolist.model.Trip;
import com.group4.togolist.viewmodel.AddFormViewModel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AddFormActivity extends AppCompatActivity implements View.OnClickListener , View.OnFocusChangeListener{
   private LatLng latLang1;
   private LatLng latLang2;
   private Double lat1;
    private Double lat2;
   private Double long2;
   private Double long1;
   private String placeDestination;
   private String placeName;
   private String strMinute = "";
    private String strHour = "";
    private int repetition = Trip.NOT_REPEATED;
    private boolean roundTrip = false;
    private AddFormViewModel addFormViewModel;
    private Button btnAdd , btnCancel ;
    private RadioButton rdnBtnDaily , rdnBtnWeekly , rdnBtnDays , rdnBtnOneDirection , rdnBtnRoundTrip;
    private EditText eTxtTripName  , eTxtStartDate , eTxtStartTime , eTxtNotes;
    private    final Calendar myCalendar = Calendar.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_form);

        addFormViewModel = ViewModelProviders.of(this , new MyViewModelFactory(AddFormActivity.this)).get(AddFormViewModel.class);


        initComponent();


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
                Toast.makeText(AddFormActivity.this, placeName + ":" + long1 + ":" + lat1, Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(AddFormActivity.this, " Dest "+placeDestination + ":" + long2 + ":" + lat2, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(Status status) {

                    }
                });
            }
        });
    }


    private void initComponent() {
        eTxtTripName = findViewById(R.id.editText_nameTrip);
        eTxtStartDate =findViewById(R.id.editText_startDate);
        eTxtStartTime = findViewById(R.id.editText6);
        eTxtNotes = findViewById(R.id.editText_Note);

        btnAdd = findViewById(R.id.btn_add);
        btnCancel = findViewById(R.id.btn_cancel);

        rdnBtnDaily = findViewById(R.id.radioBtn_daily);
        rdnBtnWeekly = findViewById(R.id.radioBtnweakly);
        rdnBtnDays = findViewById(R.id.radioBtnDays);
        rdnBtnOneDirection = findViewById(R.id.radioBtnOneDirection);
        rdnBtnRoundTrip = findViewById(R.id.radioButton5);

       eTxtStartDate.setOnFocusChangeListener(this);
       eTxtStartTime.setOnFocusChangeListener(this);


        rdnBtnDaily.setOnClickListener(this);
       rdnBtnWeekly.setOnClickListener(this);
       rdnBtnDays.setOnClickListener(this);
       rdnBtnOneDirection.setOnClickListener(this);
       rdnBtnRoundTrip.setOnClickListener(this);

       btnAdd.setOnClickListener(this);
       btnCancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.editText_startPoint:
                break;
            case R.id.editText_endPoint:
                break;

            case R.id.radioBtn_daily:
                boolean checked = ((RadioButton) v).isChecked();
                if (checked){
                    repetition = Trip.DAILY;
                }
                break;
            case R.id.radioBtnweakly:
                boolean checked1 = ((RadioButton) v).isChecked();
                if (checked1){
                    repetition = Trip.WEEKLY;
                }
                break;
            case R.id.radioBtnDays:
                boolean checked2 = ((RadioButton) v).isChecked();
                if (checked2){
                    // upComing handling
                }
                break;
            case R.id.radioBtnOneDirection:
                boolean checked3 = ((RadioButton) v).isChecked();
                if (checked3){
                    roundTrip = false ;
                }
                break;
            case R.id.radioButton5:    // RoundTrip radioButton
                boolean checked4 = ((RadioButton) v).isChecked();
                if (checked4){
                    roundTrip = true ;
                }
                break;
            case R.id.btn_add:
                addTrip();
                break;

            case R.id.btn_cancel:
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
                case R.id.editText6:
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

        myCalendar.set(year , month , day , hour , minute);


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



    /**
 *
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
