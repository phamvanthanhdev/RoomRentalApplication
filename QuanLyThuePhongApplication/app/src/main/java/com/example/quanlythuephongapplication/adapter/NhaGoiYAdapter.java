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

public class NhaGoiYAdapter extends RecyclerView.Adapter<NhaGoiYAdapter.RecentsViewHolder>{
    Context context;
    List<Nha> items;

    public NhaGoiYAdapter(Context context, List<Nha> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public NhaGoiYAdapter.RecentsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //context = parent.getContext();
        View view  = LayoutInflater.from(context).inflate(R.layout.recents_row_item, null);
        return new RecentsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NhaGoiYAdapter.RecentsViewHolder holder, int position) {
        holder.tenNha.setText(items.get(position).getTenNha());
        holder.diaChiNha.setText(items.get(position).getQuanHuyen()+ ", "+items.get(position).getTinhThanh());
        holder.soTang.setText("Số tầng: "+items.get(position).getSoTang());
        Glide.with(context).load(items.get(position).getLinkHinh()).into(holder.hinhAnhNha);

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

    public class RecentsViewHolder extends RecyclerView.ViewHolder {
        ImageView hinhAnhNha;
        TextView tenNha, diaChiNha, soTang;

        public RecentsViewHolder(@NonNull View itemView) {
            super(itemView);

            hinhAnhNha = itemView.findViewById(R.id.hinhAnhNha);
            tenNha = itemView.findViewById(R.id.tenPhongtenNha);
            diaChiNha = itemView.findViewById(R.id.diaChiNha);
            soTang = itemView.findViewById(R.id.soTang);
        }
    }
}
