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

    public List<String> getRecentCities() {
        return recentCities;
    }

    public void setRecentCities(List<String> recentCities) {
        this.recentCities = recentCities;
    }

    private List<String> recentCities = Arrays.asList("LA", "Los Angeles", "New York", "Boston");

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

    public boolean isRecentCity(String city) {
        return this.recentCities.contains(city);
    }
    public boolean isWaitlistedFor(ShowModel showModel) {
        return waitlisted.contains(showModel);
    }
    public boolean isRegisteredFor(ShowModel show) {
        return registered.contains(show);
    }
    public boolean isInteterestedIn(ShowModel show) {
        return interested.contains(show);
    }
}
