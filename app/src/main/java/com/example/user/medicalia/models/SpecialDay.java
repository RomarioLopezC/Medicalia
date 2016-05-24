
package com.example.user.medicalia.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class SpecialDay {

    private static final String DATE_FORMAT = "yyy-MM-dd";

    @SerializedName("day")
    @Expose
    private String day;
    @SerializedName("start")
    @Expose
    private String start;
    @SerializedName("end")
    @Expose
    private String end;
    @SerializedName("start_lunch")
    @Expose
    private String startLunch;
    @SerializedName("end_lunch")
    @Expose
    private String endLunch;

    /**
     * 
     * @return
     *     The day
     */
    public String getDay() {
        return day;
    }

    public Calendar getDayCalendar(){
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
        Date date = new Date();
        try {
            date = dateFormat.parse(day);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        calendar.setTime(date);
        return calendar;
    }

    /**
     * 
     * @param day
     *     The day
     */
    public void setDay(String day) {
        this.day = day;
    }

    /**
     * 
     * @return
     *     The start
     */
    public String getStart() {
        return start;
    }

    /**
     * 
     * @param start
     *     The start
     */
    public void setStart(String start) {
        this.start = start;
    }

    /**
     * 
     * @return
     *     The end
     */
    public String getEnd() {
        return end;
    }

    /**
     * 
     * @param end
     *     The end
     */
    public void setEnd(String end) {
        this.end = end;
    }

    /**
     * 
     * @return
     *     The startLunch
     */
    public String getStartLunch() {
        return startLunch;
    }

    /**
     * 
     * @param startLunch
     *     The start_lunch
     */
    public void setStartLunch(String startLunch) {
        this.startLunch = startLunch;
    }

    /**
     * 
     * @return
     *     The endLunch
     */
    public String getEndLunch() {
        return endLunch;
    }

    /**
     * 
     * @param endLunch
     *     The end_lunch
     */
    public void setEndLunch(String endLunch) {
        this.endLunch = endLunch;
    }

}
