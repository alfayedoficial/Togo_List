package com.group4.togolist.view.fragments;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.group4.togolist.R;
import com.group4.togolist.view.adapters.HistoryAdapter;
import com.group4.togolist.model.Trip;
import com.group4.togolist.viewmodel.HomeViewModel;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class HistoryFragment extends Fragment  implements HistoryAdapter.OnItemListener {

   private List<Trip> pastTrips;
   private Context context;
   private HomeViewModel homeViewModel;
   private HistoryAdapter historyAdapter;

    public HistoryFragment(Context context, HomeViewModel homeViewModel) {
        // Required empty public constructor
        this.context = context;
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

        historyAdapter = new HistoryAdapter(context,this);
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

        builder.setTitle(R.string.titledelete);
        builder.setMessage(R.string.messagedelete);
        builder.setCancelable(false);
        builder.setPositiveButton(R.string.yes,
                new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog,
                                        int which) {
                        homeViewModel.deleteTrip(tripName);
                        updateRecyclerView();
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

    @Override
    public void onStart() {
        super.onStart();
        updateRecyclerView();
    }

    public void updateRecyclerView(){
        homeViewModel.getEndedTrip().observe(this, new Observer<List<Trip>>() {
            @Override
            public void onChanged(List<Trip> trips) {
                historyAdapter.setPastTrips(trips);
                pastTrips = trips;
            }
        });
    }
}
