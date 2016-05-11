package com.example.user.medicalia.Utils;

import com.example.user.medicalia.models.UserAttributes;
import com.google.gson.Gson;

/**
 * Created by USER on 10/05/2016.
 */
public class Utils {

    public static UserAttributes toUserAtributtes(String json){
        Gson gson = new Gson();
        UserAttributes user = gson.fromJson(json, UserAttributes.class);
        return user;
    }
}
