package com.group4.togolist.fragments;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.group4.togolist.R;
import com.group4.togolist.model.HistoryAdapter;
import com.group4.togolist.model.Trip;
import com.group4.togolist.viewmodel.HomeViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class HistoryFragment extends Fragment  implements HistoryAdapter.OnItemListener {

   private List<Trip> pastTrips;
   private Context context;
   private HomeViewModel homeViewModel;

    public HistoryFragment(Context context, List<Trip> trips, HomeViewModel homeViewModel) {
        // Required empty public constructor
        this.context = context;
        pastTrips = trips;
        this.homeViewModel = homeViewModel ;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_history, container, false);
        RecyclerView pastTripRecyclerView = view.findViewById(R.id.RecyclePastTrip);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        pastTripRecyclerView.setLayoutManager(linearLayoutManager);

        HistoryAdapter historyAdapter = new HistoryAdapter(context, pastTrips,this);
        pastTripRecyclerView.setAdapter(historyAdapter);
        return view;
    }


    @Override
    public void onItemClick(int position) {
        homeViewModel.endedTripItemClicked(position);
    }

    @Override
    public void onItemDeleteClick(int deleteTripPosition) {
        deleteTrip(pastTrips.get(deleteTripPosition).getTripName());
    }
    private void deleteTrip(String tripNam) {
        final String  tripName = tripNam ;
        AlertDialog.Builder builder = new AlertDialog.Builder(
                context);
        /**
         * set message
         */

        builder.setMessage("Are you Sure you want to Delete Trip?");
        builder.setCancelable(false);
        builder.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog,
                                        int which) {
                        homeViewModel.deleteTrip(tripName);
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
}
