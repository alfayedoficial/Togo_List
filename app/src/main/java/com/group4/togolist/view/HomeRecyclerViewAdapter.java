package com.group4.togolist.view;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.group4.togolist.R;
import com.group4.togolist.model.Trip;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

public class HomeRecyclerViewAdapter extends RecyclerView.Adapter {

    private OnClickLisenter adptOnClickLisenter;
    private Context context ;
    private List<Trip> upcomingTrip ;



    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardrecyclerview_upcoming , parent,false);
        return  new MyViewHolder(itemView , adptOnClickLisenter);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MyViewHolder myHolder = (MyViewHolder)holder;

        myHolder.txtTripName.setText(upcomingTrip.get(position).getTripName());
        myHolder.txtTripPlace.setText(getTripPlace(upcomingTrip.get(position)));
        myHolder.txtTripDate.setText(upcomingTrip.get(position).getTripDate());
        myHolder.txtTripTime.setText(upcomingTrip.get(position).getTripTime());

    }

    @Override
    public int getItemCount() {
        return upcomingTrip.size();
    }



    private class MyViewHolder extends RecyclerView.ViewHolder implements  View.OnClickListener{

       private TextView txtTripPlace , txtTripName , txtTripDate , txtTripTime;
       private ImageButton imgBtnDetails;
        OnClickLisenter onClickLisenter;

        public MyViewHolder(@NonNull View itemView , OnClickLisenter onclickLis) {
            super(itemView);

            txtTripName = itemView.findViewById(R.id.textViewTripName);
            txtTripPlace = itemView.findViewById(R.id.textViewTripPlace);
            txtTripDate = itemView.findViewById(R.id.textViewTripCalender);
            txtTripTime = itemView.findViewById(R.id.textViewPastTripTime);
            imgBtnDetails = itemView.findViewById(R.id.imageBtnDetails);


            onClickLisenter = onclickLis;
            imgBtnDetails.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            onClickLisenter.onDetailClick(upcomingTrip.get(getAdapterPosition()).getTripName());
        }
    }

    public void setUpcomingTrip(List<Trip> upcomingTrip) {
        this.upcomingTrip = upcomingTrip;
    }
    public interface OnClickLisenter{

        public  void onDetailClick(String tripName);
    }

    private String getTripPlace(Trip currentTrip){
        String tripLocation = "";
     try{
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        List<Address> address = geocoder.getFromLocation(currentTrip.getEndLocationLatitude(),currentTrip.getEndLocationLongitude(),1);
        tripLocation =address.get(0).getAddressLine(0);

     } catch (IOException e) {
         e.printStackTrace();
     }
        return tripLocation ;
    }
}