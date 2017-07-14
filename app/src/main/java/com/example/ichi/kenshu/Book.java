package com.example.ichi.kenshu;

import android.graphics.Bitmap;

public class Book {
    private Bitmap mImageView = null;
    private String mTitle = null;
    private String mPrice = null;
    private String mPurchaseDate = null;

    public Book() {}

    public Book(Bitmap imageView, String title, String price, String purchaseDate) {
        mImageView = imageView;
        mTitle = title;
        mPrice = price;
        mPurchaseDate = purchaseDate;
    }

    public void setmImageView(Bitmap imageView) {
        mImageView = imageView;
    }

    public void setmTitle(String title) {
        mTitle = title;
    }

    public void setmPrice(String price) {
        mPrice = price;
    }

    public void setmPurchaseDate(String purchaseDate) {
        mPurchaseDate = purchaseDate;
    }

    public Bitmap getImageView() {
        return mImageView;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getPrice(){
        return mPrice;
    }

    public String getPurchaseDate() {
        return mPurchaseDate;
    }
}
