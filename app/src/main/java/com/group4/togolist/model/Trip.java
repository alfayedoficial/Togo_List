package com.group4.togolist.model;

import android.location.Location;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

/**
 * Class do :
 * Created by Group 4 ITI (Eng/Bassen - Eng Fatma - Eng Ali)
 */

@Entity
public class Trip {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String tripName;
    private double startLocationLongitude;
    private double startLocationLatitude;
    private double endLocationLongitude;
    private double endLocationLatitude;
    private int startDateYear;
    private int startDateMonth;
    private int startDateDay;
    private int startDateHours;
    private int startDateMinutes;
    private int endDateYear;
    private int endDateMonth;
    private int endDateDay;
    private int endDateHours;
    private int endDateMinutes;
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

    public Trip(String tripName, double startLocationLongitude, double startLocationLatitude, double endLocationLongitude, double endLocationLatitude, int startDateYear, int startDateMonth, int startDateDay, int startDateHours, int startDateMinutes, int endDateYear, int endDateMonth, int endDateDay, int endDateHours, int endDateMinutes, String status, int repetition, boolean isRoundTrip, String notes) {
        this.tripName = tripName;
        this.startLocationLongitude = startLocationLongitude;
        this.startLocationLatitude = startLocationLatitude;
        this.endLocationLongitude = endLocationLongitude;
        this.endLocationLatitude = endLocationLatitude;
        this.startDateYear = startDateYear;
        this.startDateMonth = startDateMonth;
        this.startDateDay = startDateDay;
        this.startDateHours = startDateHours;
        this.startDateMinutes = startDateMinutes;
        this.endDateYear = endDateYear;
        this.endDateMonth = endDateMonth;
        this.endDateDay = endDateDay;
        this.endDateHours = endDateHours;
        this.endDateMinutes = endDateMinutes;
        this.status = status;
        this.repetition = repetition;
        this.isRoundTrip = isRoundTrip;
        this.notes = notes;
    }

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

    public int getStartDateYear() {
        return startDateYear;
    }

    public int getStartDateMonth() {
        return startDateMonth;
    }

    public int getStartDateDay() {
        return startDateDay;
    }

    public int getStartDateHours() {
        return startDateHours;
    }

    public int getStartDateMinutes() {
        return startDateMinutes;
    }

    public int getEndDateYear() {
        return endDateYear;
    }

    public int getEndDateMonth() {
        return endDateMonth;
    }

    public int getEndDateDay() {
        return endDateDay;
    }

    public int getEndDateHours() {
        return endDateHours;
    }

    public int getEndDateMinutes() {
        return endDateMinutes;
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

    public void setStartDateYear(int startDateYear) {
        this.startDateYear = startDateYear;
    }

    public void setStartDateMonth(int startDateMonth) {
        this.startDateMonth = startDateMonth;
    }

    public void setStartDateDay(int startDateDay) {
        this.startDateDay = startDateDay;
    }

    public void setStartDateHours(int startDateHours) {
        this.startDateHours = startDateHours;
    }

    public void setStartDateMinutes(int startDateMinutes) {
        this.startDateMinutes = startDateMinutes;
    }

    public void setEndDateYear(int endDateYear) {
        this.endDateYear = endDateYear;
    }

    public void setEndDateMonth(int endDateMonth) {
        this.endDateMonth = endDateMonth;
    }

    public void setEndDateDay(int endDateDay) {
        this.endDateDay = endDateDay;
    }

    public void setEndDateHours(int endDateHours) {
        this.endDateHours = endDateHours;
    }

    public void setEndDateMinutes(int endDateMinutes) {
        this.endDateMinutes = endDateMinutes;
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

