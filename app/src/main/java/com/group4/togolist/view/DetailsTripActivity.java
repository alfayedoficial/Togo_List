package com.group4.togolist.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.group4.togolist.R;
import com.group4.togolist.viewmodel.AddFormViewModel;
import com.group4.togolist.viewmodel.DetailsTripViewModel;
import com.group4.togolist.viewmodel.HomeViewModel;
import com.group4.togolist.viewmodel.LoginViewModel;

public class DetailsTripActivity extends AppCompatActivity implements View.OnClickListener {


    private EditText eTxtTripName  , eTxtStartDate , eTxtStartTime , eTxtNotes;
    private Button btnEdit , btnStart , btnDelete ;
    private TextView txtStatus , txtRepetition , txtTripType , txtStartPoint , txtEndPoint ;
    private DetailsTripViewModel detailsTripViewModel;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_trip);

        detailsTripViewModel = ViewModelProviders.of(this , new MyViewModelFactory(DetailsTripActivity.this)).get(DetailsTripViewModel.class);
        initComponent();
    }


    private void initComponent() {

        eTxtTripName = findViewById(R.id.editText_nameTrip2);
        eTxtStartDate = findViewById(R.id.editTextStatusStartDate);
        eTxtStartTime = findViewById(R.id.editTextStatusStartTime);
        eTxtNotes = findViewById(R.id.editTextStatusNote);
        txtStatus = findViewById(R.id.textViewStatusShow);


        txtRepetition = findViewById(R.id.TextViewStatusRepetitionShow);
        txtTripType = findViewById(R.id.textViewStatusTripTypeShow);

        txtStartPoint = findViewById(R.id.textViewStatusStartPoint);
        txtEndPoint = findViewById(R.id.textViewStatusEndPoint);

        btnEdit = findViewById(R.id.btnStatusLater);
        btnStart = findViewById(R.id.btnStatusStart);
        btnDelete = findViewById(R.id.btnStatusDelete);

        btnEdit.setOnClickListener(this);
        btnStart.setOnClickListener(this);
        btnDelete.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnStatusLater:  // edit Button
                break;
            case R.id.btnStatusStart:
                break;
            case R.id.btnStatusDelete:
              //  homeViewModel.deleteTrip(eTxtTripName.getText().toString());
                break;
        }
    }

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

}
