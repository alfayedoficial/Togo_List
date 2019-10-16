package com.group4.togolist.fragments;


import android.content.Context;
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

    List<Trip> pastTrips;
    Context context;
    HomeViewModel homeViewModel;
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
        LinearLayoutManager lm = new LinearLayoutManager(context);
        pastTripRecyclerView.setLayoutManager(lm);

        HistoryAdapter historyAdapter = new HistoryAdapter(context,pastTrips,this);
        pastTripRecyclerView.setAdapter(historyAdapter);
        return view;
    }


    @Override
    public void onItemClick(int position) {
        homeViewModel.endedTripItemClicked(position);
    }
}
