package com.group4.togolist.model;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Calendar;

public class UserTrip {

    // possible values for Trip Status
    public static final String UPCOMING = "upcoming";
    public static final String ENDED = "Ended";
    public static final String CANCELED = "Canceled";
    //possible values for Trip Repetition
    public static final int NOT_REPEATED = 1001;
    public static final int DAILY = 1002;
    public static final int WEEKLY = 1003;
    public static final int MONTHLY = 1004;

    private String tripId;
    private String userId;
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
    private String status;
    private int repetition;
    private boolean isRoundTrip;
    private String notes;

    public UserTrip() {
        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
    }

    public String getTripId() {
        return tripId;
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

    public void setUserId(String userId) {
        this.userId = userId;
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

    public Calendar getStartTime() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, getStartDateYear());
        cal.set(Calendar.MONTH, getStartDateMonth());
        cal.set(Calendar.DAY_OF_MONTH, getStartDateDay());
        cal.set(Calendar.HOUR_OF_DAY, getStartDateHours());
        cal.set(Calendar.MINUTE, getStartDateMinutes());
        return cal;
    }

    public void setStartTime(Calendar cal) {
        setStartDateYear(cal.get(Calendar.YEAR));
        setStartDateMonth(cal.get(Calendar.MONTH));
        setStartDateDay(cal.get(Calendar.DAY_OF_MONTH));
        setStartDateHours(cal.get(Calendar.HOUR_OF_DAY));
        setStartDateMinutes(cal.get(Calendar.MINUTE));
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

    public void setTripId(String tripId) {
        this.tripId = tripId;
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
