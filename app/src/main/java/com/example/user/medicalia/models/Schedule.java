
package com.example.user.medicalia.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class Schedule {

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
    @SerializedName("special_days")
    @Expose
    private List<SpecialDay> specialDays = new ArrayList<SpecialDay>();
    @SerializedName("appointments")
    @Expose
    private List<Appointment> appointments = new ArrayList<Appointment>();

    public Schedule(String start, String end, String startLunch, String endLunch, List<SpecialDay> specialDays, List<Appointment> appointments) {
        this.start = start;
        this.end = end;
        this.startLunch = startLunch;
        this.endLunch = endLunch;
        this.specialDays = specialDays;
        this.appointments = appointments;
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

    /**
     * 
     * @return
     *     The specialDays
     */
    public List<SpecialDay> getSpecialDays() {
        return specialDays;
    }

    /**
     * 
     * @param specialDays
     *     The special_days
     */
    public void setSpecialDays(List<SpecialDay> specialDays) {
        this.specialDays = specialDays;
    }

    /**
     * 
     * @return
     *     The appointments
     */
    public List<Appointment> getAppointments() {
        return appointments;
    }

    /**
     * 
     * @param appointments
     *     The appointments
     */
    public void setAppointments(List<Appointment> appointments) {
        this.appointments = appointments;
    }

}
