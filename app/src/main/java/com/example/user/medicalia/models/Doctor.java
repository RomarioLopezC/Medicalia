package com.example.user.medicalia.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class Doctor {

    @SerializedName("rfc")
    @Expose
    private String rfc;
    @SerializedName("specialty")
    @Expose
    private String specialty;
    @SerializedName("hospital")
    @Expose
    private String hospital;
    @SerializedName("office")
    @Expose
    private String office;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("office_phone_number")
    @Expose
    private Object officePhoneNumber;
    @SerializedName("user_attributes")
    @Expose
    private UserAttributes userAttributes;

    /**
     *
     * @return
     * The rfc
     */
    public String getRfc() {
        return rfc;
    }

    /**
     *
     * @param rfc
     * The rfc
     */
    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    /**
     *
     * @return
     * The specialty
     */
    public String getSpecialty() {
        return specialty;
    }

    /**
     *
     * @param specialty
     * The specialty
     */
    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    /**
     *
     * @return
     * The hospital
     */
    public String getHospital() {
        return hospital;
    }

    /**
     *
     * @param hospital
     * The hospital
     */
    public void setHospital(String hospital) {
        this.hospital = hospital;
    }

    /**
     *
     * @return
     * The office
     */
    public String getOffice() {
        return office;
    }

    /**
     *
     * @param office
     * The office
     */
    public void setOffice(String office) {
        this.office = office;
    }

    /**
     *
     * @return
     * The address
     */
    public String getAddress() {
        return address;
    }

    /**
     *
     * @param address
     * The address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     *
     * @return
     * The officePhoneNumber
     */
    public Object getOfficePhoneNumber() {
        return officePhoneNumber;
    }

    /**
     *
     * @param officePhoneNumber
     * The office_phone_number
     */
    public void setOfficePhoneNumber(Object officePhoneNumber) {
        this.officePhoneNumber = officePhoneNumber;
    }

    /**
     *
     * @return
     * The user
     */
    public UserAttributes getUserAttributes() {
        return userAttributes;
    }

    /**
     *
     * @param userAttributes
     * The user
     */
    public void setUserAttributes(UserAttributes userAttributes) {
        this.userAttributes = userAttributes;
    }

}
