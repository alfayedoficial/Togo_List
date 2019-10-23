package com.group4.togolist.model;

import androidx.lifecycle.LiveData;

import java.util.ArrayList;
import java.util.List;

/**
 * Class do :
 * Created by Group 4 ITI (Eng/Bassem - Eng Fatma - Eng Ali)
 */

public class User {

    private String name;
    private String email;
    private String photoUrl;
    private String password;
    private LiveData<List<Trip>> trips;
    private static User user;

    //Three User Constructors

    /**
     * POJO constructor using user name, email, photo, password and list of trips
     */
    private User(String name, String email, String photoUrl, String password, LiveData<List<Trip>> trips) {
        this.name = name;
        this.email = email;
        this.photoUrl = photoUrl;
        this.password = password;
        this.trips = trips;
    }

    /**
     * POJO constructor using user name, email, password and list of trips
     */
    private User(String name, String email, String password, LiveData<List<Trip>> trips) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.trips = trips;
    }

    /**
     * POJO constructor using user name, password and list of trips
     */
    private User(String name, String password, LiveData<List<Trip>> trips) {
        this.name = name;
        this.password = password;
        this.trips = trips;
    }

    private User(String name, String email,String password ) {
        this.name = name;
        this.email = email;
        this.photoUrl = photoUrl;
        this.password = password;
        this.trips = trips;
    }
    private User(String name, String email) {
        this.name = name;
        this.email = email;
        this.photoUrl = photoUrl;
        this.password = password;
        this.trips = trips;
    }
    /**
     * user Static Constructor
     */

    public static User getUserInstance(){
        return user;
    }

    public static User getUserInstance(String username, String email){
        user.setName(username);
        user.setEmail(email);
        return user;
    }

    public static User getUserInstance(String name, String email, String photoUrl, String password, LiveData<List<Trip>> trips){
        user = new User(name, email, photoUrl, password, trips);
        return user;
    }

    public static User getUserInstance(String name, String email, String password, LiveData<List<Trip>> trips){
        user = new User(name, email, password, trips);
        return user;
    }

    public static User getUserInstance(String name,  String password, LiveData<List<Trip>> trips){
        user = new User(name, password, trips);
        return user;
    }

    public static User getUserInstance(String name,String email,String password){
        user = new User(name,email,password);
        return user;
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

    public LiveData<List<Trip>> getTrips() {
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

    public void setTrips(LiveData<List<Trip>> trips) {
        this.trips = trips;
    }
}
