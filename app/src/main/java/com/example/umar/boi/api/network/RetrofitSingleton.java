package com.example.umar.boi.api.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitSingleton {

    private static Retrofit retrofit;

    private RetrofitSingleton() {
    }

    public static Retrofit getRetrofitInstance() {
        if (retrofit == null)
            retrofit = new Retrofit.Builder()
                    .baseUrl("http://ec2-54-169-199-5.ap-southeast-1.compute.amazonaws.com:5000/api/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        return retrofit;
    }
}
