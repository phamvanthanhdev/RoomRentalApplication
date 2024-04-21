package com.example.quanlythuephongapplication;

import static com.example.quanlythuephongapplication.MainActivity.khachHang;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.example.quanlythuephongapplication.adapter.PhongAdapter;
import com.example.quanlythuephongapplication.adapter.PhongYeuThichAdapter;
import com.example.quanlythuephongapplication.model.KhachHang;
import com.example.quanlythuephongapplication.model.Nha;
import com.example.quanlythuephongapplication.model.Phong;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PhongYeuThichActivity extends AppCompatActivity {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference dataPhongYeuThich;
    DatabaseReference dataNha = database.getReference("Nha");
    DatabaseReference dataPhong;
    List<String> idPhongList = new ArrayList<>();
    List<Phong> phongYeuThichList = new ArrayList<>();
    Map<Nha, Phong> phongNhaMap = new HashMap<>();
    RecyclerView rvPhongYeuThich;
    PhongYeuThichAdapter phongyeuThichAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phong_yeu_thich);

        Intent intent = getIntent();
        KhachHang khachHang = (KhachHang) intent.getSerializableExtra("khachHang");

        dataPhongYeuThich = database.getReference("YeuThichPhong/"+khachHang.getId());
        LayIdPhongYeuThich();
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                LayDuLieuPhongYeuThich();
            }
        }, 1000);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                setPhongRecycler();
            }
        }, 2000);

    }

    private void setPhongRecycler(){
        rvPhongYeuThich = findViewById(R.id.recyclerViewPhongYeuThich);
        //RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 2);
        rvPhongYeuThich.setLayoutManager(layoutManager);
        phongyeuThichAdapter = new PhongYeuThichAdapter(this, phongYeuThichList, phongNhaMap);
        rvPhongYeuThich.setAdapter(phongyeuThichAdapter);
    }

    private void LayDuLieuPhongYeuThich() {
        dataNha.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot item:snapshot.getChildren()) {
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

                    //Lấy dữ liệu phòng
                    dataPhong = database.getReference("Nha/" + nha.getId()+"/Phong");
                    dataPhong.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot item:snapshot.getChildren()) {
                                String idPhongDB = item.child("id").getValue().toString();
                                Log.d("CCC", idPhongList.size()+" size");
                                for (String idPhong :idPhongList) {
                                    //Kiểm tra id các phòng trong nhà có trùng với id phòng yêu thích không
                                    if(idPhong.trim().equals(idPhongDB)){
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

                                        phongYeuThichList.add(phong);
                                        phongNhaMap.put(nha, phong);
                                    }
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void LayIdPhongYeuThich() {
        dataPhongYeuThich.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot item:snapshot.getChildren()) {
                    if(item.getValue() != null) {
                        String idPhong = item.getValue().toString();
                        idPhongList.add(idPhong);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void XoaYeuThichPhong(Phong phong) {
        dataPhongYeuThich.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot item:snapshot.getChildren()) {
                    String idPhong = item.getValue().toString();
                    if(idPhong.trim().equals(phong.getId().trim())){
                        String phongKey = item.getKey().toString();
                        //Xóa trên db
                        dataPhongYeuThich.child(phongKey).removeValue();
                        //Xóa trên android
                        phongYeuThichList.remove(phong);
                        phongyeuThichAdapter.notifyDataSetChanged();
                        Log.d("AAA", "Xoa yeu thich thanh conggggg");
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}