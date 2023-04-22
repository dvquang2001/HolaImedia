package com.example.holaimedia.model.delivery;

public class OrderManagement {
    private String Hoten;
    private String Gio;

    private String Khoiluong;
    private String Ngay;
    private String SDT;
    private  String Tien;
    private  String Diachigui;
    private  String Diachinhan;

    public OrderManagement() {
    }

    public String getHoten() {
        return Hoten;
    }

    public void setHoten(String hoten) {
        Hoten = hoten;
    }

    public String getGio() {
        return Gio;
    }

    public void setGio(String gio) {
        Gio = gio;
    }

    public String getKhoiluong() {
        return Khoiluong;
    }

    public void setKhoiluong(String khoiluong) {
        Khoiluong = khoiluong;
    }

    public String getNgay() {
        return Ngay;
    }

    public void setNgay(String ngay) {
        Ngay = ngay;
    }

    public String getSdt() {
        return SDT;
    }

    public void setSdt(String SDT) {
        SDT = SDT;
    }

    public String getTien() {
        return Tien;
    }

    public void setTien(String tien) {
        Tien = tien;
    }

    public String getDiachigui() {
        return Diachigui;
    }

    public void setDiachigui(String diachigui) {
        Diachigui = diachigui;
    }

    public String getDiachinhan() {
        return Diachinhan;
    }

    public void setDiachinhan(String diachinhan) {
        Diachinhan = diachinhan;
    }

    public OrderManagement(String hoten, String gio, String khoiluong, String ngay, String SDT, String tien, String diachigui, String diachinhan) {
        Hoten = hoten;
        Gio = gio;
        Khoiluong = khoiluong;
        Ngay = ngay;
        SDT = SDT;
        Tien = tien;
        Diachigui = diachigui;
        Diachinhan = diachinhan;
    }
}
