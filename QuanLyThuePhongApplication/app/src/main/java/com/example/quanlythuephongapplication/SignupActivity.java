package com.example.quanlythuephongapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quanlythuephongapplication.adapter.ShowDialogError;
import com.example.quanlythuephongapplication.model.KhachHang;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignupActivity extends AppCompatActivity {
    FirebaseDatabase database;
    DatabaseReference dataKH;
    EditText edtHoTen, edtEmail, edtSdt, edtMatKhau;
    Button btnDangKy;
    TextView txtDangNhap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        database = FirebaseDatabase.getInstance();
        dataKH = database.getReference("KhachHang");

        AnhXa();
        setEvent();
    }

    private void setEvent() {
        txtDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                //startActivity(intent);
                finish();
            }
        });

        btnDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String hoTen = edtHoTen.getText().toString().trim();
                String sdt = edtSdt.getText().toString().trim();
                String email = edtEmail.getText().toString().trim();
                String matKhau = edtMatKhau.getText().toString().trim();
                if(hoTen.equals("") || sdt.equals("") || email.equals("") || matKhau.equals("")){
                    new ShowDialogError(SignupActivity.this, "Thông báo lỗi", "Vui lòng nhập đầy đủ thông tin").settingDialog();
                }else {
                    KhachHang khachHang = new KhachHang();
                    khachHang.setEmail(email);
                    khachHang.setSdt(sdt);
                    khachHang.setMatKhau(matKhau);
                    khachHang.setHoTen(hoTen);
                    khachHang.setId(dataKH.push().getKey());

                    dataKH.child(khachHang.getId()).setValue(khachHang, new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                            if(error == null){
                                finish();
                                Toast.makeText(SignupActivity.this, "Đăng ký tài khoản thành công", Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(SignupActivity.this, "Đăng ký tài khoản thất bại", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }
            }
        });
    }

    private void AnhXa() {
        edtHoTen = findViewById(R.id.edtHoTen);
        edtEmail = findViewById(R.id.edtEmail);
        edtSdt = findViewById(R.id.edtSdt);
        edtMatKhau = findViewById(R.id.edtMatKhau);
        btnDangKy = findViewById(R.id.btnDangKy);
        txtDangNhap = findViewById(R.id.txtDangNhap);
    }
}