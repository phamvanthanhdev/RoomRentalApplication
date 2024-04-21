package com.example.quanlythuephongapplication.model;

import java.io.Serializable;
import java.util.List;

public class Nha implements Serializable {
    private String id;
    private String linkHinh;
    private String tenNha;
    private int soTang;
    private String moTa;
    private String diaChi;
    private String tinhThanh;
    private String quanHuyen;
    private String gioMoCua;
    private String gioDongCua;
    private int soNgayChuyenDi;
    private String ghiChu;
    private List<Phong> phongList;

    public Nha() {
    }

    public Nha(String id, String linkHinh, String tenNha, int soTang, String moTa, String diaChi, String tinhThanh, String quanHuyen, String gioMoCua, String gioDongCua, int soNgayChuyenDi, String ghiChu, List<Phong> phongList) {
        this.id = id;
        this.linkHinh = linkHinh;
        this.tenNha = tenNha;
        this.soTang = soTang;
        this.moTa = moTa;
        this.diaChi = diaChi;
        this.tinhThanh = tinhThanh;
        this.quanHuyen = quanHuyen;
        this.gioMoCua = gioMoCua;
        this.gioDongCua = gioDongCua;
        this.soNgayChuyenDi = soNgayChuyenDi;
        this.ghiChu = ghiChu;
        this.phongList = phongList;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLinkHinh() {
        return linkHinh;
    }

    public void setLinkHinh(String linkHinh) {
        this.linkHinh = linkHinh;
    }

    public String getTenNha() {
        return tenNha;
    }

    public void setTenNha(String tenNha) {
        this.tenNha = tenNha;
    }

    public int getSoTang() {
        return soTang;
    }

    public void setSoTang(int soTang) {
        this.soTang = soTang;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getTinhThanh() {
        return tinhThanh;
    }

    public void setTinhThanh(String tinhThanh) {
        this.tinhThanh = tinhThanh;
    }

    public String getQuanHuyen() {
        return quanHuyen;
    }

    public void setQuanHuyen(String quanHuyen) {
        this.quanHuyen = quanHuyen;
    }

    public String getGioMoCua() {
        return gioMoCua;
    }

    public void setGioMoCua(String gioMoCua) {
        this.gioMoCua = gioMoCua;
    }

    public String getGioDongCua() {
        return gioDongCua;
    }

    public void setGioDongCua(String gioDongCua) {
        this.gioDongCua = gioDongCua;
    }

    public int getSoNgayChuyenDi() {
        return soNgayChuyenDi;
    }

    public void setSoNgayChuyenDi(int soNgayChuyenDi) {
        this.soNgayChuyenDi = soNgayChuyenDi;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    public List<Phong> getPhongList() {
        return phongList;
    }

    public void setPhongList(List<Phong> phongList) {
        this.phongList = phongList;
    }

    @Override
    public String toString() {
        return "Nha{" +
                "id='" + id + '\'' +
                ", linkHinh='" + linkHinh + '\'' +
                ", tenNha='" + tenNha + '\'' +
                ", soTang=" + soTang +
                ", moTa='" + moTa + '\'' +
                ", diaChi='" + diaChi + '\'' +
                ", tinhThanh='" + tinhThanh + '\'' +
                ", quanHuyen='" + quanHuyen + '\'' +
                ", gioMoCua='" + gioMoCua + '\'' +
                ", gioDongCua='" + gioDongCua + '\'' +
                ", soNgayChuyenDi=" + soNgayChuyenDi +
                ", ghiChu='" + ghiChu + '\'' +
                ", phongList=" + phongList +
                '}';
    }
}
