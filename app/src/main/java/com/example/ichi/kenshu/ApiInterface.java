package com.example.ichi.kenshu;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {
    String END_POINT = "http://54.238.252.116";

    @Headers({
            "Accept: application/json",
            "Content-type: application/json",
            //"Authorization: "
    })
    @POST("/signup")
    Call<User> createUser(@Body User user);

    @POST("/login")
    Call<User> login(@Body User user);

    @GET("/books")
    Call<Book> getBook(
            @Query("page") String page,
            @Query("user_id") Integer user_id);

    @FormUrlEncoded
    @POST("/books")
    Call<Book> addBook(
            @Field("name") String name,
            @Field("price") Integer price,
            @Field("purchase_date") String purchase_date,
            @Field("image_data") String image_data,
            @Field("user_id") Integer user_id);

    @FormUrlEncoded
    @PATCH("/books/{id}")
    Call<Book> editBook(
            @Path("id") Integer id,
            @Field("name") String name,
            @Field("price") Integer price,
            @Field("purchase_date") String purchase_date,
            @Field("image_data") String image_data);
}
