package com.example.sofarsounds;

import java.util.Arrays;
import java.util.List;

/**
 * Created by lucid on 4/8/14.
 */
public class ProfileModel {

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String name = "";

    public boolean isSpecialCity(String city) {
        return this.specialCities.contains(city);
    }
    public List<String> getSpecialCities() {
        return specialCities;
    }

    public void setSpecialCities(List<String> specialCities) {
        this.specialCities = specialCities;
    }

    private List<String> specialCities = Arrays.asList("LA", "Los Angeles", "New York");

    public String getHomeCity() {
        return homeCity;
    }

    public void setHomeCity(String homeCity) {
        this.homeCity = homeCity;
    }

    private String homeCity = "";
    public ProfileModel() {
    }

    public List<ShowModel> getInterested() {
        return interested;
    }

    public void setInterested(List<ShowModel> interested) {
        this.interested = interested;
    }

    public List<ShowModel> getRegistered() {
        return registered;
    }

    public void setRegistered(List<ShowModel> registered) {
        this.registered = registered;
    }

    public List<ShowModel> getWaitlisted() {
        return waitlisted;
    }

    public void setWaitlisted(List<ShowModel> waitlisted) {
        this.waitlisted = waitlisted;
    }

    public List<ShowModel> getShows() {
        return shows;
    }

    public void setShows(List<ShowModel> shows) {
        this.shows = shows;
    }

    private List<ShowModel> interested;
    private List<ShowModel> registered;
    private List<ShowModel> waitlisted;
    private List<ShowModel> shows;
}
