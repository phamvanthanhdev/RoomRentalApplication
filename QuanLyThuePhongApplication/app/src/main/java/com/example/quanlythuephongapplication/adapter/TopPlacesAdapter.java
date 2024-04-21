package com.example.quanlythuephongapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quanlythuephongapplication.R;
import com.example.quanlythuephongapplication.model.TopPlacesData;

import java.util.List;

public class TopPlacesAdapter extends RecyclerView.Adapter<TopPlacesAdapter.TopPlacesViewHolder> {

    Context context;
    List<TopPlacesData> topPlacesDataList;

    public TopPlacesAdapter(Context context, List<TopPlacesData> topPlacesDataList) {
        this.context = context;
        this.topPlacesDataList = topPlacesDataList;
    }

    @NonNull
    @Override
    public TopPlacesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.top_places_row_item, parent, false);

        // here we create a recyclerview row item layout file
        return new TopPlacesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TopPlacesViewHolder holder, int position) {

        holder.countryName.setText(topPlacesDataList.get(position).getCountryName());
        holder.placeName.setText(topPlacesDataList.get(position).getPlaceName());
        holder.price.setText(topPlacesDataList.get(position).getPrice());
        holder.placeImage.setImageResource(topPlacesDataList.get(position).getImageUrl());
    }

    @Override
    public int getItemCount() {
        return topPlacesDataList.size();
    }

    public static final class TopPlacesViewHolder extends RecyclerView.ViewHolder{

        ImageView placeImage;
        TextView placeName, countryName, price;

        public TopPlacesViewHolder(@NonNull View itemView) {
            super(itemView);

            placeImage = itemView.findViewById(R.id.hinhAnhNha);
            placeName = itemView.findViewById(R.id.tenPhongtenNha);
            countryName = itemView.findViewById(R.id.diaChiNha);
            price = itemView.findViewById(R.id.huyenTinhNha);

        }
    }
}
