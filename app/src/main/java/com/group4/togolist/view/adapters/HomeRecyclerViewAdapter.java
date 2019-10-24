package com.group4.togolist.view.adapters;

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
import com.group4.togolist.model.Trip;

import java.util.List;
import java.util.Locale;

public class HomeRecyclerViewAdapter extends RecyclerView.Adapter {

    private OnItemListener onItemListener;
    private List<Trip> upcomingTrip;
    private Context context;

    public HomeRecyclerViewAdapter(OnItemListener onItemListener, Context context) {
        this.onItemListener = onItemListener;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardrecyclerview_upcoming , parent,false);
        return  new MyViewHolder(itemView , onItemListener);
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

        int count = 0;
        if(upcomingTrip != null)
            count = upcomingTrip.size();
        return count;
    }



    private class MyViewHolder extends RecyclerView.ViewHolder implements  View.OnClickListener{

        private TextView txtTripPlace , txtTripName , txtTripDate , txtTripTime;
        private ImageButton imgBtnDetails;
        private ImageView imgViewDelete ;
        OnItemListener onItemListener;

        public MyViewHolder(@NonNull View itemView , OnItemListener onclickLis) {
            super(itemView);

            txtTripName = itemView.findViewById(R.id.textViewTripName);
            txtTripPlace = itemView.findViewById(R.id.textViewTripPlace);
            txtTripDate = itemView.findViewById(R.id.textViewTripCalender);
            txtTripTime = itemView.findViewById(R.id.textViewPastTripTime);
            imgBtnDetails = itemView.findViewById(R.id.imageBtnDetails);
            imgViewDelete = itemView.findViewById(R.id.imageView3);

            imgViewDelete.setOnClickListener(this);

            this.onItemListener = onclickLis;
            //itemView.setOnClickListener(this);
            imgBtnDetails.setOnClickListener(this);
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

    public void setUpcomingTrip(List<Trip> upcomingTrip) {
        this.upcomingTrip = upcomingTrip;
        notifyDataSetChanged();
    }
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
