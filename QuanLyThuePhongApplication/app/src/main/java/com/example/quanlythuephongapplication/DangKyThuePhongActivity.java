package com.example.quanlythuephongapplication;

import static com.example.quanlythuephongapplication.MainActivity.khachHang;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.example.quanlythuephongapplication.adapter.DangKyThuePhongAdapter;
import com.example.quanlythuephongapplication.adapter.NguoiThueAdapter;
import com.example.quanlythuephongapplication.model.DangKyThuePhong;
import com.example.quanlythuephongapplication.model.Nha;
import com.example.quanlythuephongapplication.model.Phong;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DangKyThuePhongActivity extends AppCompatActivity {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference dataNha;
    DatabaseReference dataPhong;
    DatabaseReference dataDangKyThuePhong;
    DangKyThuePhongAdapter dangKyThuePhongAdapter;
    RecyclerView rvDangKyThuePhong;
    List<DangKyThuePhong> dangKyThuePhongList = new ArrayList<>();
    List<Nha> nhaList = new ArrayList<>();
    List<Phong> phongList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_ky_thue_phong);

        dataDangKyThuePhong = database.getReference("DangKyThuePhong");
        LayDuLieuDangKyThuePhong();

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //Log.d("AAA", dangKyThuePhongList.size()+"sizeDK");
                LayDuLieuNhaPhong();
            }
        }, 1000);

        int delayMini = 2000;
        do {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    //Log.d("AAA", nhaList.size()+"sizeNha");
                    //Log.d("AAA", phongList.size()+"sizePhong");
                    setDangKyThueRecycler();
                }
            }, delayMini);
            delayMini+= 1000;
        }while (nhaList.size() < dangKyThuePhongList.size());

    }

    private void LayDuLieuNhaPhong() {
        //Nha nha = new Nha();
        //Phong phong = new Phong();
        for (DangKyThuePhong dangKyThuePhong: dangKyThuePhongList) {
            dataNha = database.getReference("Nha");
            dataNha.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot item:snapshot.getChildren()) {
                        String idNha = item.child("id").getValue().toString();
                        if(idNha.trim().equals(dangKyThuePhong.getIdNha().trim())) {
                            Nha nha = new Nha();
                            nha.setId(item.child("id").getValue().toString());
                            nha.setLinkHinh(item.child("linkHinh").getValue().toString());
                            nha.setTenNha(item.child("tenNha").getValue().toString());
                            nha.setSoTang(Integer.parseInt(item.child("soTang").getValue().toString()));
                            nha.setMoTa(item.child("moTa").getValue().toString());
                            nha.setDiaChi(item.child("diaChi").getValue().toString());
                            nha.setTinhThanh(item.child("tinhThanh").getValue().toString());
                            nha.setQuanHuyen(item.child("quanHuyen").getValue().toString());
                            nha.setGioMoCua(item.child("gioMoCua").getValue().toString());
                            nha.setGioDongCua(item.child("gioDongCua").getValue().toString());
                            nha.setSoNgayChuyenDi(Integer.parseInt(item.child("soNgayChuyenDi").getValue().toString()));
                            nha.setGhiChu(item.child("ghiChu").getValue().toString());

                            //Log.d("BBB", nha.toString());

                            dataPhong = database.getReference("Nha/" + nha.getId()+"/Phong");
                            dataPhong.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    for (DataSnapshot item:snapshot.getChildren()) {
                                        String idPhong = item.child("id").getValue().toString();
                                        if(idPhong.trim().equals(dangKyThuePhong.getIdPhong().trim())) {
                                            Phong phong = new Phong();
                                            phong.setId(item.child("id").getValue().toString());
                                            phong.setLinkHinh(item.child("linkHinh").getValue().toString());
                                            phong.setTenPhong(item.child("tenPhong").getValue().toString());
                                            phong.setGiaPhong(Integer.parseInt(item.child("giaPhong").getValue().toString()));
                                            phong.setTangSo(Integer.parseInt(item.child("tangSo").getValue().toString()));
                                            phong.setSoPhongNgu(Integer.parseInt(item.child("soPhongNgu").getValue().toString()));
                                            phong.setSoPhongKhach(Integer.parseInt(item.child("soPhongKhach").getValue().toString()));
                                            phong.setDienTich(Double.parseDouble(item.child("dienTich").getValue().toString()));
                                            phong.setGioiHanNguoiThue(Integer.parseInt(item.child("gioiHanNguoiThue").getValue().toString()));
                                            phong.setTienCoc(Integer.parseInt(item.child("tienCoc").getValue().toString()));
                                            phong.setMoTa(item.child("moTa").getValue().toString());
                                            phong.setLuuY(item.child("luuY").getValue().toString());

                                            //Log.d("BBB", phong.toString());
                                            nhaList.add(nha);
                                            phongList.add(phong);
                                        }
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }



    }

    private void LayDuLieuDangKyThuePhong() {
        dangKyThuePhongList.clear();
        dataDangKyThuePhong.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                DangKyThuePhong dangKyThuePhong = snapshot.getValue(DangKyThuePhong.class);
                Log.d("AAA", dangKyThuePhong.toString());
                if(dangKyThuePhong.getIdKhachHang().trim().equals(khachHang.getId().trim()))
                    dangKyThuePhongList.add(dangKyThuePhong);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }



    private  void setDangKyThueRecycler(){
        rvDangKyThuePhong = findViewById(R.id.recyclerViewDangKy);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        rvDangKyThuePhong.setLayoutManager(layoutManager);
        dangKyThuePhongAdapter = new DangKyThuePhongAdapter(this, dangKyThuePhongList, nhaList, phongList);
        rvDangKyThuePhong.setAdapter(dangKyThuePhongAdapter);
    }
}