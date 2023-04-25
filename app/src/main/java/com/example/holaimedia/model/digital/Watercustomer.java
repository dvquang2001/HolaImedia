package com.example.holaimedia.model.digital;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Watercustomer implements Serializable {
    private String Ten;
    private String Sodienthoai;

    private String Diachi;
    private String Tien;
    private String Ma;
    private  String Kyhan;
    public Watercustomer() {
    }

    public Watercustomer(String ten, String sodienthoai, String diachi, String tien, String ma, String kyhan) {
        Ten = ten;
        Sodienthoai = sodienthoai;
        Diachi = diachi;
        Tien = tien;
        Ma = ma;
        Kyhan = kyhan;
    }

    public String getKyhan() {
        return Kyhan;
    }

    public void setKyhan(String kyhan) {
        Kyhan = kyhan;
    }

    public String getTen() {
        return Ten;
    }

    public void setTen(String ten) {
        Ten = ten;
    }

    public String getSodienthoai() {
        return Sodienthoai;
    }

    public void setSodienthoai(String sodienthoai) {
        Sodienthoai = sodienthoai;
    }

    public String getDiachi() {
        return Diachi;
    }

    public void setDiachi(String diachi) {
        Diachi = diachi;
    }

    public String getTien() {
        return Tien;
    }

    public void setTien(String tien) {
        Tien = tien;
    }

    public String getMa() {
        return Ma;
    }

    public void setMa(String ma) {
        Ma = ma;
    }

    public Map<String,Object> toMap(){
        HashMap<String,Object> result=new HashMap<>();
        result.put("Tien",Tien);
        return result;
    }
}
