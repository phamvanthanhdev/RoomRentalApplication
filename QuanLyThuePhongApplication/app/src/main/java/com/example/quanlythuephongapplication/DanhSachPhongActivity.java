package com.example.quanlythuephongapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quanlythuephongapplication.adapter.PhongAdapter;
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

public class DanhSachPhongActivity extends AppCompatActivity {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference dataPhong, dataNguoiThue;
    Nha nha;
    List<Phong> phongList = new ArrayList<>();
    PhongAdapter phongAdapter;
    RecyclerView rvPhong;
    TextView txtTenNha;
    Map<String, Long> nguoiThuePhong = new HashMap<>();
    RadioButton rbTatCa, rbPhongTrong, rbDangThue;
    List<Phong> phongListHienThi = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danh_sach_phong);

        Intent intent = getIntent();
        nha = (Nha) intent.getSerializableExtra("nha");

        AnhXa();
        txtTenNha.setText(nha.getTenNha());

        dataPhong = database.getReference("Nha/" + nha.getId()+"/Phong");
        LayDuLieuPhong();
        setPhongRecycler();
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Do something after 5s = 5000ms
                LayNguoiThuePhong();
            }
        }, 1000);

        setEvent();
    }

    private void setEvent() {
        rbPhongTrong.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    phongListHienThi.clear();
                    //Toast.makeText(DanhSachPhongActivity.this, nguoiThuePhong.size()+"size", Toast.LENGTH_SHORT).show();
                    for (Phong phong:phongList) {
                        for (Map.Entry<String, Long> entry : nguoiThuePhong.entrySet()) {
                            //Toast.makeText(DanhSachPhongActivity.this, entry.getKey()+"-"+entry.getValue() , Toast.LENGTH_SHORT).show();
                            if(phong.getId().trim().equals(entry.getKey().trim()) && entry.getValue() == 0){
                                phongListHienThi.add(phong);
                            }
                        }
                    }
                    //Toast.makeText(DanhSachPhongActivity.this, nguoiThuePhong.size()+" size", Toast.LENGTH_SHORT).show();
                    phongAdapter.notifyDataSetChanged();
                }
            }
        });

        rbDangThue.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    phongListHienThi.clear();
                    for (Phong phong:phongList) {
                        for (Map.Entry<String, Long> entry : nguoiThuePhong.entrySet()) {
                            //Toast.makeText(DanhSachPhongActivity.this, entry.getKey()+"-"+phong.getId()+"-" , Toast.LENGTH_SHORT).show();
                            if(phong.getId().trim().equals(entry.getKey().trim()) && entry.getValue() > 0){
                                phongListHienThi.add(phong);
                            }
                        }
                    }
                    phongAdapter.notifyDataSetChanged();
                }
            }
        });

        rbTatCa.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    phongListHienThi.clear();
                    phongListHienThi.addAll(phongList);
                    phongAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    private void AnhXa() {
        txtTenNha = findViewById(R.id.textViewTenNha);
        rbTatCa = findViewById(R.id.radioButtonTatCa);
        rbDangThue = findViewById(R.id.radioButtonPhongDangThue);
        rbPhongTrong = findViewById(R.id.radioButtonPhongTrong);
    }


    private void setPhongRecycler(){
        rvPhong = findViewById(R.id.recyclerViewPhong);
        //RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 2);
        rvPhong.setLayoutManager(layoutManager);
        phongAdapter = new PhongAdapter(this, phongListHienThi, nha);
        rvPhong.setAdapter(phongAdapter);
    }

    private void LayDuLieuPhong() {
        phongList.clear();
        phongListHienThi.clear();
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
                    phongListHienThi.add(phong);
                    phongAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void LayNguoiThuePhong() {
        for (Phong phong:phongList) {
            dataNguoiThue = database.getReference("Nha/" + nha.getId()+"/Phong/"+phong.getId()+"/NguoiThue");
            dataNguoiThue.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    nguoiThuePhong.put(phong.getId(), snapshot.getChildrenCount());
                    //Toast.makeText(DanhSachPhongActivity.this, phong.getId()+"-"+snapshot.getChildrenCount(), Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }
}