package com.example.quanlythuephongapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.quanlythuephongapplication.adapter.NhaGoiYAdapter;
import com.example.quanlythuephongapplication.adapter.PhongAdapter;
import com.example.quanlythuephongapplication.model.Nha;
import com.example.quanlythuephongapplication.model.Phong;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ChiTietNhaActivity extends AppCompatActivity {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference dataPhong;
    Nha nha;
    List<Phong> phongList = new ArrayList<>();
    ImageView btnThoat, imgHinh;
    TextView txtTenNha, txtDiaChi, txtSoTang, txtGioMoCua, txtGioKhoaCua, txtChuyenDiBaoTruoc, txtMoTa, txtLuuY;
    Button btnDanhSachPhong;
    RecyclerView rvPhong;
    PhongAdapter phongAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_nha);

        Intent intent = getIntent();
        nha = (Nha) intent.getSerializableExtra("nha");

        dataPhong = database.getReference("Nha/" + nha.getId()+"/Phong");

        AnhXa();
        GanDuLieu();
        setPhongRecycler();

        btnDanhSachPhong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentDanhSachPhong = new Intent(ChiTietNhaActivity.this, DanhSachPhongActivity.class);
                intentDanhSachPhong.putExtra("nha", nha);
                startActivity(intentDanhSachPhong);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        LayDuLieuPhong();
    }

    private void setPhongRecycler(){
        rvPhong = findViewById(R.id.recyclerViewPhong);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        rvPhong.setLayoutManager(layoutManager);
        phongAdapter = new PhongAdapter(this, phongList, nha);
        rvPhong.setAdapter(phongAdapter);
    }

    private void GanDuLieu() {
        Glide.with(this).load(nha.getLinkHinh()).into(imgHinh);
        txtTenNha.setText(nha.getTenNha());
        txtDiaChi.setText(nha.getDiaChi()+", "+nha.getQuanHuyen()+", " +nha.getTinhThanh());
        txtSoTang.setText(String.valueOf(nha.getSoTang()));
        txtGioMoCua.setText(nha.getGioMoCua());
        txtGioKhoaCua.setText(nha.getGioDongCua());
        txtChuyenDiBaoTruoc.setText(nha.getSoNgayChuyenDi()+" ngày");
        txtMoTa.setText(nha.getMoTa());
        txtLuuY.setText(nha.getGhiChu());
    }

    private void AnhXa() {
        btnThoat = findViewById(R.id.btnThoat);
        imgHinh = findViewById(R.id.imageViewHinh);
        txtTenNha = findViewById(R.id.textViewTenNha);
        txtDiaChi = findViewById(R.id.textViewDiaChi);
        txtSoTang = findViewById(R.id.textViewSoTang);
        txtGioMoCua = findViewById(R.id.textViewGioMoCua);
        txtGioKhoaCua = findViewById(R.id.textViewGioKhoaCua);
        txtChuyenDiBaoTruoc = findViewById(R.id.textViewChuyenDiBaoTruoc);
        txtMoTa = findViewById(R.id.textViewMoTa);
        txtLuuY = findViewById(R.id.textViewLuuY);
        btnDanhSachPhong = findViewById(R.id.buttonDanhSachPhong);
    }

    private void LayDuLieuPhong() {
        phongList.clear();
        dataPhong.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot item:snapshot.getChildren()) {
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

                    phongList.add(phong);
                    phongAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}



