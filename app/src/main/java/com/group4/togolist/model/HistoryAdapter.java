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
import androidx.recyclerview.widget.RecyclerView;

import com.group4.togolist.R;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryHolder> {

    private OnItemListener onItemListener;
    private List<Trip> pastTrips;
    private Context context;

    public HistoryAdapter(Context context,List<Trip> pastTrips,OnItemListener onItemListener){
        this.context = context;
        this.pastTrips = pastTrips;
        this.onItemListener = onItemListener;
    }


    @NonNull
    @Override
    public HistoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater =LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.cardrecyclerview_pasttrip,parent,false);
        HistoryHolder historyHolder =new HistoryHolder(view,onItemListener);
        return historyHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryHolder holder, int position) {
        Trip trip = pastTrips.get(position);
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        List<Address> address = null;
        try {
            address = geocoder.getFromLocation(trip.getEndLocationLatitude(),trip.getEndLocationLongitude(),1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String tripLocation =address.get(0).getAddressLine(0);
        holder.getTxtTripName().setText(trip.getTripName());
        holder.getTxtTripPlace().setText(tripLocation);
        holder.getTxtTripDate().setText(trip.getTripDate());
        holder.getTxtTripTime().setText(trip.getTripTime());
    }

    @Override
    public int getItemCount() {
        return pastTrips.size();
    }

    public class HistoryHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView txtTripName, txtTripPlace, txtTripDate, txtTripTime;
        private OnItemListener onItemListener;
        private ImageButton imgBtnDetails;
        private ImageView imgViewDelete ;

        public HistoryHolder(@NonNull View itemView, OnItemListener onItemListener) {
            super(itemView);
            txtTripName = itemView.findViewById(R.id.textViewPastTripName);
            txtTripPlace = itemView.findViewById(R.id.textViewPastTripPlace);
            txtTripDate = itemView.findViewById(R.id.textViewPastTripCalender);
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

    public interface OnItemListener{
        void onItemClick(int position);
        void onItemDeleteClick( int position);
    }
}
