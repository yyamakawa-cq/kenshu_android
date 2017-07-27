package com.example.ichi.kenshu;

import android.graphics.Bitmap;

public class Book {
    private Bitmap imageView = null;
    private String title = null;
    private String price = null;
    private String purchaseDate = null;

    public Book(Bitmap bookImage, String bookTitle, String bookPrice, String bookPurchaseDate) {
        imageView = bookImage;
        title = bookTitle;
        price = bookPrice;
        purchaseDate = bookPurchaseDate;
    }

    public void setImageView(Bitmap bookImage) {
        imageView = bookImage;
    }

    public void setTitle(String bookTitle) {
        title = bookTitle;
    }

    public void setPrice(String bookPrice) {
        price = bookPrice;
    }

    public void setPurchaseDate(String bookPurchaseDate) {
        purchaseDate = bookPurchaseDate;
    }

    public Bitmap getImageView() {
        return imageView;
    }

    public String getTitle() {
        return title;
    }

    public String getPrice() {
        return price;
    }

    public String getPurchaseDate() {
        return purchaseDate;
    }
}
