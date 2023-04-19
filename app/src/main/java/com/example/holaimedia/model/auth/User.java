package com.example.holaimedia.model.auth;

import androidx.annotation.NonNull;

public class User {
    private String name;
    private String email;
    private String password;
    private String profileImg;
    private String phoneNumber;
    private String address;
    private String pinCode;
    private Long account;
    private boolean isAdmin;

    public User() {}

    public User(String name, String email, String password, String profileImg, String phoneNumber, String address, String pinCode, Long account, boolean isAdmin) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.profileImg = profileImg;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.pinCode = pinCode;
        this.account = account;
        this.isAdmin = isAdmin;
    }

    public String getProfileImg() {
        return profileImg;
    }

    public void setProfileImg(String profileImg) {
        this.profileImg = profileImg;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPinCode() {
        return pinCode;
    }

    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
    }

    public Long getAccount() {
        return account;
    }

    public void setAccount(Long account) {
        this.account = account;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    @NonNull
    @Override
    public String toString() {
        return "User: " + name + address + "account: " + account;
    }
}
