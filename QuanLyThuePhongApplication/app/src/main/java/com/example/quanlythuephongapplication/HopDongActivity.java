package com.example.quanlythuephongapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;

import com.example.quanlythuephongapplication.adapter.DichVuAdapter;
import com.example.quanlythuephongapplication.model.HopDong;
import com.example.quanlythuephongapplication.model.Nha;
import com.example.quanlythuephongapplication.model.Phong;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class HopDongActivity extends AppCompatActivity {
    FirebaseDatabase database= FirebaseDatabase.getInstance();
    DatabaseReference dataHopDong;
    Nha nha;
    Phong phong;
    HopDong hopDong;
    TextView txtMa, txtNguoiDaiDien, txtCamKetNguoiThue, txtTenNha, txtTenPhong, txtTienPhong, txtTienCoc, txtNgayTinhTien, txtNgayKy, txtKyHan;
    RecyclerView rvDichVu;
    DichVuAdapter dichVuAdapter;
    final Handler handler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hop_dong);

        Intent intent = getIntent();
        nha = (Nha) intent.getSerializableExtra("nha");
        phong = (Phong) intent.getSerializableExtra("phong");

        dataHopDong = database.getReference("HopDong");
        LayDuLieuHopDong();
        AnhXa();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                HienThiDuLieu();
                setDichVuRecycler();
            }
        }, 1000);

    }

    private void setDichVuRecycler(){
        rvDichVu = findViewById(R.id.recyclerViewDichVu);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        rvDichVu.setLayoutManager(layoutManager);
        dichVuAdapter = new DichVuAdapter(this, hopDong.getDichVu());
        rvDichVu.setAdapter(dichVuAdapter);
    }

    private void HienThiDuLieu() {
        txtMa.setText(hopDong.getId());
        txtNguoiDaiDien.setText(hopDong.getHoTen());
        txtCamKetNguoiThue.setText(hopDong.getCamKetNguoiThue()+" người");
        txtTenNha.setText(hopDong.getTenNha());
        txtTenPhong.setText(hopDong.getTenPhong());
        txtTienPhong.setText(hopDong.getTienPhong()+"đ/tháng");
        txtTienCoc.setText(hopDong.getTienCoc()+"đ/tháng");
        txtNgayTinhTien.setText(hopDong.getNgayBatDauTinhTien());
        txtNgayKy.setText(hopDong.getNgayKyHopDong());
        txtKyHan.setText(hopDong.getKyHanThanhToan());
    }

    private void AnhXa() {
        txtMa = findViewById(R.id.textViewMaHopDong);
        txtNguoiDaiDien = findViewById(R.id.textViewNguoiDaiDien);
        txtCamKetNguoiThue = findViewById(R.id.textViewCamKetNguoiThue);
        txtTenNha = findViewById(R.id.textViewTenNha);
        txtTenPhong = findViewById(R.id.textViewTenPhong);
        txtTienPhong = findViewById(R.id.textViewTienPhong);
        txtTienCoc = findViewById(R.id.textViewTienCoc);
        txtNgayTinhTien = findViewById(R.id.textViewNgayTinhTien);
        txtNgayKy = findViewById(R.id.textViewNgayKy);
        txtKyHan = findViewById(R.id.textViewKiHanThanhToan);
    }

    private void LayDuLieuHopDong() {
        dataHopDong.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                HopDong hopDongDB = snapshot.getValue(HopDong.class);
                if(hopDongDB.getIdNha().trim().equals(nha.getId().trim()) &&
                    hopDongDB.getIdPhong().trim().equals(phong.getId().trim())){
                    hopDong = hopDongDB;
                    //Log.d("AAA", hopDong.toString());
                }
                //Log.d("BBB", hopDongDB.toString());
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
}