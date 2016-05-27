package com.example.user.medicalia.remote;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Path;

public interface AppointmentAPI {
    String BASE_URL = "http://medicalia.herokuapp.com/";

    @Headers("Content-Type: application/json")
    @GET("api/appointments/{beacon_uuid}/register")
    Call<ResponseBody> register(
            @Header("Authorization") String token,
            @Path("beacon_uuid") String beacon_uuid);

    @Headers("Content-Type: application/json")
    @GET("api/appointments/{beacon_uuid}/diagnostic_created")
    Call<ResponseBody> diagnostic_created(
            @Header("Authorization") String token,
            @Path("beacon_uuid") String beacon_uuid);


    class Factory {
        public static AppointmentAPI service;

        public static AppointmentAPI getInstance(){
            if (service == null) {

                Retrofit retrofit = new Retrofit.Builder()
                        .addConverterFactory(GsonConverterFactory.create())
                        .baseUrl(BASE_URL)
                        .build();

                service = retrofit.create(AppointmentAPI.class);
            }

            return service;
        }
    }

}
