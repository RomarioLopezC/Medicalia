
package com.example.user.medicalia.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class Appointment {

    private static final String DATE_FORMAT = "yyy-MM-dd'T'HH:mm";

    @SerializedName("start_time")
    @Expose
    private String startTime;

    /**
     * 
     * @return
     *     The startTime
     */
    public String getStartTime() {
        return startTime;
    }

    /**
     * 
     * @param startTime
     *     The start_time
     */
    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public Calendar getStartTimeCalendar(){
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
        Date date = new Date();
        try {
            date = dateFormat.parse(startTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        calendar.setTime(date);
        return calendar;
    }

}
