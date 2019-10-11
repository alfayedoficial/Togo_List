package com.group4.togolist.model;

import android.location.Location;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity
public class Trip {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String tripName;
    private double startLocationLongitude;
    private double startLocationLatitude;
    private double endLocationLongitude;
    private double endLocationLatitude;
    private Date startDate;
    private Date endDate;
    private String status;
    private int repetition;
    private boolean isRoundTrip;
    private String notes;

    // possible values for Trip Status
    public static final String UPCOMING = "upcoming";
    public static final String IN_PROGRESS = "In Progress";
    public static final String ENDED = "Ended";
    public static final String DELAYED = "Delayed";
    public static final String CANCELED = "Canceled";
    //possible values for Trip Repetition
    public static final int NOT_REPEATED = 1001;
    public static final int DAILY = 1002;
    public static final int WEEKLY = 1003;
    public static final int MONTHLY = 1004;

    public int getId() {
        return id;
    }

    public String getTripName() {
        return tripName;
    }

    public double getStartLocationLongitude() {
        return startLocationLongitude;
    }

    public double getStartLocationLatitude() {
        return startLocationLatitude;
    }

    public double getEndLocationLongitude() {
        return endLocationLongitude;
    }

    public double getEndLocationLatitude() {
        return endLocationLatitude;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public String getStatus() {
        return status;
    }

    public int getRepetition() {
        return repetition;
    }

    public boolean isRoundTrip() {
        return isRoundTrip;
    }

    public String getNotes() {
        return notes;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTripName(String tripName) {
        this.tripName = tripName;
    }

    public void setStartLocationLongitude(double startLocationLongitude) {
        this.startLocationLongitude = startLocationLongitude;
    }

    public void setStartLocationLatitude(double startLocationLatitude) {
        this.startLocationLatitude = startLocationLatitude;
    }

    public void setEndLocationLongitude(double endLocationLongitude) {
        this.endLocationLongitude = endLocationLongitude;
    }

    public void setEndLocationLatitude(double endLocationLatitude) {
        this.endLocationLatitude = endLocationLatitude;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setRepetition(int repetition) {
        this.repetition = repetition;
    }

    public void setRoundTrip(boolean roundTrip) {
        isRoundTrip = roundTrip;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}

