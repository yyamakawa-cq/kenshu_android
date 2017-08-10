package com.example.ichi.kenshu;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Book implements Serializable {

    @Expose
    @SerializedName("name")
    private String name;

    @Expose
    @SerializedName("price")
    private int price;

    @Expose
    @SerializedName("purchase_date")
    private String purchaseDate;

    @Expose
    @SerializedName("image_data")
    private String imageData;

    @Expose
    @SerializedName("image_url")
    private String imageUrl;

    @Expose
    @SerializedName("user_id")
    private int userId;

    @Expose
    @SerializedName("id")
    private int bookId;

    public Book(String name, int price, String purchaseDate, String imageData,int id) {
        this.name = name;
        this.price = price;
        this.purchaseDate = purchaseDate;
        this.imageData = imageData;
        this.userId = id;
    }

    public Book(String name, int price, String purchaseDate, String imageData) {
        this.name = name;
        this.price = price;
        this.purchaseDate = purchaseDate;
        this.imageData = imageData;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(String purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getImageData() {
        return imageData;
    }

    public void setImageData(String imageData) {
        this.imageData = imageData;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }
}
