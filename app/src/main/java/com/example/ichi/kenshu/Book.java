package com.example.ichi.kenshu;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Book implements Serializable {

    @Expose
    @SerializedName("title")
    private String name;

    @Expose
    @SerializedName("price")
    private Integer price;

    @Expose
    @SerializedName("purchaseDate")
    private String purchaseDate;

    private String image;

    @Expose
    @SerializedName("image_data")
    private String imageData;

    @Expose
    @SerializedName("imageUrl")
    private String imageUrl;


    public Book(String image, String name, Integer price, String purchaseDate) {
        this.image = image;
        this.name = name;
        this.price = price;
        this.purchaseDate = purchaseDate;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPrice() {
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
