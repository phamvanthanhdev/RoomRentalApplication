package com.example.quanlythuephongapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.quanlythuephongapplication.adapter.NhaHangDauAdapter;
import com.example.quanlythuephongapplication.adapter.NhaGoiYAdapter;
import com.example.quanlythuephongapplication.adapter.RecentsAdapter;
import com.example.quanlythuephongapplication.model.DichVu;
import com.example.quanlythuephongapplication.model.HoaDon;
import com.example.quanlythuephongapplication.model.KhachHang;
import com.example.quanlythuephongapplication.model.NguoiThue;
import com.example.quanlythuephongapplication.model.Nha;
import com.example.quanlythuephongapplication.model.Phong;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference dataNha = database.getReference("Nha");
    RecyclerView recentRecycler, topPlacesRecycler;
    TextView txtHoTen;
    EditText edtTimKiem;
    ImageView btnHome, btnProfile, btnPhongYeuThich, btnPhongCuaToi, btnNhieuHon;
    public static KhachHang khachHang;
    List<Nha> nhaHangDauList = new ArrayList<>();
    List<Nha> nhaGoiYList = new ArrayList<>();
    NhaHangDauAdapter nhaHangDauAdapter;
    NhaGoiYAdapter nhaGoiYAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        khachHang = (KhachHang) intent.getSerializableExtra("khachHang");

        AnhXa();

        txtHoTen.setText(khachHang.getHoTen());

        setNhaGoiYRecycler();
        setNhaHangDauRecycler();
        setEvent();
    }

    @Override
    protected void onResume() {
        super.onResume();
        LayDuLieuNha();
    }

    private void LayDuLieuNha() {
        dataNha.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                nhaHangDauList.clear();
                nhaGoiYList.clear();
                for (DataSnapshot item:snapshot.getChildren()) {
                    Nha nha = new Nha();//String id, String linkHinh, String tenNha, int soTang, String moTa, String diaChi, String tinhThanh, String quanHuyen, String gioMoCua, String gioDongCua, int soNgayChuyenDi, String ghiChu, List<Phong> phongList
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

                    nhaHangDauList.add(nha);
                    nhaGoiYList.add(nha);
                    nhaGoiYList.add(nha);
                    nhaGoiYList.add(nha);
                }
                nhaHangDauAdapter.notifyDataSetChanged();
                nhaGoiYAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    /*private void LayDuLieuPhong() {
        List<Phong> phongList = new ArrayList<>();
        for (Nha nha:nhaGoiYList) {
            dataPhong = dataNha.child(nha.getId()).child("Phong");
            //phongList.clear();
            nha.setPhongList(new ArrayList<>());
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
                        nha.getPhongList().add(phong);
                        Log.d("AAA", phong.toString());
                    }
                    //nha.setPhongList(phongList);
                    Log.d("CCC",nha.getPhongList().size()+" size");
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
            //nha.setPhongList(phongList);
            //Log.d("CCC",nha.getPhongList().size()+" size");
        }
        Log.d("DDD",nhaGoiYList.get(0).getPhongList().size()+" size");
    }*/

    private void setEvent() {
        btnProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ProfileActivity.class));
            }
        });

        btnPhongYeuThich.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PhongYeuThichActivity.class);
                intent.putExtra("khachHang", khachHang);
                startActivity(intent);
            }
        });

        btnPhongCuaToi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PhongCuaToiActivity.class);
                startActivity(intent);
            }
        });

        btnNhieuHon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, NhieuHonActivity.class);
                startActivity(intent);
            }
        });
    }

    private void AnhXa() {
        txtHoTen = findViewById(R.id.textViewHoTen);
        edtTimKiem = findViewById(R.id.editTextTimKiem);
        btnHome = findViewById(R.id.btnHome);
        btnProfile = findViewById(R.id.btnProfile);
        btnPhongYeuThich = findViewById(R.id.btnPhongYeuThich);
        btnPhongCuaToi = findViewById(R.id.btnPhongCuaToi);
        btnNhieuHon = findViewById(R.id.btnNhieuHon);
    }

    private void setNhaGoiYRecycler(){
        recentRecycler = findViewById(R.id.recent_recycler);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        recentRecycler.setLayoutManager(layoutManager);
        nhaGoiYAdapter = new NhaGoiYAdapter(this, nhaGoiYList);
        recentRecycler.setAdapter(nhaGoiYAdapter);
    }

    private  void setNhaHangDauRecycler(){
        topPlacesRecycler = findViewById(R.id.top_places_recycler);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        topPlacesRecycler.setLayoutManager(layoutManager);
        nhaHangDauAdapter = new NhaHangDauAdapter(this, nhaHangDauList);
        topPlacesRecycler.setAdapter(nhaHangDauAdapter);
    }

    /*private void LayGioiTinhPhong(DatabaseReference dataGioiTinh, List<String> gioiTinhList){
        dataGioiTinh.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot item:snapshot.getChildren()) {
                    String gioiTinhDB = item.getValue().toString();
                    gioiTinhList.add(gioiTinhDB);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void LayDichVuPhong(DatabaseReference dataDichVu, List<DichVu> dichVuList){
        dataDichVu.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot item:snapshot.getChildren()) {
                    DichVu dichVu = new DichVu();
                    dichVu.setId(item.child("id").getValue().toString());
                    dichVu.setTenDichVu(item.child("tenDichVu").getValue().toString());
                    dichVu.setGia(Integer.parseInt(item.child("gia").getValue().toString()));
                    dichVu.setDonVi(item.child("donVi").getValue().toString());
                    dichVu.setLinkHinh(item.child("linkHinh").getValue().toString());
                    dichVuList.add(dichVu);
                    Log.d("BBB", dichVu.toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void LayHoaDonPhong(DatabaseReference dataHoaDon, List<HoaDon> hoaDonList){
        dataHoaDon.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot item:snapshot.getChildren()) {
                    HoaDon hoaDon = new HoaDon();//String id, String thang, String ngayThanhToan, String hanThanhToan, int tienPhong, int tienDichVu, String ghiChu, int trangThai
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

    private void LayNguoiThuePhong() {
        for (Nha nha:nhaHangDauList) {
            dataPhong = dataNha.child(nha.getId()).child("Phong");
            dataPhong.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot items:snapshot.getChildren()) {
                        dataNguoiThue = dataPhong.child(items.child("id").getValue().toString()).child("NguoiThue");
                        dataNguoiThue.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                List<NguoiThue> nguoiThueList = new ArrayList<>();
                                for (DataSnapshot item : snapshot.getChildren()) {
                                    NguoiThue nguoiThue = new NguoiThue();
                                    nguoiThue.setId(item.child("id").getValue().toString());
                                    nguoiThue.setHoTen(item.child("hoTen").getValue().toString());
                                    nguoiThue.setSoDienThoai(item.child("soDienThoai").getValue().toString());
                                    nguoiThue.setEmail(item.child("email").getValue().toString());
                                    nguoiThue.setNgaySinh(item.child("ngaySinh").getValue().toString());
                                    nguoiThue.setNoiSinh(item.child("noiSinh").getValue().toString());
                                    nguoiThue.setCanCuocCongDan(item.child("canCuocCongDan").getValue().toString());
                                    nguoiThue.setNgayCap(item.child("ngayCap").getValue().toString());
                                    nguoiThue.setNoiCap(item.child("noiCap").getValue().toString());

                                    nguoiThueList.add(nguoiThue);
                                    Log.d("BBB", nguoiThue.toString());
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
    }*/
}