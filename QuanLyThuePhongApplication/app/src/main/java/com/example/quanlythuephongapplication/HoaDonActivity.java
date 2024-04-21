package com.example.quanlythuephongapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.example.quanlythuephongapplication.adapter.HoaDonAdapter;
import com.example.quanlythuephongapplication.adapter.NguoiThueAdapter;
import com.example.quanlythuephongapplication.model.HoaDon;
import com.example.quanlythuephongapplication.model.Nha;
import com.example.quanlythuephongapplication.model.Phong;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HoaDonActivity extends AppCompatActivity {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference dataHoaDon;
    Phong phong;
    Nha nha;
    List<HoaDon> hoaDonList = new ArrayList<>();
    HoaDonAdapter hoaDonAdapter;
    RecyclerView rvHoaDon;
    final Handler handler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hoa_don);

        Intent intent = getIntent();
        nha = (Nha) intent.getSerializableExtra("nha");
        phong = (Phong) intent.getSerializableExtra("phong");
        dataHoaDon = database.getReference("Nha/" + nha.getId()+"/Phong/"+phong.getId()+"/HoaDon");

        LayDuLieuHoaDon();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                setHoaDonRecycler();
            }
        }, 500);
    }

    private void LayDuLieuHoaDon() {
        dataHoaDon.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot item:snapshot.getChildren()) {
                    HoaDon hoaDon = new HoaDon();
                    hoaDon.setId(item.child("id").getValue().toString());
                    hoaDon.setThang(item.child("thang").getValue().toString());
                    hoaDon.setNgayThanhToan(item.child("ngayThanhToan").getValue().toString());
                    hoaDon.setHanThanhToan(item.child("hanThanhToan").getValue().toString());
                    hoaDon.setTienPhong(Integer.parseInt(item.child("tienPhong").getValue().toString()));
                    hoaDon.setTienDichVu(Integer.parseInt(item.child("tienDichVu").getValue().toString()));
                    hoaDon.setGhiChu(item.child("ghiChu").getValue().toString());
                    hoaDon.setTrangThai(Integer.parseInt(item.child("trangThai").getValue().toString()));
                    hoaDonList.add(hoaDon);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private  void setHoaDonRecycler(){
        rvHoaDon = findViewById(R.id.recyclerViewHoaDon);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        rvHoaDon.setLayoutManager(layoutManager);
        hoaDonAdapter = new HoaDonAdapter(this, hoaDonList);
        rvHoaDon.setAdapter(hoaDonAdapter);
    }
}