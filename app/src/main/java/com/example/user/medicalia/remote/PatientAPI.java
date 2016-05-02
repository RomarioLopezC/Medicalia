package com.example.user.medicalia.remote;

import com.example.user.medicalia.models.Patient;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by Kev' on 02/05/2016.
 */
public interface PatientAPI {
    String BASE_URL = "http://medicalia.herokuapp.com/";

    @Headers("Content-Type: application/json")
    @POST("api/patients")
    Call<ResponseBody> register(@Body Patient patient);

    class Factory {
        public static PatientAPI service;

        public static PatientAPI getInstance(){
            if (service == null) {

                Retrofit retrofit = new Retrofit.Builder()
                        .addConverterFactory(GsonConverterFactory.create())
                        .baseUrl(BASE_URL)
                        .build();

                service = retrofit.create(PatientAPI.class);
            }

            return service;
        }
    }
}
