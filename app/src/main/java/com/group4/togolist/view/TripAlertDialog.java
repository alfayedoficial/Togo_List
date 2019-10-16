package com.group4.togolist.view;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.group4.togolist.R;

public class TripAlertDialog extends DialogFragment {

   private String [] tripData;

    private AlertDialog.Builder builder ;
    private TripDialogInterface  tripDialogInterface;

    public void setTripData(String[] tripData) {
        this.tripData = tripData;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(false);
        return dialog;
    }

    @Override
    public void onAttach(@NonNull Context context) {

        super.onAttach(context);
        try {
            tripDialogInterface = (TripDialogInterface) context;
        } catch (Exception e) {
            e.printStackTrace();
        }


        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View dialogView = inflater.inflate(R.layout.activity_dialog , container , false );


        return dialogView;
    }
}
