package com.example.user.medicalia.models;

/**
 * Created by USER on 22/05/2016.
 */
public class Hour {

    private String hour;
    private String hour_format;
    private boolean availabe;

    public Hour(String hour, String hour_format, boolean availabe) {
        this.hour = hour;
        this.hour_format = hour_format;
        this.availabe = availabe;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public String getHour_format() {
        return hour_format;
    }

    public void setHour_format(String hour_format) {
        this.hour_format = hour_format;
    }

    public boolean isAvailabe() {
        return availabe;
    }

    public void setAvailabe(boolean availabe) {
        this.availabe = availabe;
    }
}
