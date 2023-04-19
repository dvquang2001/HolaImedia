package com.example.holaimedia.model.food;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class Cart implements Serializable {
    private String documentId;
    private String productName;
    private String productPrice;
    private String totalQuantity;
    private int totalPrice;
    private String imageUrl;
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
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public String getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(String totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Cart() {
    }

    public Cart(String documentId, String productName, String productPrice, String totalQuantity, int totalPrice, String imageUrl,String userId) {
        this.documentId = documentId;
        this.productName = productName;
        this.productPrice = productPrice;
        this.totalQuantity = totalQuantity;
        this.totalPrice = totalPrice;
        this.imageUrl = imageUrl;
        this.userId = userId;
    }

    @NonNull
    @Override
    public String toString() {
        return this.productName + productPrice;
    }
}
