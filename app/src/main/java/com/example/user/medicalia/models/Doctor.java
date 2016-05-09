package com.example.user.medicalia.models;

/**
 * Created by USER on 08/05/2016.
 */
public class Doctor {
    private String name;
    private String picture;

    public Doctor(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }
}
