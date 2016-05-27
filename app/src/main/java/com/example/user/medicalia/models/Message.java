package com.example.user.medicalia.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

/**
 * Created by romarin on 5/26/16.
 */
@Generated("org.jsonschema2pojo")
public class Message {

    @SerializedName("body")
    @Expose
    private String body;

    /**
     * @return The body
     */
    public String getBody() {
        return body;
    }

    /**
     * @param body The blood_type
     */
    public void setBody(String body) {
        this.body = body;
    }
}
