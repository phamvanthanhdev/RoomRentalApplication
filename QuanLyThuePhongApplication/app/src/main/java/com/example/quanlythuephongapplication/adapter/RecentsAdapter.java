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
import com.example.quanlythuephongapplication.model.RecentsData;

import java.util.List;

public class RecentsAdapter extends RecyclerView.Adapter<RecentsAdapter.RecentsViewHolder>{
    Context context;
    List<RecentsData> recentsDataList;

    public RecentsAdapter(Context context, List<RecentsData> recentsDataList) {
        this.context = context;
        this.recentsDataList = recentsDataList;
    }

    @NonNull
    @Override
    public RecentsAdapter.RecentsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //context = parent.getContext();
        View view  = LayoutInflater.from(context).inflate(R.layout.recents_row_item, null);
        return new RecentsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecentsAdapter.RecentsViewHolder holder, int position) {
        holder.countryName.setText(recentsDataList.get(position).getCountryName());
        holder.placeName.setText(recentsDataList.get(position).getPlaceName());
        holder.price.setText(recentsDataList.get(position).getPrice());
        holder.placeImage.setImageResource(recentsDataList.get(position).getImageUrl());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(context, DetailsActivity.class);
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return recentsDataList.size();
    }

    public class RecentsViewHolder extends RecyclerView.ViewHolder {
        ImageView placeImage;
        TextView placeName, countryName, price;

        public RecentsViewHolder(@NonNull View itemView) {
            super(itemView);

            placeImage = itemView.findViewById(R.id.hinhAnhNha);
            placeName = itemView.findViewById(R.id.tenPhongtenNha);
            countryName = itemView.findViewById(R.id.diaChiNha);
            price = itemView.findViewById(R.id.huyenTinhNha);

        }
    }
}
