package com.example.quanlythuephongapplication;

import static com.example.quanlythuephongapplication.MainActivity.khachHang;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quanlythuephongapplication.adapter.ShowDialogError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class EditProfileActivity extends AppCompatActivity {
    FirebaseDatabase database;
    DatabaseReference dataCustomer;
    TextView txtHoTen, txtEmail, txtSDT, txtMatKhauMoi, txtXacNhanMK;
    Switch switchDoiMatKhau;
    Button btnLuu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        database = FirebaseDatabase.getInstance();
        dataCustomer = database.getReference("KhachHang");

        AnhXa();
        HienThiDuLieu();
        setEvent();
    }

    private void setEvent() {
        switchDoiMatKhau.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(!switchDoiMatKhau.isChecked()){
                    txtMatKhauMoi.setVisibility(View.GONE);
                    txtXacNhanMK.setVisibility(View.GONE);
                }else{
                    txtMatKhauMoi.setVisibility(View.VISIBLE);
                    txtXacNhanMK.setVisibility(View.VISIBLE);
                }
            }
        });
        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String hoTen = txtHoTen.getText().toString().trim();
                String email = txtEmail.getText().toString().trim();
                String sdt = txtSDT.getText().toString().trim();
                if(switchDoiMatKhau.isChecked()){
                    String matKhauMoi = txtMatKhauMoi.getText().toString().trim();
                    String xacNhanMatKhau = txtXacNhanMK.getText().toString().trim();
                    if(hoTen.equals("") || email.equals("") || sdt.equals("") || matKhauMoi.equals("") || xacNhanMatKhau.equals("")){
                        new ShowDialogError(EditProfileActivity.this, "Thông báo lỗi", "Vui lòng nhập đầy đủ thông tin.").settingDialog();
                    }  else if (!matKhauMoi.equals(xacNhanMatKhau)) {
                        new ShowDialogError(EditProfileActivity.this, "Thông báo lỗi", "Mật khẩu xác nhận chưa trùng khớp.").settingDialog();
                    } else{
                        String khachHangKey = khachHang.getId();
                        Map<String, Object> updateKhachHang = new HashMap<>();
                        updateKhachHang.put("hoTen", hoTen);
                        updateKhachHang.put("email", email);
                        updateKhachHang.put("sdt", sdt);
                        updateKhachHang.put("matKhau", matKhauMoi);

                        dataCustomer.child(khachHangKey).updateChildren(updateKhachHang);
                        khachHang.setHoTen(hoTen);
                        khachHang.setMatKhau(matKhauMoi);
                        khachHang.setSdt(sdt);
                        khachHang.setEmail(email);
                        //new ShowDialogError(EditProfileActivity.this, "Thông báo", "Cập nhật tài khoản thành công.").settingDialog();
                        Toast.makeText(EditProfileActivity.this, "Cập nhật tài khoản thành công", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }else {
                    if (hoTen.equals("") || email.equals("") || sdt.equals("")) {
                        new ShowDialogError(EditProfileActivity.this, "Thông báo lỗi", "Vui lòng nhập đầy đủ thông tin.").settingDialog();
                    }else{
                        String khachHangKey = khachHang.getId();
                        Map<String, Object> updateKhachHang = new HashMap<>();
                        updateKhachHang.put("hoTen", hoTen);
                        updateKhachHang.put("email", email);
                        updateKhachHang.put("sdt", sdt);

                        dataCustomer.child(khachHangKey).updateChildren(updateKhachHang);
                        khachHang.setHoTen(hoTen);
                        khachHang.setSdt(sdt);
                        khachHang.setEmail(email);
                        //new ShowDialogError(EditProfileActivity.this, "Thông báo", "Cập nhật tài khoản thành công.").settingDialog();
                        Toast.makeText(EditProfileActivity.this, "Cập nhật tài khoản thành công", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }
            }
        });
    }

    private void HienThiDuLieu() {
        txtHoTen.setText(khachHang.getHoTen());
        txtEmail.setText(khachHang.getEmail());
        txtSDT.setText(khachHang.getSdt());
    }

    private void AnhXa() {
        txtHoTen = findViewById(R.id.editHoTen);
        txtEmail = findViewById(R.id.editEmail);
        txtSDT = findViewById(R.id.editSoDienThoai);
        txtMatKhauMoi = findViewById(R.id.editMatKhauMoi);
        txtXacNhanMK = findViewById(R.id.editNhapLaiMatKhau);
        switchDoiMatKhau = findViewById(R.id.switchDoiMatKhau);
        btnLuu = findViewById(R.id.btnLuu);

        txtMatKhauMoi.setVisibility(View.GONE);
        txtXacNhanMK.setVisibility(View.GONE);
    }
}