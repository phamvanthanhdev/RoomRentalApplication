package com.example.quanlythuephongapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quanlythuephongapplication.adapter.ShowDialog;
import com.example.quanlythuephongapplication.adapter.ShowDialogError;
import com.example.quanlythuephongapplication.model.KhachHang;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {
    FirebaseDatabase database;
    DatabaseReference dataCustomer;
    EditText edtEmail, edtMatKhau;
    Button btnDangNhap;
    TextView txtDangKy;
    CheckBox cbNhoMK;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        database = FirebaseDatabase.getInstance();
        dataCustomer = database.getReference("KhachHang");

        AnhXa();
        setEvent();
    }

    private void setEvent() {
        btnDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = edtEmail.getText().toString().trim();
                String matKhau = edtMatKhau.getText().toString().trim();
                if(email.equals("") || matKhau.equals("")){
                    ShowDialogError dialogError = new ShowDialogError(LoginActivity.this, "Thông báo lỗi", "Vui lòng nhập đầy đủ thông tin");
                    dialogError.settingDialog();
                }else{
                    checkCustomer(email, matKhau);
                }
            }
        });

        txtDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, SignupActivity.class));
            }
        });
    }

    private void AnhXa() {
        edtEmail = findViewById(R.id.edtEmail);
        edtMatKhau = findViewById(R.id.edtMatKhau);
        btnDangNhap = findViewById(R.id.btnDangNhap);
        txtDangKy = findViewById(R.id.txtDangKy);
        cbNhoMK = findViewById(R.id.checkBoxNhoMatKhau);
    }

    public void checkCustomer(String email, String matKhau){
        dataCustomer.orderByChild("email")
                .equalTo(email)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()) {
                            for (DataSnapshot item : snapshot.getChildren()) {
                                String matKhauDB = item.child("matKhau").getValue().toString();
                                if(matKhau.equals(matKhauDB)){
                                    //Ghi email, mật khẩu vào bộ nhớ
                                    if(cbNhoMK.isChecked())
                                        GhiMatKhau(email, matKhauDB);
                                    else
                                        KhongGhiMatKhau();

                                    String idKH = item.getKey().toString();
                                    String hoTen = item.child("hoTen").getValue().toString();
                                    String sdt = item.child("sdt").getValue().toString();
                                    int trangThaiThue = Integer.parseInt(item.child("trangThaiThue").getValue().toString());

                                    KhachHang khachHang = new KhachHang(idKH, email, hoTen, sdt, matKhauDB, trangThaiThue);
                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                    intent.putExtra("khachHang", khachHang);
                                    startActivity(intent);
                                    /*Intent intent = new Intent(LoginActivity.this, OTPActivity.class);
                                    startActivity(intent);*/
                                }else{
                                    ShowDialogError dialogError = new ShowDialogError(LoginActivity.this, "Thông báo lỗi", "Tài khoản, mật khẩu chưa chính xác");
                                    dialogError.settingDialog();
                                }
                            }
                        }else{
                            ShowDialogError dialogError = new ShowDialogError(LoginActivity.this, "Thông báo lỗi", "Tài khoản, mật khẩu chưa chính xác");
                            dialogError.settingDialog();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(LoginActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                    }
                });

    }

    private void GhiMatKhau(String email, String matKhau) {
        SharedPreferences sharedPreferences = getSharedPreferences("LuuTaiKhoan", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("email", email);
        editor.putString("matkhau", matKhau);
        editor.apply();
    }

    private void KhongGhiMatKhau() {
        SharedPreferences sharedPreferences = getSharedPreferences("LuuTaiKhoan", Context.MODE_PRIVATE);
        sharedPreferences.edit().clear().apply();
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sharedPreferences = getSharedPreferences("LuuTaiKhoan", Context.MODE_PRIVATE);
        String email = sharedPreferences.getString("email", "").toString();
        String matkhau = sharedPreferences.getString("matkhau", "").toString();
        edtEmail.setText(email);
        edtMatKhau.setText(matkhau);
        if(!email.equals("")) cbNhoMK.setChecked(true);
    }

}