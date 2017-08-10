package com.example.ichi.kenshu;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BookResponse {

    @Expose
    @SerializedName("result")
    private List<Book> result;

    public List<Book> getResult() {
        return result;
    }

}
