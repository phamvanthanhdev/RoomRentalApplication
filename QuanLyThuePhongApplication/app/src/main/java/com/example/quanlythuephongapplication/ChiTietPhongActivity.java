package com.example.quanlythuephongapplication;

import static com.example.quanlythuephongapplication.MainActivity.khachHang;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
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
import com.example.quanlythuephongapplication.adapter.PhongAdapter;
import com.example.quanlythuephongapplication.model.DangKyThuePhong;
import com.example.quanlythuephongapplication.model.DichVu;
import com.example.quanlythuephongapplication.model.Nha;
import com.example.quanlythuephongapplication.model.Phong;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ChiTietPhongActivity extends AppCompatActivity {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference dataDV;
    DatabaseReference dataDangKyThuePhong;
    DatabaseReference dataGioiTinh;
    DatabaseReference dataYeuThichPhong;
    List<DichVu> dichVuList = new ArrayList<>();
    List<String> gioiTinhList = new ArrayList<>();
    Nha nha;
    Phong phong;
    ImageView imgHinh, imgYeuThichPhong;
    TextView txtTenPhong, txtGiaPhong, txtTenNha, txtTang, txtGioiHanNguoiThue, txtDienTich, txtTienCoc, txtSoPhongNgu, txtSoPhongKhach, txtDoiTuongThue, txtMoTa, txtLuuY;
    RecyclerView rvDichVu;
    Button btnThuePhongNgay;
    DichVuAdapter dichVuAdapter;
    Boolean isYeuThichPhong = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_phong);

        Intent intent = getIntent();
        nha = (Nha) intent.getSerializableExtra("nha");
        phong = (Phong) intent.getSerializableExtra("phong");
        dataDV = database.getReference("Nha/" + nha.getId()+"/Phong/"+phong.getId()+"/dichVuPhong");
        dataGioiTinh = database.getReference("Nha/" + nha.getId()+"/Phong/"+phong.getId()+"/gioiTinh");
        dataYeuThichPhong = database.getReference("YeuThichPhong/"+khachHang.getId());
        dataDangKyThuePhong = database.getReference("DangKyThuePhong");

        LayDichVuPhong();
        LayGioiTinhPhong();
        KiemTraYeuThichPhong();
        AnhXa();
        HienThiDuLieu();
        setDichVuRecycler();
        setEvent();
    }

    private void setEvent() {
        imgYeuThichPhong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isYeuThichPhong){
                    imgYeuThichPhong.setImageResource(R.drawable.like_no);
                    XoaYeuThichPhong();
                    Log.d("BBB", "XOA SUCCESS");
                }else{
                    imgYeuThichPhong.setImageResource(R.drawable.like_yes);
                    ThemYeuThichPhong();
                    Log.d("BBB", "THEM SUCCESS");
                }
                isYeuThichPhong = !isYeuThichPhong;
            }
        });

        btnThuePhongNgay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DangKyThuePhong dangKyThuePhong = new DangKyThuePhong();
                dangKyThuePhong.setIdPhong(phong.getId());
                dangKyThuePhong.setIdNha(nha.getId());
                dangKyThuePhong.setIdKhachHang(khachHang.getId());

                Calendar calendar = Calendar.getInstance();
                int nam = calendar.get(Calendar.YEAR);
                int thang = calendar.get(Calendar.MONTH);
                int ngay = calendar.get(Calendar.DAY_OF_MONTH);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                calendar.set(nam,thang, ngay);
                dangKyThuePhong.setThoiGian(simpleDateFormat.format(calendar.getTime()));

                dangKyThuePhong.setId(dataDangKyThuePhong.push().getKey());
                dataDangKyThuePhong.child(dangKyThuePhong.getId()).setValue(dangKyThuePhong, new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                        if(error == null){
                            Toast.makeText(ChiTietPhongActivity.this, "Đăng ký thuê phòng thành công.", Toast.LENGTH_SHORT).show();
                            btnThuePhongNgay.setText("Đã đăng ký");
                        }else{
                            Toast.makeText(ChiTietPhongActivity.this, "Lỗi đăng ký thuê phòng", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                //Log.d("AAA", dangKyThuePhong.toString());
            }
        });
    }

    private void XoaYeuThichPhong() {
        dataYeuThichPhong.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot item:snapshot.getChildren()) {
                    String idPhong = item.getValue().toString();
                    if(idPhong.trim().equals(phong.getId().trim())){
                        String phongKey = item.getKey().toString();
                        dataYeuThichPhong.child(phongKey).removeValue();
                        //Log.d("AAA", "Xoa yeu thich thanh cong");
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void ThemYeuThichPhong() {
        dataYeuThichPhong.push().setValue(phong.getId());
    }

    private void KiemTraYeuThichPhong() {
        dataYeuThichPhong.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot item:snapshot.getChildren()) {
                    if(item.getValue() != null) {
                        String idPhong = item.getValue().toString();
                        if (idPhong.trim().equals(phong.getId().trim())) {
                            isYeuThichPhong = true;
                            break;
                        }
                        //Log.d("AAA", isYeuThichPhong.toString());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

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

    private void HienThiDuLieu() {
        Glide.with(this).load(phong.getLinkHinh()).into(imgHinh);
        txtTenPhong.setText(phong.getTenPhong());
        txtGiaPhong.setText(phong.getGiaPhong()+"đ/tháng");
        txtTenNha.setText(nha.getTenNha());
        txtTang.setText(phong.getTangSo()+"");
        txtGioiHanNguoiThue.setText(phong.getGioiHanNguoiThue()+"");
        txtDienTich.setText(phong.getDienTich()+"m2");
        txtTienCoc.setText(phong.getTienCoc()+"đ");
        txtSoPhongNgu.setText(phong.getSoPhongNgu()+"");
        txtSoPhongKhach.setText(phong.getSoPhongKhach()+"");

        txtMoTa.setText(phong.getMoTa());
        txtLuuY.setText(phong.getLuuY());

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                String gioiTinhDB = "";
                for (String gioiTinh:gioiTinhList) {
                    gioiTinhDB += gioiTinh+", ";
                }
                txtDoiTuongThue.setText(gioiTinhDB);

                if(isYeuThichPhong){
                    imgYeuThichPhong.setImageResource(R.drawable.like_yes);
                }else{
                    imgYeuThichPhong.setImageResource(R.drawable.like_no);
                }
            }
        }, 1000);

    }

    private void AnhXa() {
        imgHinh = findViewById(R.id.imageViewHinh);
        imgYeuThichPhong = findViewById(R.id.imageViewYeuThichPhong);
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
        btnThuePhongNgay = findViewById(R.id.buttonThuePhongNgay);
    }

    private void LayDichVuPhong(){
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
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void LayGioiTinhPhong(){
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