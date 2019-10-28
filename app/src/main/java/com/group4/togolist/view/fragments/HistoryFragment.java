package com.group4.togolist.view.fragments;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.group4.togolist.R;
import com.group4.togolist.view.adapters.HistoryAdapter;
import com.group4.togolist.model.Trip;
import com.group4.togolist.viewmodel.HomeViewModel;

import java.util.List;
import java.util.Random;

/**
 * A simple {@link Fragment} subclass.
 */
public class HistoryFragment extends Fragment  implements HistoryAdapter.OnItemListener , OnMapReadyCallback {

   private List<Trip> pastTrips;
   private Context context;
   private HomeViewModel homeViewModel;
   private HistoryAdapter historyAdapter;

   private GoogleMap myMap;

   private SupportMapFragment  mMapFragment ;
 private float markerBitMap [] = {BitmapDescriptorFactory.HUE_AZURE , BitmapDescriptorFactory.HUE_BLUE , BitmapDescriptorFactory.HUE_CYAN , BitmapDescriptorFactory.HUE_GREEN,
         BitmapDescriptorFactory.HUE_MAGENTA , BitmapDescriptorFactory.HUE_ORANGE , BitmapDescriptorFactory.HUE_RED , BitmapDescriptorFactory.HUE_ROSE,
         BitmapDescriptorFactory.HUE_VIOLET , BitmapDescriptorFactory.HUE_YELLOW};
    public HistoryFragment(){
           // Required empty public constructor
       }
    public HistoryFragment(Context context, HomeViewModel homeViewModel) {

        this.context = context;
        this.homeViewModel = homeViewModel ;
    }

    @Override
    public View onCreateView(LayoutInflater inflater,   ViewGroup container,
                            Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_history, container, false);
        RecyclerView pastTripRecyclerView = view.findViewById(R.id.RecyclePastTrip);

        /**
         *
         */

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        /**
         *
         */
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
                drawRoutes();
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        myMap = googleMap ;

    }

    private  int getColor(){
        Random rnd = new Random();
         int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
        return color ;


    }

    private void drawRoutes(){
        if (pastTrips !=null) {
            if (pastTrips.size() != 0) {
                int i = 0 ;
                for (Trip trip : pastTrips) {

                    myMap.addMarker(new MarkerOptions().position(new LatLng(trip.getStartLocationLatitude(), trip.getStartLocationLongitude())).title(trip.getTripName()).icon(BitmapDescriptorFactory.defaultMarker(markerBitMap[i])));
                    myMap.addMarker(new MarkerOptions().position(new LatLng(trip.getEndLocationLatitude(), trip.getEndLocationLongitude())).title(trip.getTripName()).icon(BitmapDescriptorFactory.defaultMarker(markerBitMap[i])));
                    myMap.addPolyline(new PolylineOptions()
                            .add(new LatLng(trip.getStartLocationLatitude(), trip.getStartLocationLongitude()), new LatLng(trip.getEndLocationLatitude(), trip.getEndLocationLongitude()))
                            .width(5)
                            .color(getColor()));
                    Log.i("MapRoute" , ""+ trip.getTripName());
                    System.out.println("Map Route" + trip.getTripName());
                     i ++ ;
                    if ( i == markerBitMap.length )
                        i = 0 ;
                }
            }
        }

        /**
         * test code
         *
         *
                int color = getColor();
                myMap.addPolyline(new PolylineOptions()
                        .add(new LatLng(28.4956014,34.4829166), new LatLng(25.0684143,34.8666785))
                        .width(5)
                        .color(color));
                myMap.addMarker(new MarkerOptions().position(new LatLng(28.4956014,34.4829166)).title("Fatma").icon(BitmapDescriptorFactory.defaultMarker(markerBitMap[0])));

                myMap.addMarker(new MarkerOptions().position(new LatLng(25.0684143,34.8666785)).title("Fatma").icon(BitmapDescriptorFactory.defaultMarker(markerBitMap[0])));
                int colorDes = getColor();
                myMap.addPolyline(new PolylineOptions()
                        .add(new LatLng(31.0413814,31.3478201), new LatLng(31.3366173,27.1852923))
                        .width(5)
                        .color(colorDes));
                myMap.addMarker(new MarkerOptions().position(new LatLng(31.0413814,31.3478201)).title("Kaml").icon(BitmapDescriptorFactory.defaultMarker(markerBitMap[1])));
                myMap.addMarker(new MarkerOptions().position(new LatLng(31.3366173,27.1852923)).title("Kamal").icon(BitmapDescriptorFactory.defaultMarker(markerBitMap[1])));
            */

    }

}
