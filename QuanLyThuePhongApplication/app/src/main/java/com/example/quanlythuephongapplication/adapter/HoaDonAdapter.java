package com.example.quanlythuephongapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quanlythuephongapplication.R;
import com.example.quanlythuephongapplication.model.HoaDon;
import com.example.quanlythuephongapplication.model.NguoiThue;

import java.util.List;

public class HoaDonAdapter extends RecyclerView.Adapter<HoaDonAdapter.viewHolder> {

    Context context;
    List<HoaDon> items;

    public HoaDonAdapter(Context context, List<HoaDon> items) {
        this.context = context;
        this.items = items;
    }


    @NonNull
    @Override
    public HoaDonAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.viewholder_hoadon, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HoaDonAdapter.viewHolder holder, int position) {
        holder.txtThang.setText("#"+items.get(position).getThang());
        holder.txtTienPhong.setText(items.get(position).getTienPhong()+" đ");
        holder.txtTienDichVu.setText(items.get(position).getTienDichVu()+" đ");
        holder.txtHanThanhThoan.setText(items.get(position).getHanThanhToan());
        holder.txtNgayThanhToan.setText(items.get(position).getNgayThanhToan());
        if(items.get(position).getTrangThai() == 0) holder.txtTrangThai.setText("Chưa thanh toán");
        if(items.get(position).getTrangThai() == 1) holder.txtTrangThai.setText("Đã thanh toán");

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }


    @Override
    public int getItemCount() {
        return items.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        //ImageView nhaHinhAnh;
        TextView txtThang, txtTrangThai, txtTienPhong, txtTienDichVu, txtTongTien, txtHanThanhThoan, txtNgayThanhToan;
        Button btnChiTiet, btnThanhToan;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            txtThang = itemView.findViewById(R.id.textViewThang);
            txtTrangThai = itemView.findViewById(R.id.textViewTrangThai);
            txtTienPhong = itemView.findViewById(R.id.textViewTienPhong);
            txtTienDichVu = itemView.findViewById(R.id.textViewTienDichVu);
            txtTongTien = itemView.findViewById(R.id.textViewTongTien);
            txtHanThanhThoan = itemView.findViewById(R.id.textViewHanThanhToan);
            txtNgayThanhToan = itemView.findViewById(R.id.textViewNgayThanhToan);
            btnChiTiet = itemView.findViewById(R.id.buttonChiTiet);
            btnThanhToan = itemView.findViewById(R.id.buttonThanhToan);
        }
    }
}
