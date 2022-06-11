package com.prio.pariwisata.service;

import com.prio.pariwisata.calling.Calling_Carousel;
import com.prio.pariwisata.calling.Calling_Menu;
import com.prio.pariwisata.calling.Calling_User;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface Client {

    @FormUrlEncoded
    @POST("api/auth/login")
    Call<Calling_User> UserLogin(
            @Field("username") String username,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("api/auth/register")
    Call<Calling_User> UserRegister(
            @Field("username") String username,
            @Field("password") String password,
            @Field("email") String email
    );

    @GET("api/auth/me")
    Call<Calling_User> UserMe(@Header("Authorization") String token);

    // Carousel
    @GET("api/pariwisata/carousel/show")
    Call<Calling_Carousel> get_carousel(@Header("Authorization") String token);


    // Menu
    @GET("api/pariwisata/categori/show")
    Call<Calling_Menu> get_menu(@Header("Authorization") String token);
}
