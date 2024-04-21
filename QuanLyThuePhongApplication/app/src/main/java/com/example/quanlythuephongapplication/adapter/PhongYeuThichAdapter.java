package com.example.quanlythuephongapplication.adapter;

import static com.example.quanlythuephongapplication.MainActivity.khachHang;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.quanlythuephongapplication.ChiTietPhongActivity;
import com.example.quanlythuephongapplication.PhongYeuThichActivity;
import com.example.quanlythuephongapplication.R;
import com.example.quanlythuephongapplication.model.Nha;
import com.example.quanlythuephongapplication.model.Phong;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;
import java.util.Map;

public class PhongYeuThichAdapter extends RecyclerView.Adapter<PhongYeuThichAdapter.RecentsViewHolder>{
    PhongYeuThichActivity context;
    List<Phong> items;
    Map<Nha, Phong> maps;

    public PhongYeuThichAdapter(PhongYeuThichActivity context,List<Phong>items, Map<Nha,Phong> maps) {
        this.context = context;
        this.items = items;
        this.maps = maps;
    }

    @NonNull
    @Override
    public PhongYeuThichAdapter.RecentsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //context = parent.getContext();
        View view  = LayoutInflater.from(context).inflate(R.layout.viewholder_phongyeuthich, null);
        return new RecentsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PhongYeuThichAdapter.RecentsViewHolder holder, int position) {
        holder.tenPhong.setText(items.get(position).getTenPhong());
        holder.giaPhong.setText(items.get(position).getGiaPhong()+ "đ/tháng");
        Glide.with(context).load(items.get(position).getLinkHinh()).into(holder.imgHinh);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (Map.Entry<Nha, Phong> entry:maps.entrySet()) {
                    if(items.get(position).getId().trim().equals(entry.getValue().getId().trim())){
                        Intent intent=new Intent(context, ChiTietPhongActivity.class);
                        intent.putExtra("nha", entry.getKey());
                        intent.putExtra("phong", entry.getValue());
                        context.startActivity(intent);
                    }
                }
            }
        });

        holder.imgYeuThichPhong2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //context.XoaYeuThichPhong(items.get(position));
                Toast.makeText(context, "Chỉ có thể bỏ yêu thích khi xem chi tiết nhà.", Toast.LENGTH_SHORT).show();
                //Log.d("DDD", "Tai sao lai xoa o day");
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class RecentsViewHolder extends RecyclerView.ViewHolder {
        ImageView imgHinh, imgYeuThichPhong2;
        TextView tenPhong, giaPhong;

        public RecentsViewHolder(@NonNull View itemView) {
            super(itemView);

            imgHinh = itemView.findViewById(R.id.imgHinh);
            imgYeuThichPhong2 = itemView.findViewById(R.id.imageViewYeuThichPhong2);
            tenPhong = itemView.findViewById(R.id.tenPhong);
            giaPhong = itemView.findViewById(R.id.giaPhong);
        }
    }


}
