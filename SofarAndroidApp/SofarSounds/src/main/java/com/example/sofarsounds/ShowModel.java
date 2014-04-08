package com.example.sofarsounds;

/**
 * Created by lucid on 4/8/14.
 */
public class ShowModel {
    public ShowModel(String date, String city) {
        this.date = date;
        this.city = city;
    }

    public ShowModel() {
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    private String city;
    private String date;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ShowModel showModel = (ShowModel) o;

        if (!city.equals(showModel.city)) return false;
        if (!date.equals(showModel.date)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = city.hashCode();
        result = 31 * result + date.hashCode();
        return result;
    }
}
