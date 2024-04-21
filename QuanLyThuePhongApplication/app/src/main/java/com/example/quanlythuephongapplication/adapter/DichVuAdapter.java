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

import com.example.quanlythuephongapplication.DetailsActivity;
import com.example.quanlythuephongapplication.R;
import com.example.quanlythuephongapplication.model.DichVu;
import com.example.quanlythuephongapplication.model.RecentsData;

import java.util.List;

public class DichVuAdapter extends RecyclerView.Adapter<DichVuAdapter.RecentsViewHolder>{
    Context context;
    List<DichVu> items;

    public DichVuAdapter(Context context, List<DichVu> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public DichVuAdapter.RecentsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //context = parent.getContext();
        View view  = LayoutInflater.from(context).inflate(R.layout.viewholder_dichvu, null);
        return new RecentsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DichVuAdapter.RecentsViewHolder holder, int position) {
        holder.txtTenDichVu.setText(items.get(position).getTenDichVu());
        holder.txtGiaDichVu.setText(items.get(position).getGia()+"đ/"+items.get(position).getDonVi());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class RecentsViewHolder extends RecyclerView.ViewHolder {
        TextView txtTenDichVu, txtGiaDichVu;

        public RecentsViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTenDichVu = itemView.findViewById(R.id.textViewTenDichVu);
            txtGiaDichVu = itemView.findViewById(R.id.textViewGiaDichVu);
        }
    }
}
