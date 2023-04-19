package com.example.holaimedia.model.digital;

import androidx.annotation.NonNull;

public class Transaction {
    private String email;
    private long money;
    private String transactionDate;

    public Transaction() {
    }

    public Transaction(String email, long money, String dateTransaction) {
        this.email = email;
        this.money = money;
        this.transactionDate = dateTransaction;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public String getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(String transactionDate) {
        this.transactionDate = transactionDate;
    }

    @NonNull
    @Override
    public String toString() {
        return  email + money;
    }
}
