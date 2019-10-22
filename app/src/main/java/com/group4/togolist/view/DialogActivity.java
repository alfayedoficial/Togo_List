package com.group4.togolist.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.group4.togolist.R;
import com.group4.togolist.viewmodel.DialogViewModel;
import com.group4.togolist.viewmodel.LoginViewModel;
import com.group4.togolist.viewmodel.ProfileViewModel;

public class DialogActivity extends AppCompatActivity implements View.OnClickListener {

    private DialogViewModel dialogViewModel;
    private Button btnStart , btnLater , btnCancel;
    private TextView txtTripPlace , txtTripName , txtTripDate , txtTripTime;
    private ImageButton imgBtnDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        initComponent();
        dialogViewModel = ViewModelProviders.of(this, new MyViewModelFactory(DialogActivity.this)).get(DialogViewModel.class);
        dialogViewModel.askForSystemOverlayPermission();
    }

    /**
     * initiate views
     */
    private void initComponent() {

        txtTripName = findViewById(R.id.textViewPastTripName);
        txtTripPlace = findViewById(R.id.textViewPastTripPlace);
        txtTripDate = findViewById(R.id.textViewPastTripCalender);
        txtTripTime = findViewById(R.id.textViewPastTripTime);

        imgBtnDetails = findViewById(R.id.imageBtnDetails);
        btnStart = findViewById(R.id.btnDialogStart);
        btnLater = findViewById(R.id.btnDialogLater);
        btnCancel = findViewById(R.id.btnDialogCancel);

        imgBtnDetails.setOnClickListener(this);
        btnStart.setOnClickListener(this);
        btnLater.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnDialogStart:
                dialogViewModel.startTrip();
                break;
            case R.id.btnDialogLater:
                dialogViewModel.waitForLater();
                break;
            case R.id.btnDialogCancel:
                cancelTrip();
                break;
            case R.id.imageBtnDetails:
                dialogViewModel.showDetails();
                break;
        }

    }

    private void cancelTrip() {
        AlertDialog.Builder builder = new AlertDialog.Builder(
                DialogActivity.this);
        /**
         * set message
         */

        builder.setMessage("Are you Sure you want to Cancel the Trip?");
        builder.setCancelable(false);
        builder.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog,
                                        int which) {
                        dialogViewModel.cancelTrip();
                    }
                });

        builder.setNegativeButton("No",
                new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog,
                                        int which) {
                        // TODO Auto-generated method stub
                        // close the dialog box
                        dialog.cancel();
                    }
                });
/**
 * create instance of alert dialog and assign configuration of builder to alert dialog instance
 */

        AlertDialog alert = builder.create();
        alert.setCanceledOnTouchOutside(false);
        /**
         * Show Alert Dialog
         */

        alert.show();
    }
    /**
     *
     * @param tripName assign Trip Name to textView for TripName
     * @param tripPlace assign Trip Place to textView for TripPlace
     * @param tripDate assign Trip Date to textView for TripDate
     * @param tripTime assign Trip Time to textView for TripTime
     */
    public void setDialogTripData(String tripName , String tripPlace , String tripDate , String tripTime){

        txtTripName.setText(tripName);
        txtTripDate.setText(tripPlace);
        txtTripDate.setText(tripDate);
        txtTripTime.setText(tripTime);

    }

    /**
     *  DialogViewModelFactory
     */

    class MyViewModelFactory implements ViewModelProvider.Factory {
        private DialogActivity mActivity;


        public MyViewModelFactory(DialogActivity activity) {
            mActivity = activity;
        }


        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            return (T) new DialogViewModel(mActivity);
        }

    }
}
