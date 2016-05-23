package com.example.user.medicalia.remote;

import com.example.user.medicalia.models.Schedule;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Path;

/**
 * Created by USER on 20/05/2016.
 */
public interface ScheduleAPI {
    String BASE_URL = "http://medicalia.herokuapp.com/";

    @Headers("Content-Type: application/json")
    @GET("api/schedules/{doctor_id}")
    Call<Schedule> getSchedule(@Header("Authorization") String token, @Path("doctor_id") String doctor_id);

    class Factory {
        public static ScheduleAPI service;

        public static ScheduleAPI getInstance(){
            if (service == null) {

                Retrofit retrofit = new Retrofit.Builder()
                        .addConverterFactory(GsonConverterFactory.create())
                        .baseUrl(BASE_URL)
                        .build();

                service = retrofit.create(ScheduleAPI.class);
            }

            return service;
        }
    }
}
