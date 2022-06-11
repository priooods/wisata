package com.prio.pariwisata.service;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Service {

    // local = http://192.168.175.161/Angeline_Laravel/public/
    public static final String BASE_URL = "http://192.168.175.161/Angeline_Laravel/public/";
    private static Retrofit retrofit = null;
    public static Retrofit getClient() {
        if (retrofit == null) {

            OkHttpClient client = new OkHttpClient.Builder()
                    .readTimeout(100, TimeUnit.SECONDS)
                    .connectTimeout(100,TimeUnit.SECONDS)
                    .build();

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
