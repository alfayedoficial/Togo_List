package com.group4.togolist.fragments;


import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.group4.togolist.R;
import com.group4.togolist.model.HistoryAdapter;
import com.group4.togolist.model.Trip;
import com.group4.togolist.view.HomeRecyclerViewAdapter;
import com.group4.togolist.viewmodel.HomeViewModel;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class UpcomingFragment extends Fragment  implements HistoryAdapter.OnItemListener {

    private RecyclerView recyclerViewUpcomingTrip;
    private List<Trip> upcomingTrip ;

    Context context;
    HomeViewModel homeViewModel;

    public UpcomingFragment(Context context, List<Trip> trips, HomeViewModel homeViewModel) {
        // Required empty public constructor
        this.context = context;
        upcomingTrip = trips;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_upcoming,container,false);

        recyclerViewUpcomingTrip = view.findViewById(R.id.RecycleUpcoming);

        LinearLayoutManager lm = new LinearLayoutManager(context);
        recyclerViewUpcomingTrip.setLayoutManager(lm);
        HomeRecyclerViewAdapter adapter = new HomeRecyclerViewAdapter((HomeRecyclerViewAdapter.OnItemListener) this, context);

        adapter.setUpcomingTrip(upcomingTrip);

        recyclerViewUpcomingTrip.setAdapter(adapter);

        return view;
    }


    public void setUpcomingTrip(List<Trip> upcomingTrip) {
        this.upcomingTrip = upcomingTrip;
    }

    @Override
    public void onItemClick(int position) {
        homeViewModel.endedTripItemClicked(position);
    }

}
