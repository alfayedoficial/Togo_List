package com.group4.togolist.view;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;


import com.group4.togolist.R;
import com.group4.togolist.viewmodel.DialogViewModel;

public class TripAlertDialog  implements View.OnClickListener {
    private Context context;
    private DialogViewModel dialogViewModel ;
    private String tripName ;
    private TextView txtTripName , txtTripPlace , txtTripDate , txtTripTime ;

    public  TripAlertDialog(Context context , String tripNam){
        this.context = context ;
        this.tripName = tripNam;
        //dialogViewModel = new DialogViewModel();

    }
    public void showDialog ( ){

        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.activity_dialog);
         txtTripName = dialog.findViewById(R.id.textViewPastTripName);
         txtTripPlace = dialog.findViewById(R.id.textViewPastTripPlace);
          txtTripDate = dialog.findViewById(R.id.textViewPastTripCalender);
         txtTripTime = dialog.findViewById(R.id.textViewPastTripTime);

        //TextView imgBtnDetails = findViewById(R.id.imageBtnDetails);
        Button btnStart = dialog.findViewById(R.id.btnDialogStart);
        Button  btnLater = dialog.findViewById(R.id.btnDialogLater);
        Button btnCancel = dialog.findViewById(R.id.btnDialogCancel);

      //  imgBtnDetails.setOnClickListener(this);
        btnStart.setOnClickListener(this);
        btnLater.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }



    private void cancelTrip() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        /**
         * set message
         */

        builder.setTitle(R.string.titlecancel);
        builder.setMessage(R.string.messagecancel);
        builder.setCancelable(false);
        builder.setPositiveButton(R.string.yes,
                new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog,
                                        int which) {
                        dialogViewModel.cancelTrip();
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
}
