package com.example.ichi.kenshu;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {
    String END_POINT = "http://54.238.252.116";

    @Headers({
            "Accept: application/json",
            "Content-type: application/json"
    })
    @POST("/signup")
    Call<User> createUser(@Body User user);

    @Headers({
            "Accept: application/json",
            "Content-type: application/json"
    })
    @POST("/login")
    Call<User> login(@Body User user);

    @Headers({
            "Accept: application/json",
            "Content-type: application/json"
    })
    @GET("/books")
    Call<BookResponse> getBook(
            @Query("page") String page,
            @Query("user_id") Integer user_id
    );

    @FormUrlEncoded
    @Headers({
            "Accept: application/json",
            "Content-type: application/json"
    })
    @POST("/books")
    Call<Book> addBook(
            @Header("Authorization") String request_token,
            //@Body Book book
            @Field(value = "name", encoded = true) String name,
            @Field(value = "price", encoded = true) int price,
            @Field(value = "purchase_date", encoded = true) String purchase_date,
            @Field(value = "image_data", encoded = true) String image_data,
            @Field(value = "user_id", encoded = true) int user_id
    );

    @Headers({
            "Accept: application/json",
            "Content-type: application/json"
    })
    @FormUrlEncoded
    @PATCH("/books/{id}")
    Call<Book> editBook(
            @Path("id") Integer id,
            @Field("name") String name,
            @Field("price") Integer price,
            @Field("purchase_date") String purchase_date,
            @Field("image_data") String image_data
    );
}