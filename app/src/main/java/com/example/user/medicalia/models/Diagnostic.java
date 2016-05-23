
package com.example.user.medicalia.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class Diagnostic {

    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("treatment")
    @Expose
    private String treatment;
    @SerializedName("date_time")
    @Expose
    private String dateTime;
    @SerializedName("doctor")
    @Expose
    private String doctor;

    /**
     * 
     * @return
     *     The description
     */
    public String getDescription() {
        return description;
    }

    /**
     * 
     * @param description
     *     The description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * 
     * @return
     *     The treatment
     */
    public String getTreatment() {
        return treatment;
    }

    /**
     * 
     * @param treatment
     *     The treatment
     */
    public void setTreatment(String treatment) {
        this.treatment = treatment;
    }

    /**
     * 
     * @return
     *     The dateTime
     */
    public String getDateTime() {
        return dateTime;
    }

    /**
     * 
     * @param dateTime
     *     The date_time
     */
    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    /**
     * 
     * @return
     *     The doctor
     */
    public String getDoctor() {
        return doctor;
    }

    /**
     * 
     * @param doctor
     *     The doctor
     */
    public void setDoctor(String doctor) {
        this.doctor = doctor;
    }

}
