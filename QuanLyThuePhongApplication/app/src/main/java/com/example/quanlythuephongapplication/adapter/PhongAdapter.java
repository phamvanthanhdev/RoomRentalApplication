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
import com.example.quanlythuephongapplication.ChiTietPhongActivity;
import com.example.quanlythuephongapplication.R;
import com.example.quanlythuephongapplication.model.Nha;
import com.example.quanlythuephongapplication.model.Phong;

import java.util.List;

public class PhongAdapter extends RecyclerView.Adapter<PhongAdapter.RecentsViewHolder>{
    Context context;
    List<Phong> items;
    Nha nha;

    public PhongAdapter(Context context, List<Phong> items, Nha nha) {
        this.context = context;
        this.items = items;
        this.nha = nha;
    }

    @NonNull
    @Override
    public PhongAdapter.RecentsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //context = parent.getContext();
        View view  = LayoutInflater.from(context).inflate(R.layout.viewholder_phong, null);
        return new RecentsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PhongAdapter.RecentsViewHolder holder, int position) {
        holder.tenPhong.setText(items.get(position).getTenPhong());
        holder.giaPhong.setText(items.get(position).getGiaPhong()+ "đ/tháng");
        Glide.with(context).load(items.get(position).getLinkHinh()).into(holder.imgHinh);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, ChiTietPhongActivity.class);
                intent.putExtra("nha", nha);
                intent.putExtra("phong", items.get(position));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class RecentsViewHolder extends RecyclerView.ViewHolder {
        ImageView imgHinh;
        TextView tenPhong, giaPhong;

        public RecentsViewHolder(@NonNull View itemView) {
            super(itemView);

            imgHinh = itemView.findViewById(R.id.imgHinh);
            tenPhong = itemView.findViewById(R.id.tenPhong);
            giaPhong = itemView.findViewById(R.id.giaPhong);
        }
    }
}
