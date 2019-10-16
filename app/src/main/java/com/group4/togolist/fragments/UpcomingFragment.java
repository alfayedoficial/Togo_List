package com.group4.togolist.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.group4.togolist.R;
import com.group4.togolist.model.Trip;
import com.group4.togolist.view.HomeRecyclerViewAdapter;
import com.group4.togolist.view.PastTripDetailsActivity;
import com.group4.togolist.viewmodel.HomeViewModel;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class UpcomingFragment extends Fragment implements HomeRecyclerViewAdapter.OnItemListener {

    private RecyclerView recyclerViewUpcomingTrip;
    private List<Trip> upcomingTrip ;
    private Context context;
    private HomeViewModel homeViewModel;

    public UpcomingFragment(Context context, List<Trip> trips, HomeViewModel homeViewModel) {
        // Required empty public constructor
        this.homeViewModel = homeViewModel;
        this.context = context;
        upcomingTrip = trips;
        this.homeViewModel = homeViewModel ;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_upcoming,container,false);

        recyclerViewUpcomingTrip = view.findViewById(R.id.RecycleUpcoming);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        recyclerViewUpcomingTrip.setLayoutManager(linearLayoutManager);
        HomeRecyclerViewAdapter adapter = new HomeRecyclerViewAdapter(this , context);

        adapter.setUpcomingTrip(upcomingTrip);

        recyclerViewUpcomingTrip.setAdapter(adapter);

        return view;
    }


   public void setUpcomingTrip(List<Trip> upcomingTrip) {
        this.upcomingTrip = upcomingTrip;
    }

    @Override
    public void onItemClick(int position) {
        homeViewModel.upcomingTripItemClicked(position);
    }

    @Override
    public void onItemDeleteClick(int deleteTripPosition) {
            deleteTrip(upcomingTrip.get(deleteTripPosition).getTripName());
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
