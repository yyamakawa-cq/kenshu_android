package com.example.ichi.kenshu;

import retrofit2.Call;
import retrofit2.http.Body;
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
            @Query("user_id") int user_id
    );

    @Headers({
            "Accept: application/json",
            "Content-type: application/json"
    })
    @POST("/books")
    Call<Book> addBook(
            @Header("Authorization") String request_token,
            @Body Book book
    );

    @Headers({
            "Accept: application/json",
            "Content-type: application/json"
    })
    @PATCH("/books/{id}")
    Call<Book> editBook(
            @Path("id") int id,
            @Header("Authorization") String request_token,
            @Body Book book
    );
}
