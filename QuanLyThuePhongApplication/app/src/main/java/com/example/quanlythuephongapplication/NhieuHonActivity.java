package com.example.quanlythuephongapplication;

import static com.example.quanlythuephongapplication.MainActivity.khachHang;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class NhieuHonActivity extends AppCompatActivity {
    TextView txtHoTen, txtEmail ,txtTrangChu, txtPhongYeuThich, txtPhongCuaToi, txtHoaDon, txtCapNhatTaiKhoan, txtLienHeChuTro, txtDangXuat, txtDangKyThuePhong;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nhieu_hon);

        AnhXa();
        HienThiDuLieu();
        setEvent();
    }

    private void setEvent() {
        txtTrangChu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NhieuHonActivity.this, MainActivity.class);
                intent.putExtra("khachHang", khachHang);
                startActivity(intent);
            }
        });

        txtPhongYeuThich.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NhieuHonActivity.this, PhongYeuThichActivity.class);
                intent.putExtra("khachHang", khachHang);
                startActivity(intent);
            }
        });

        txtPhongCuaToi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NhieuHonActivity.this, PhongCuaToiActivity.class);
                startActivity(intent);
            }
        });

        txtCapNhatTaiKhoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NhieuHonActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });

        txtLienHeChuTro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(khachHang.getTrangThaiThue() == 0){
                    Toast.makeText(NhieuHonActivity.this, "Bạn chưa thuê phòng nào.", Toast.LENGTH_SHORT).show();
                } else if (khachHang.getTrangThaiThue() == 1) {
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:" + "0987658686"));
                    startActivity(intent);
                }
            }
        });

        txtDangXuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NhieuHonActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        txtDangKyThuePhong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NhieuHonActivity.this, DangKyThuePhongActivity.class);
                startActivity(intent);
            }
        });
    }

    private void HienThiDuLieu() {
        txtHoTen.setText(khachHang.getHoTen());
        txtEmail.setText(khachHang.getEmail());
    }

    private void AnhXa() {
        txtHoTen = findViewById(R.id.textViewHoTen);
        txtEmail = findViewById(R.id.textViewEmail);
        txtTrangChu = findViewById(R.id.textViewTrangChu);
        txtPhongYeuThich = findViewById(R.id.textViewPhongYeuThich);
        txtPhongCuaToi = findViewById(R.id.textViewPhongCuaToi);
        txtHoaDon = findViewById(R.id.textViewHoaDon);
        txtCapNhatTaiKhoan = findViewById(R.id.textViewCapNhatTaiKhoan);
        txtLienHeChuTro = findViewById(R.id.textViewLienHeChuTro);
        txtDangXuat = findViewById(R.id.textViewDangXuat);
        txtDangKyThuePhong = findViewById(R.id.textViewDangKyThuePhong);
    }
}