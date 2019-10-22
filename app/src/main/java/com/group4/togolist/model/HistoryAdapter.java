package com.group4.togolist.model;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.group4.togolist.R;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryHolder> {

    private OnItemListener onItemListener;
    private List<Trip> pastTrips;
    private Context context;

    public HistoryAdapter(Context context,OnItemListener onItemListener){
        this.context = context;
        this.onItemListener = onItemListener;
    }


    @NonNull
    @Override
    public HistoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater =LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.cardrecyclerview_upcoming,parent,false);
        HistoryHolder historyHolder =new HistoryHolder(view,onItemListener);
        return historyHolder;
    }

    public void setPastTrips(List<Trip> pastTrips) {
        this.pastTrips = pastTrips;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryHolder holder, int position) {
        Trip trip = pastTrips.get(position);

        holder.getTxtTripName().setText(trip.getTripName());
        holder.getTxtTripPlace().setText(getTripPlace(trip));
        holder.getTxtTripDate().setText(trip.getTripDate());
        holder.getTxtTripTime().setText(trip.getTripTime());
    }

    @Override
    public int getItemCount() {
        int count = 0;
        if(pastTrips != null)
            count = pastTrips.size();
        return count;
    }

    public class HistoryHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView txtTripName, txtTripPlace, txtTripDate, txtTripTime;
        private OnItemListener onItemListener;
        private ImageButton imgBtnDetails;
        private ImageView imgViewDelete ;

        public HistoryHolder(@NonNull View itemView, OnItemListener onItemListener) {
            super(itemView);
            txtTripName = itemView.findViewById(R.id.textViewTripName);
            txtTripPlace = itemView.findViewById(R.id.textViewTripPlace);
            txtTripDate = itemView.findViewById(R.id.textViewTripCalender);
            txtTripTime = itemView.findViewById(R.id.textViewPastTripTime);

            imgBtnDetails = itemView.findViewById(R.id.imageBtnDetails);
            imgViewDelete = itemView.findViewById(R.id.imageView3);

            this.onItemListener = onItemListener;
            imgViewDelete.setOnClickListener(this);
            imgBtnDetails.setOnClickListener(this);

           // itemView.setOnClickListener(this);
        }

        public TextView getTxtTripName() {
            return txtTripName;
        }

        public TextView getTxtTripPlace() {
            return txtTripPlace;
        }

        public TextView getTxtTripDate() {
            return txtTripDate;
        }

        public TextView getTxtTripTime() {
            return txtTripTime;
        }

        @Override
        public void onClick(View v) {

            switch (v.getId()){
                case R.id.imageView3:
                    onItemListener.onItemDeleteClick(getAdapterPosition());
                    break;
                case R.id.imageBtnDetails:
                    onItemListener.onItemClick(getAdapterPosition());
                    break;

            }
        }
    }

    /**
     *  communicator interface
     */
    public interface OnItemListener{
        void onItemClick(int position);
        void onItemDeleteClick( int position);
    }

    private String getTripPlace(Trip currentTrip){
        String tripLocation = "";
        try{
            Geocoder geocoder = new Geocoder(context, Locale.getDefault());
            List<Address> address = geocoder.getFromLocation(currentTrip.getEndLocationLatitude(),currentTrip.getEndLocationLongitude(),1);
            tripLocation =address.get(0).getAddressLine(0);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return tripLocation ;
    }
}
