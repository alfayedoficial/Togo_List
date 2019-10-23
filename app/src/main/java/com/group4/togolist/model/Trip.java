package com.group4.togolist.model;


import java.util.Calendar;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

/**
 * Class do :
 * Created by Group 4 ITI (Eng/Bassem - Eng Fatma - Eng Ali)
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
    private int roundTripDateYear;
    private int roundTripDateMonth;
    private int roundTripDateDay;
    private int roundTripDateHours;

    public int getRoundTripDateYear() {
        return roundTripDateYear;
    }

    public int getRoundTripDateMonth() {
        return roundTripDateMonth;
    }

    public int getRoundTripDateDay() {
        return roundTripDateDay;
    }

    public int getRoundTripDateHours() {
        return roundTripDateHours;
    }

    public int getRoundTripDateMinutes() {
        return roundTripDateMinutes;
    }

    private int roundTripDateMinutes;
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

    public Trip( String tripName, double startLocationLongitude, double startLocationLatitude, double endLocationLongitude, double endLocationLatitude, String status, int repetition, boolean isRoundTrip, String notes) {
        this.tripName = tripName;
        this.startLocationLongitude = startLocationLongitude;
        this.startLocationLatitude = startLocationLatitude;
        this.endLocationLongitude = endLocationLongitude;
        this.endLocationLatitude = endLocationLatitude;
        this.status = status;
        this.repetition = repetition;
        this.isRoundTrip = isRoundTrip;
        this.notes = notes;
    }

    @Ignore
    public Trip(int id, String tripName, double startLocationLongitude, double startLocationLatitude, double endLocationLongitude, double endLocationLatitude, int startDateYear, int startDateMonth, int startDateDay, int startDateHours, int startDateMinutes, int roundTripDateYear, int roundTripDateMonth, int roundTripDateDay, int roundTripDateHours, int roundTripDateMinutes, String status, int repetition, boolean isRoundTrip, String notes) {
        this.id = id;
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
        this.roundTripDateYear = roundTripDateYear;
        this.roundTripDateMonth = roundTripDateMonth;
        this.roundTripDateDay = roundTripDateDay;
        this.roundTripDateHours = roundTripDateHours;
        this.roundTripDateMinutes = roundTripDateMinutes;
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

    public Calendar getRoundTime() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, getRoundTripDateYear());
        cal.set(Calendar.MONTH, getRoundTripDateMonth());
        cal.set(Calendar.DAY_OF_MONTH, getRoundTripDateDay());
        cal.set(Calendar.HOUR_OF_DAY, getRoundTripDateHours());
        cal.set(Calendar.MINUTE, getRoundTripDateMinutes());
        return cal;
    }

    public void setRoundTime(Calendar cal) {
        setRoundTripDateYear(cal.get(Calendar.YEAR));
        setRoundTripDateMonth(cal.get(Calendar.MONTH));
        setRoundTripDateDay(cal.get(Calendar.DAY_OF_MONTH));
        setRoundTripDateHours(cal.get(Calendar.HOUR_OF_DAY));
        setRoundTripDateMinutes(cal.get(Calendar.MINUTE));
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

    public String getTripDate() {
        return startDateDay + " / " + startDateMonth + " / " + startDateYear;
    }

    public String getTripTime() {
        return startDateHours + " : " + startDateMinutes;
    }

    public void setRoundTripDateYear(int roundTripDateYear) {
        this.roundTripDateYear = roundTripDateYear;
    }

    public void setRoundTripDateMonth(int roundTripDateMonth) {
        this.roundTripDateMonth = roundTripDateMonth;
    }

    public void setRoundTripDateDay(int roundTripDateDay) {
        this.roundTripDateDay = roundTripDateDay;
    }

    public void setRoundTripDateHours(int roundTripDateHours) {
        this.roundTripDateHours = roundTripDateHours;
    }

    public void setRoundTripDateMinutes(int roundTripDateMinutes) {
        this.roundTripDateMinutes = roundTripDateMinutes;
    }
}

