package com.example.user.medicalia.Utils;

import com.example.user.medicalia.models.Patient;
import com.google.gson.Gson;

public class Utils {

    public static Patient toUserAtributtes(String json){
        Gson gson = new Gson();
        Patient patient = gson.fromJson(json, Patient.class);
        return patient;
    }
}
