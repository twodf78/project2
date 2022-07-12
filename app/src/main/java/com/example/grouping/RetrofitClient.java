package com.example.grouping;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static final String URL = "http://192.249.19.184:443/";

    public static APIService getAPIService(){
        return getInstance().create(APIService.class);
    }

    private static Retrofit getInstance(){
        Gson gson= new GsonBuilder()
                .setLenient()
                .create();

        return new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }

}
