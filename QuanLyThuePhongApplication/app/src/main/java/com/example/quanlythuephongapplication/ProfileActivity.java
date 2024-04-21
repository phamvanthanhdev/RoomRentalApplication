package com.example.quanlythuephongapplication;

import static com.example.quanlythuephongapplication.MainActivity.khachHang;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ProfileActivity extends AppCompatActivity {
    Button btnCapNhat;
    TextView txtHoTen, txtEmail, txtProfileTen, txtProfileEmail, txtProfileSdt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        AnhXa();
        //HienThiDuLieu();
        setEvent();
    }

    @Override
    protected void onResume() {
        super.onResume();
        HienThiDuLieu();
    }

    private void setEvent() {
        btnCapNhat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this, EditProfileActivity.class));
            }
        });
    }

    private void HienThiDuLieu() {
        txtHoTen.setText(khachHang.getHoTen());
        txtEmail.setText(khachHang.getEmail());
        txtProfileTen.setText(khachHang.getHoTen());
        txtProfileEmail.setText(khachHang.getEmail());
        txtProfileSdt.setText(khachHang.getSdt());
    }

    private void AnhXa() {
        btnCapNhat = findViewById(R.id.btnCapNhat);
        txtHoTen = findViewById(R.id.txtTen);
        txtEmail = findViewById(R.id.txtEmail);
        txtProfileEmail = findViewById(R.id.txtProfileEmail);
        txtProfileTen = findViewById(R.id.txtProfileTen);
        txtProfileSdt = findViewById(R.id.txtProfileSdt);
    }
}