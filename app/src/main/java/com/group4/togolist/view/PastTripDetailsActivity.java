package com.group4.togolist.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.group4.togolist.R;
import com.group4.togolist.model.Trip;
import com.group4.togolist.viewmodel.DetailsTripViewModel;
import com.group4.togolist.viewmodel.PastTripsDetailsViewModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Locale;

public class PastTripDetailsActivity extends AppCompatActivity  implements View.OnClickListener {
    private static Handler handler;
    private ImageButton imgBtnHome , imgBtnProfile;
    private com.google.android.material.floatingactionbutton.FloatingActionButton fltBtnAdd;
    private PastTripsDetailsViewModel pastTripsDetailsViewModel;

    private Button btnDelete ;
    private TextView txtTripName  , txtStartDate , txtStartTime , txtNotes;
    private TextView   txtRepetition , txtTripType , txtStartPoint , txtEndPoint ;
    private ImageView mapImage;
    private double avgLong;
    private double avgLat;
    private double lat1;
    private double long1;
    private double lat2;
    private double long2;
    private String result = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_past_trip_details);
        initComponent();
        pastTripsDetailsViewModel = ViewModelProviders.of(this , new MyViewModelFactory(PastTripDetailsActivity.this)).get(PastTripsDetailsViewModel.class);

        /**
         * image trip
         */
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                Bundle b = msg.getData();
                String res = b.getString("JSON");
                try {
                    JSONObject reader = new JSONObject(res);
                    JSONArray items = reader.getJSONArray("routes");
                    JSONObject jobj1 = items.getJSONObject(0);
                    JSONObject jarray2 = jobj1.getJSONObject("overview_polyline");
                    String code = jarray2.getString("points");
                    for (int i = 0; i < items.length(); ++i) {
                        class LongOperation extends AsyncTask<String, Void, Bitmap> {
                            @Override
                            protected void onPreExecute() {
                                super.onPreExecute();
                            }

                            @Override
                            protected void onPostExecute(Bitmap bitmap) {
                                super.onPostExecute(bitmap);
                                if (bitmap == null)
                                    mapImage.setImageResource(R.mipmap.cancelled);
                                mapImage.setImageBitmap(bitmap);
                            }

                            @Override
                            protected void onProgressUpdate(Void... values) {
                                super.onProgressUpdate(values);
                            }

                            @Override
                            protected Bitmap doInBackground(String... strings) {
                                String s;
                                s = strings[0];
                                Bitmap bitmap = download(s);
                                return bitmap;
                            }

                            public Bitmap download(String code) {
                                Bitmap b = null;
                                InputStream is;
                                HttpURLConnection connection;
                                URL url1;
                                try {

                                    url1 = new URL("https://maps.googleapis.com/maps/api/staticmap?center=" + avgLat + "," + avgLong + "&" +
                                            "zoom=9&size=800x380&maptype=roadmap&path=weight:7%10Ccolor:orange%7Cenc:" + code + "&key=AIzaSyCeYHDhDctqGmb5APIdyWrd-imDO2DkQHc");
                                    connection = (HttpURLConnection) url1.openConnection();
                                    is = connection.getInputStream();
                                    b = BitmapFactory.decodeStream(is);
                                    is.close();

                                } catch (MalformedURLException e) {
                                    e.printStackTrace();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                                return b;
                            }
                        }
                        new LongOperation().execute(code);
                    }

                } catch (JSONException e) {
                    Log.i("Test", e.toString());
                }


            }
        };
        final String url = "https://maps.googleapis.com/maps/api/directions/json?origin=" + lat1 + "," + long1 + "&destination=" + lat2 + "," + long2 + "&key=AIzaSyCeYHDhDctqGmb5APIdyWrd-imDO2DkQHc";
        Runnable r = new Runnable() {

            @Override
            public void run() {
                InputStream is;
                HttpURLConnection connection;
                URL url1;
                try {
                    url1 = new URL(url);
                    connection = (HttpURLConnection) url1.openConnection();
                    is = connection.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"), 8);
                    StringBuilder sb = new StringBuilder();
                    String line = null;
                    while ((line = reader.readLine()) != null) {
                        sb.append(line + "\n");
                    }
                    result = sb.toString();
                    Message msg = new Message();
                    Bundle bundle = new Bundle();
                    bundle.putString("JSON", result);
                    msg.setData(bundle);
                    handler.sendMessage(msg);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

        };
        Thread th = new Thread(r);
        th.start();
    }

    private void initComponent() {

        mapImage = findViewById(R.id.mapImage);
        txtTripName = findViewById(R.id.textViewPTDStatusNameTrip);
        txtStartPoint = findViewById(R.id.textViewPTDStatusStartPoint);
        txtEndPoint = findViewById(R.id.textViewPTDStatusEndPoint);
        txtStartDate = findViewById(R.id.textViewPTDStatusStartDate);
        txtStartTime = findViewById(R.id.textViewPTDStatusStartTime);

        txtRepetition = findViewById(R.id.textViewPTDStatusRepetition);
        txtTripType = findViewById(R.id.textViewPTDStatusTripType);

        txtNotes = findViewById(R.id.textViewPTDStatusNote);;

        btnDelete = findViewById(R.id.btnStatusDelete);

        imgBtnHome = findViewById(R.id.imageBtnHome);
        imgBtnProfile = findViewById(R.id.imageBtnProfile);
        fltBtnAdd = findViewById(R.id.fABtnAddNote);
        btnDelete.setOnClickListener(this);
        imgBtnHome.setOnClickListener(this);
        imgBtnProfile.setOnClickListener(this);
        fltBtnAdd.setOnClickListener(this);

        final Trip ex2 = (Trip) getIntent().getSerializableExtra("Past_Trip");
        assert ex2 != null;
        if(ex2 != null) {
            lat1 = ex2.getStartLocationLatitude();
            long1 = ex2.getStartLocationLongitude();
            lat2 = ex2.getEndLocationLatitude();
            long2 = ex2.getEndLocationLongitude();
            avgLat = (lat1 + lat2) / 2;
            avgLong = (long1 + long2) / 2;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnStatusDelete:
                deleteTrip();
            case R.id.imageBtnHome:
                pastTripsDetailsViewModel.goToHome();
                break;
            case R.id.imageBtnProfile:
                pastTripsDetailsViewModel.goToProfile();
                break;
            case R.id.fABtnAddNote:
                pastTripsDetailsViewModel.goToAddForm();
                break;
        }
    }

    private void deleteTrip() {
        AlertDialog.Builder builder = new AlertDialog.Builder(
                PastTripDetailsActivity.this);
        /**
         * set message
         */

        builder.setMessage("Are you Sure you want to Delete Trip?");
        builder.setCancelable(false);
        builder.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog,
                                        int which) {
                        pastTripsDetailsViewModel.deleteTrip();
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


    public  void setTripDetails(Trip trip){

        txtTripName.setText(trip.getTripName());
        String repetitionString = "" ;

        switch (trip.getRepetition()){
            case Trip.NOT_REPEATED:
                repetitionString   = "NOT_REPEATED" ;
                break;

            case Trip.DAILY:
                repetitionString = "DAILY";
                break;
            case Trip.WEEKLY:
                repetitionString = "WEEKLY";
                break;
            case Trip.MONTHLY:
                repetitionString = "MONTHLY";
                break;
        }
        txtRepetition.setText(repetitionString);

        if (trip.isRoundTrip()){
            txtTripType.setText("Round Trip");
        }else
            txtTripType.setText("One Direction");

        txtStartPoint.setText(getTripPlace(trip.getStartLocationLatitude() , trip.getStartLocationLongitude()));
        txtEndPoint.setText(getTripPlace(trip.getEndLocationLatitude() , trip.getEndLocationLongitude()));

        txtStartDate.setText(trip.getTripDate());
        txtStartTime.setText(trip.getTripTime());
        txtNotes.setText(trip.getNotes());
    }


    private String getTripPlace( double latitude , double longitude) {
        String tripLocation = "";
        try {
            Geocoder geocoder = new Geocoder(this, Locale.getDefault());
            List<Address> address = geocoder.getFromLocation(latitude, longitude, 1);
            tripLocation = address.get(0).getAddressLine(0);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return tripLocation;
    }


    /**
     *
     */
    class MyViewModelFactory implements ViewModelProvider.Factory {
        private PastTripDetailsActivity mActivity;


        public MyViewModelFactory(PastTripDetailsActivity activity) {
            mActivity = activity;
        }


        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            return (T) new PastTripsDetailsViewModel(mActivity);
        }
    }







}
