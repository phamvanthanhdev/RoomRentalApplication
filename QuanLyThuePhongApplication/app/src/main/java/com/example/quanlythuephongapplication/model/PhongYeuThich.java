package com.example.quanlythuephongapplication.model;

import java.io.Serializable;

public class PhongYeuThich implements Serializable {
    private String idKhachHang;
    private String idPhong;

    public PhongYeuThich() {
    }

    public PhongYeuThich(String idKhachHang, String idPhong) {
        this.idKhachHang = idKhachHang;
        this.idPhong = idPhong;
    }

    public String getIdKhachHang() {
        return idKhachHang;
    }

    public void setIdKhachHang(String idKhachHang) {
        this.idKhachHang = idKhachHang;
    }

    public String getIdPhong() {
        return idPhong;
    }

    public void setIdPhong(String idPhong) {
        this.idPhong = idPhong;
    }

    @Override
    public String toString() {
        return "PhongYeuThich{" +
                "idKhachHang='" + idKhachHang + '\'' +
                ", idPhong='" + idPhong + '\'' +
                '}';
    }
}
