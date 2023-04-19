package com.example.holaimedia.model.food;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class Order implements Serializable {
    private String productName;
    private String ProductPrice;
    private String currentDate;
    private String currentTime;
    private String totalQuantity;
    private int totalPrice;
    private String imageUrl;
    private String documentId;
    private String userId;


    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductPrice() {
        return ProductPrice;
    }

    public void setProductPrice(String productPrice) {
        ProductPrice = productPrice;
    }

    public String getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(String currentDate) {
        this.currentDate = currentDate;
    }

    public String getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(String currentTime) {
        this.currentTime = currentTime;
    }

    public String getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(String totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Order(String productName, String ProductPrice, String currentDate, String currentTime, String totalQuantity, int totalPrice, String imageUrl,String userId) {
        this.productName = productName;
        this.ProductPrice = ProductPrice;
        this.currentDate = currentDate;
        this.currentTime = currentTime;
        this.totalQuantity = totalQuantity;
        this.totalPrice = totalPrice;
        this.imageUrl = imageUrl;
        this.userId = userId;
    }

    public Order() {
    }

    @NonNull
    @Override
    public String toString() {
        return productName + currentDate + currentTime + totalQuantity;
    }
}
