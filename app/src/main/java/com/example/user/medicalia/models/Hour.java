package com.example.user.medicalia.models;

public class Hour {

    private String hour;
    private String hour_format;
    private String info;

    public Hour(String hour, String hour_format, String info) {
        this.hour = hour;
        this.hour_format = hour_format;
        this.info = info;
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

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
