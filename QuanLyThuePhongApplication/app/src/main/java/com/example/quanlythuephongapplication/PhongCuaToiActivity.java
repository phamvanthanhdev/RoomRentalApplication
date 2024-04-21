package com.example.quanlythuephongapplication;

import static com.example.quanlythuephongapplication.MainActivity.khachHang;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.quanlythuephongapplication.adapter.DichVuAdapter;
import com.example.quanlythuephongapplication.adapter.NguoiThueAdapter;
import com.example.quanlythuephongapplication.adapter.NhaHangDauAdapter;
import com.example.quanlythuephongapplication.model.DichVu;
import com.example.quanlythuephongapplication.model.NguoiThue;
import com.example.quanlythuephongapplication.model.Nha;
import com.example.quanlythuephongapplication.model.Phong;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class PhongCuaToiActivity extends AppCompatActivity {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference dataNha = database.getReference("Nha");
    DatabaseReference dataPhong;
    DatabaseReference dataDV;
    DatabaseReference dataNguoiThue;
    DatabaseReference dataGioiTinh;
    List<DichVu> dichVuList = new ArrayList<>();
    List<String> gioiTinhList = new ArrayList<>();
    List<NguoiThue> nguoiThueList = new ArrayList<>();
    Nha nhaCuaToi;
    Phong phongCuaToi;
    ImageView imgHinh;
    TextView txtTenPhong, txtGiaPhong, txtTenNha, txtTang, txtGioiHanNguoiThue, txtDienTich, txtTienCoc, txtSoPhongNgu, txtSoPhongKhach, txtDoiTuongThue, txtMoTa, txtLuuY;
    RecyclerView rvDichVu, rvNguoiThue;
    Button btnHopDong, btnHoaDon, btnLienHe;
    DichVuAdapter dichVuAdapter;
    NguoiThueAdapter nguoiThueAdapter;
    final Handler handler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phong_cua_toi);

        if(khachHang.getTrangThaiThue() == 0){
            Toast.makeText(this, "Bạn chưa thuê phòng nào.", Toast.LENGTH_SHORT).show();
            finish();
        }

        LayPhongCuaToi();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //Log.d("AAA", nhaCuaToi.toString());
                //Log.d("BBB", phongCuaToi.toString());
                LayDichVuPhong();
                LayGioiTinhPhong();
                LayNguoiThuePhong();
            }
        }, 2000);
        AnhXa();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                HienThiDuLieu();
            }
        }, 1000);
        setDichVuRecycler();
        setNguoiThueRecycler();
        setEvent();
    }

    private void LayPhongCuaToi() {
        dataNha.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot item:snapshot.getChildren()) {
                    if(nhaCuaToi!=null){
                        break;
                    }
                    Log.d("AAA", item.child("id").getValue().toString() +" idNha");
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

                    dataPhong = database.getReference("Nha/" + nha.getId()+"/Phong");
                    dataPhong.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot item1:snapshot.getChildren()) {
                                if(nhaCuaToi!=null){
                                    break;
                                }
                                Log.d("BBB", item1.child("id").getValue().toString() +" idP");
                                Phong phong = new Phong();
                                phong.setId(item1.child("id").getValue().toString());
                                phong.setLinkHinh(item1.child("linkHinh").getValue().toString());
                                phong.setTenPhong(item1.child("tenPhong").getValue().toString());
                                phong.setGiaPhong(Integer.parseInt(item1.child("giaPhong").getValue().toString()));
                                phong.setTangSo(Integer.parseInt(item1.child("tangSo").getValue().toString()));
                                phong.setSoPhongNgu(Integer.parseInt(item1.child("soPhongNgu").getValue().toString()));
                                phong.setSoPhongKhach(Integer.parseInt(item1.child("soPhongKhach").getValue().toString()));
                                phong.setDienTich(Double.parseDouble(item1.child("dienTich").getValue().toString()));
                                phong.setGioiHanNguoiThue(Integer.parseInt(item1.child("gioiHanNguoiThue").getValue().toString()));
                                phong.setTienCoc(Integer.parseInt(item1.child("tienCoc").getValue().toString()));
                                phong.setMoTa(item1.child("moTa").getValue().toString());
                                phong.setLuuY(item1.child("luuY").getValue().toString());

                                dataNguoiThue = database.getReference("Nha/" + nha.getId()+"/Phong/"+phong.getId()+"/NguoiThue");
                                dataNguoiThue.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        for (DataSnapshot item2:snapshot.getChildren()) {
                                            if(item2.child("email").getValue()!= null) {
                                                Log.d("CCC", item2.child("email").getValue().toString() + " idNThue");
                                                String emailNguoiThue = item2.child("email").getValue().toString();
                                                if (emailNguoiThue.trim().equals(khachHang.getEmail().trim())) {
                                                    nhaCuaToi = nha;
                                                    phongCuaToi = phong;
                                                    break;
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
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void setEvent() {
        btnHopDong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PhongCuaToiActivity.this, HopDongActivity.class);
                intent.putExtra("nha", nhaCuaToi);
                intent.putExtra("phong", phongCuaToi);
                startActivity(intent);
            }
        });

        btnHoaDon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PhongCuaToiActivity.this, HoaDonActivity.class);
                intent.putExtra("nha", nhaCuaToi);
                intent.putExtra("phong", phongCuaToi);
                startActivity(intent);
            }
        });

        btnLienHe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + "0987658686"));
                startActivity(intent);
            }
        });
    }

    private void setDichVuRecycler(){
        rvDichVu = findViewById(R.id.recyclerViewDichVu);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        rvDichVu.setLayoutManager(layoutManager);
        dichVuAdapter = new DichVuAdapter(this, dichVuList);
        rvDichVu.setAdapter(dichVuAdapter);
    }

    private  void setNguoiThueRecycler(){
        rvNguoiThue = findViewById(R.id.recyclerViewNguoiThue);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        rvNguoiThue.setLayoutManager(layoutManager);
        nguoiThueAdapter = new NguoiThueAdapter(this, nguoiThueList);
        rvNguoiThue.setAdapter(nguoiThueAdapter);
    }

    private void HienThiDuLieu() {
        //Glide.with(this).load(phongCuaToi.getLinkHinh()).into(imgHinh);
        txtTenPhong.setText(phongCuaToi.getTenPhong());
        txtGiaPhong.setText(phongCuaToi.getGiaPhong()+"đ/tháng");
        txtTenNha.setText(nhaCuaToi.getTenNha());
        txtTang.setText(phongCuaToi.getTangSo()+"");
        txtGioiHanNguoiThue.setText(phongCuaToi.getGioiHanNguoiThue()+"");
        txtDienTich.setText(phongCuaToi.getDienTich()+"m2");
        txtTienCoc.setText(phongCuaToi.getTienCoc()+"đ");
        txtSoPhongNgu.setText(phongCuaToi.getSoPhongNgu()+"");
        txtSoPhongKhach.setText(phongCuaToi.getSoPhongKhach()+"");

        txtMoTa.setText(phongCuaToi.getMoTa());
        txtLuuY.setText(phongCuaToi.getLuuY());

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                String gioiTinhDB = "";
                for (String gioiTinh:gioiTinhList) {
                    gioiTinhDB += gioiTinh+", ";
                }
                txtDoiTuongThue.setText(gioiTinhDB);
            }
        }, 1000);

    }

    private void AnhXa() {
        imgHinh = findViewById(R.id.imageViewHinh);
        btnHoaDon = findViewById(R.id.buttonHoaDon);
        btnHopDong = findViewById(R.id.buttonHopDong);
        btnLienHe = findViewById(R.id.buttonLienHe);
        txtTenPhong = findViewById(R.id.textViewTenPhong);
        txtTenNha = findViewById(R.id.textViewTenNha);
        txtTang = findViewById(R.id.textViewTang);
        txtGioiHanNguoiThue = findViewById(R.id.textViewGioiHanNguoiThue);
        txtDienTich = findViewById(R.id.textViewDienTich);
        txtTienCoc = findViewById(R.id.textViewTienCoc);
        txtSoPhongNgu = findViewById(R.id.textViewSoPhongNgu);
        txtGiaPhong = findViewById(R.id.textViewGiaPhong);
        txtSoPhongKhach = findViewById(R.id.textViewSoPhongKhach);
        txtDoiTuongThue = findViewById(R.id.textViewDoiTuongThue);
        txtMoTa = findViewById(R.id.textViewMoTa);
        txtLuuY = findViewById(R.id.textViewLuuY);
    }

    private void LayDichVuPhong(){
        dataDV = database.getReference("Nha/" + nhaCuaToi.getId()+"/Phong/"+phongCuaToi.getId()+"/dichVuPhong");
        dataDV.addValueEventListener(new ValueEventListener() {
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
                }
                dichVuAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void LayNguoiThuePhong(){
        dataNguoiThue = database.getReference("Nha/" + nhaCuaToi.getId()+"/Phong/"+phongCuaToi.getId()+"/NguoiThue");
        dataNguoiThue.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot item:snapshot.getChildren()) {
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
                }
                nguoiThueAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void LayGioiTinhPhong(){
        dataGioiTinh = database.getReference("Nha/" + nhaCuaToi.getId()+"/Phong/"+phongCuaToi.getId()+"/gioiTinh");
        dataGioiTinh.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot item:snapshot.getChildren()) {
                    String gioiTinh = item.getValue().toString();
                    gioiTinhList.add(gioiTinh);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}