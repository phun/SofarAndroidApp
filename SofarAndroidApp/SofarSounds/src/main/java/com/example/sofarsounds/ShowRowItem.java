package com.example.sofarsounds;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by lucid on 4/8/14.
 */
public class ShowRowItem {
    private int imageId;
    private String city;
    private String date;

    public Date getDateObj() {
        return dateObj;
    }

    public void setDateObj(Date dateObj) {
        this.dateObj = dateObj;
    }

    private Date dateObj;

    public ShowRowItem(String city, String date) {
        this.city = city;
        this.date = date;
    }

    public ShowRowItem(int imageId, String city, String date) {
        this.imageId = imageId;
        this.city = city;
        this.date = date;

        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");

        try {
            this.dateObj = formatter.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }
    public int getIconId() {
        return imageId;
    }
    public void setIconId(int imageId) {
        this.imageId = imageId;
    }
    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }

    public boolean hasIcon() {
        return getIconId() != 0;
    }
    @Override
    public String toString() {
        return city + "\n" + date;
    }
}