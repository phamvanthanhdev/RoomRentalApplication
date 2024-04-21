package com.example.quanlythuephongapplication.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.quanlythuephongapplication.ChiTietPhongActivity;
import com.example.quanlythuephongapplication.R;
import com.example.quanlythuephongapplication.model.DangKyThuePhong;
import com.example.quanlythuephongapplication.model.NguoiThue;
import com.example.quanlythuephongapplication.model.Nha;
import com.example.quanlythuephongapplication.model.Phong;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class DangKyThuePhongAdapter extends RecyclerView.Adapter<DangKyThuePhongAdapter.viewHolder> {
    Context context;
    List<DangKyThuePhong> items;
    List<Nha> nhaList;
    List<Phong> phongList;

    public DangKyThuePhongAdapter(Context context, List<DangKyThuePhong> items, List<Nha> nhaList, List<Phong> phongList) {
        this.context = context;
        this.items = items;
        this.nhaList = nhaList;
        this.phongList = phongList;
    }

    @NonNull
    @Override
    public DangKyThuePhongAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.viewholder_dangkythuephong, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DangKyThuePhongAdapter.viewHolder holder, int position) {
        DangKyThuePhong dangKyThuePhong = items.get(position);
        Nha nha = nhaList.get(position);
        Phong phong = phongList.get(position);

        holder.txtTenPhongTenNha.setText(phong.getTenPhong() +" - " + nha.getTenNha());
        holder.txtGiaPhong.setText(phong.getGiaPhong() +"đ/tháng");
        holder.txtThoiGian.setText(dangKyThuePhong.getThoiGian());
        Glide.with(context).load(phong.getLinkHinh()).into(holder.imgHinh);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, ChiTietPhongActivity.class);
                intent.putExtra("nha", nha);
                intent.putExtra("phong", phong);
                context.startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return items.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        ImageView imgHinh;
        TextView txtTenPhongTenNha, txtGiaPhong, txtThoiGian;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            imgHinh = itemView.findViewById(R.id.imgHinh);
            txtTenPhongTenNha = itemView.findViewById(R.id.tenPhongtenNha);
            txtGiaPhong = itemView.findViewById(R.id.giaPhong);
            txtThoiGian = itemView.findViewById(R.id.thoiGian);
        }
    }
}
