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
import com.example.quanlythuephongapplication.model.Nha;

import java.util.List;

public class NhaHangDauAdapter extends RecyclerView.Adapter<NhaHangDauAdapter.viewHolder> {

    Context context;
    List<Nha> items;

    public NhaHangDauAdapter(Context context, List<Nha> items) {
        this.context = context;
        this.items = items;
    }


    @NonNull
    @Override
    public NhaHangDauAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.top_places_row_item, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NhaHangDauAdapter.viewHolder holder, int position) {
        holder.nhaTen.setText(items.get(position).getTenNha());
        holder.nhaDiaChi.setText(items.get(position).getDiaChi());
        holder.nhaHuyenTinh.setText(items.get(position).getQuanHuyen()+", " + items.get(position).getTinhThanh());
        Glide.with(context).load(items.get(position).getLinkHinh()).into(holder.nhaHinhAnh);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, ChiTietNhaActivity.class);
                intent.putExtra("nha", items.get(position));
                context.startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return items.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        ImageView nhaHinhAnh;
        TextView nhaTen, nhaDiaChi, nhaHuyenTinh;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            nhaHinhAnh = itemView.findViewById(R.id.hinhAnhNha);
            nhaTen = itemView.findViewById(R.id.tenPhongtenNha);
            nhaDiaChi = itemView.findViewById(R.id.diaChiNha);
            nhaHuyenTinh = itemView.findViewById(R.id.huyenTinhNha);
        }
    }
}
