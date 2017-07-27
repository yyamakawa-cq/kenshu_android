package com.example.ichi.kenshu;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User {

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    @Expose
    @SerializedName("email")
    private String email;

    @Expose
    @SerializedName("password")
    private String password;

    @Expose
    @SerializedName("use_id")
    private Integer user_id;

    @Expose
    @SerializedName("request_token")
    private String request_token;

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

    public Integer getUserId() {
        return user_id;
    }

    public String getRequestToken() {
        return request_token;
    }
}
