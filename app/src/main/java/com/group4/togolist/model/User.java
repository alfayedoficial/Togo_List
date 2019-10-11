package com.group4.togolist.model;

import java.util.ArrayList;

public class User {

    private String name;
    private String email;
    private String photoUrl;
    private String password;
    private ArrayList<Trip> trips;

    //Three User Constructors

    /**
     * POJO constructor using user name, email, photo, password and list of trips
     */
    public User(String name, String email, String photoUrl, String password, ArrayList<Trip> trips) {
        this.name = name;
        this.email = email;
        this.photoUrl = photoUrl;
        this.password = password;
        this.trips = trips;
    }

    /**
     * POJO constructor using user name, email, password and list of trips
     */
    public User(String name, String email, String password, ArrayList<Trip> trips) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.trips = trips;
    }

    /**
     * POJO constructor using user name, password and list of trips
     */
    public User(String name, String password, ArrayList<Trip> trips) {
        this.name = name;
        this.password = password;
        this.trips = trips;
    }

    /**
     * Getters
     */
    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public String getPassword() {
        return password;
    }

    public ArrayList<Trip> getTrips() {
        return trips;
    }

    /**
     * Setters
     */
    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setTrips(ArrayList<Trip> trips) {
        this.trips = trips;
    }
}
