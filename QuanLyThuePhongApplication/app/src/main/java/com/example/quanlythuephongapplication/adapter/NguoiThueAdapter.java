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
import com.example.quanlythuephongapplication.ChiTietNhaActivity;
import com.example.quanlythuephongapplication.R;
import com.example.quanlythuephongapplication.model.NguoiThue;
import com.example.quanlythuephongapplication.model.Nha;

import java.util.List;

public class NguoiThueAdapter extends RecyclerView.Adapter<NguoiThueAdapter.viewHolder> {

    Context context;
    List<NguoiThue> items;

    public NguoiThueAdapter(Context context, List<NguoiThue> items) {
        this.context = context;
        this.items = items;
    }


    @NonNull
    @Override
    public NguoiThueAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.viewholder_nguoithue, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NguoiThueAdapter.viewHolder holder, int position) {
        holder.txtTen.setText(items.get(position).getHoTen());
        holder.txtEmail.setText(items.get(position).getEmail());
        holder.txtSdt.setText(items.get(position).getSoDienThoai());
        //Glide.with(context).load(items.get(position).getLinkHinh()).into(holder.nhaHinhAnh);

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
        TextView txtTen, txtEmail, txtSdt;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            //nhaHinhAnh = itemView.findViewById(R.id.hinhAnhNha);
            txtTen = itemView.findViewById(R.id.tenNguoiThue);
            txtEmail = itemView.findViewById(R.id.emailNguoiThue);
            txtSdt = itemView.findViewById(R.id.sdtNguoiThue);
        }
    }
}
