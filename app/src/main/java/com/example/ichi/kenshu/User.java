package com.example.ichi.kenshu;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User {

    @Expose
    @SerializedName("email")
    private String email;

    @Expose
    @SerializedName("password")
    private String password;

    @Expose
    @SerializedName("user_id")
    private int user_id;

    @Expose
    @SerializedName("request_token")
    private String request_token;


    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getUserId() {
        return user_id;
    }

    public  void setUserId(int userId){
        this.user_id = userId;
    }

    public String getRequestToken() {
        return request_token;
    }

    public void setRequestToken(String requestToken) {
        this.request_token = requestToken;
    }
}
