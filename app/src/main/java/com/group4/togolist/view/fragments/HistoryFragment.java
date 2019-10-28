package com.group4.togolist.view.fragments;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.AsyncTask;
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
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.group4.togolist.R;
import com.group4.togolist.util.DirectionsJSONParser;
import com.group4.togolist.view.adapters.HistoryAdapter;
import com.group4.togolist.model.Trip;
import com.group4.togolist.viewmodel.HomeViewModel;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
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
   private Polyline polyline;
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
                polyline.remove();
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
            if(polyline!= null) {
                polyline.remove();
            }
            if (pastTrips.size() != 0) {
                int i = 0 ;
                for (Trip trip : pastTrips) {
                    LatLng mOrigin = new LatLng(trip.getStartLocationLatitude(),trip.getStartLocationLongitude());
                    LatLng mDestination = new LatLng(trip.getEndLocationLatitude(),trip.getEndLocationLongitude());

                    myMap.addMarker(new MarkerOptions().position(new LatLng(trip.getStartLocationLatitude(), trip.getStartLocationLongitude())).title(trip.getTripName()).icon(BitmapDescriptorFactory.defaultMarker(markerBitMap[i])));
                    myMap.addMarker(new MarkerOptions().position(new LatLng(trip.getEndLocationLatitude(), trip.getEndLocationLongitude())).title(trip.getTripName()).icon(BitmapDescriptorFactory.defaultMarker(markerBitMap[i])));
                    drawRoute(mOrigin,mDestination);
//                    polyline = myMap.addPolyline(new PolylineOptions()
//                            .add(new LatLng(trip.getStartLocationLatitude(), trip.getStartLocationLongitude()), new LatLng(trip.getEndLocationLatitude(), trip.getEndLocationLongitude()))
//                            .width(5)
//                            .color(getColor()));
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

    private void drawRoute(LatLng mOrigin,LatLng mDestination){

        // Getting URL to the Google Directions API
        String url = getDirectionsUrl(mOrigin, mDestination);

        DownloadTask downloadTask = new DownloadTask();

        // Start downloading json data from Google Directions API
        downloadTask.execute(url);
    }


    private String getDirectionsUrl(LatLng origin,LatLng dest){

        // Origin of route
        String str_origin = "origin="+origin.latitude+","+origin.longitude;

        // Destination of route
        String str_dest = "destination="+dest.latitude+","+dest.longitude;

        // Key
        String key = "key=" + getString(R.string.google_maps_key);

        // Building the parameters to the web service
        String parameters = str_origin+"&"+str_dest+"&"+key;

        // Output format
        String output = "json";

        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/"+output+"?"+parameters;

        return url;
    }

    /** A method to download json data from url */
    private String downloadUrl(String strUrl) throws IOException {
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try{
            URL url = new URL(strUrl);

            // Creating an http connection to communicate with url
            urlConnection = (HttpURLConnection) url.openConnection();

            // Connecting to url
            urlConnection.connect();

            // Reading data from url
            iStream = urlConnection.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

            StringBuffer sb  = new StringBuffer();

            String line = "";
            while( ( line = br.readLine())  != null){
                sb.append(line);
            }

            data = sb.toString();

            br.close();

        }catch(Exception e){
            Log.d("Exception on download", e.toString());
        }finally{
            iStream.close();
            urlConnection.disconnect();
        }
        return data;
    }

    /** A class to download data from Google Directions URL */
    private class DownloadTask extends AsyncTask<String, Void, String> {

        // Downloading data in non-ui thread
        @Override
        protected String doInBackground(String... url) {

            // For storing data from web service
            String data = "";

            try{
                // Fetching the data from web service
                data = downloadUrl(url[0]);
                Log.d("DownloadTask","DownloadTask : " + data);
            }catch(Exception e){
                Log.d("Background Task",e.toString());
            }
            return data;
        }

        // Executes in UI thread, after the execution of
        // doInBackground()
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            ParserTask parserTask = new ParserTask();

            // Invokes the thread for parsing the JSON data
            parserTask.execute(result);
        }
    }

    /** A class to parse the Google Directions in JSON format */
    private class ParserTask extends AsyncTask<String, Integer, List<List<HashMap<String,String>>> >{

        // Parsing the data in non-ui thread
        @Override
        protected List<List<HashMap<String, String>>> doInBackground(String... jsonData) {

            JSONObject jObject;
            List<List<HashMap<String, String>>> routes = null;

            try{
                jObject = new JSONObject(jsonData[0]);
                DirectionsJSONParser parser = new DirectionsJSONParser();

                // Starts parsing data
                routes = parser.parse(jObject);
            }catch(Exception e){
                e.printStackTrace();
            }
            return routes;
        }

        // Executes in UI thread, after the parsing process
        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> result) {
            ArrayList<LatLng> points = null;
            PolylineOptions lineOptions = null;

            // Traversing through all the routes
            for(int i=0;i<result.size();i++){
                points = new ArrayList<LatLng>();
                lineOptions = new PolylineOptions();

                // Fetching i-th route
                List<HashMap<String, String>> path = result.get(i);

                // Fetching all the points in i-th route
                for(int j=0;j<path.size();j++){
                    HashMap<String,String> point = path.get(j);

                    double lat = Double.parseDouble(point.get("lat"));
                    double lng = Double.parseDouble(point.get("lng"));
                    LatLng position = new LatLng(lat, lng);

                    points.add(position);
                }

                // Adding all the points in the route to LineOptions
                lineOptions.addAll(points);
                lineOptions.width(8);
                lineOptions.color(getColor());
            }

            // Drawing polyline in the Google Map for the i-th route
            if(lineOptions != null) {
                polyline = myMap.addPolyline(lineOptions);
            }else
                Toast.makeText(getContext(),"No route is found", Toast.LENGTH_LONG).show();
        }
    }
}


