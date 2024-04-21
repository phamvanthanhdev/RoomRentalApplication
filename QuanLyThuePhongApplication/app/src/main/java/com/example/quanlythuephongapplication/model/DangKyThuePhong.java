package com.example.quanlythuephongapplication.model;

import java.io.Serializable;

public class DangKyThuePhong implements Serializable {
    private String id;
    private String idPhong;
    private String idNha;
    private String idKhachHang;
    private String thoiGian;

    public DangKyThuePhong() {
    }

    public DangKyThuePhong(String id, String idPhong, String idNha, String idKhachHang, String thoiGian) {
        this.id = id;
        this.idPhong = idPhong;
        this.idNha = idNha;
        this.idKhachHang = idKhachHang;
        this.thoiGian = thoiGian;
    }

    public String getThoiGian() {
        return thoiGian;
    }

    public void setThoiGian(String thoiGian) {
        this.thoiGian = thoiGian;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdPhong() {
        return idPhong;
    }

    public void setIdPhong(String idPhong) {
        this.idPhong = idPhong;
    }

    public String getIdNha() {
        return idNha;
    }

    public void setIdNha(String idNha) {
        this.idNha = idNha;
    }

    public String getIdKhachHang() {
        return idKhachHang;
    }

    public void setIdKhachHang(String idKhachHang) {
        this.idKhachHang = idKhachHang;
    }

    @Override
    public String toString() {
        return "DangKyThuePhong{" +
                "id='" + id + '\'' +
                ", idPhong='" + idPhong + '\'' +
                ", idNha='" + idNha + '\'' +
                ", idKhachHang='" + idKhachHang + '\'' +
                '}';
    }
}
