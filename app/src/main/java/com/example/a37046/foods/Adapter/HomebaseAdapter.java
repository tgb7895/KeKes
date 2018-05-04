package com.example.a37046.foods.Adapter;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.a37046.foods.R;
import com.example.a37046.foods.activity.StoreDetailsActivity;
import com.example.a37046.foods.entity.HomeEntity;

import java.util.List;

import javax.crypto.spec.PSource;

import me.zhanghai.android.materialratingbar.MaterialRatingBar;

public class HomebaseAdapter extends RecyclerView.Adapter<HomebaseAdapter.Viewholder> {

    List<HomeEntity> mhomeLists;
    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        final View view= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.home_adapter,parent,false);
        final Viewholder holder=new Viewholder(view);
        holder.foods.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int postion=holder.getAdapterPosition();
                HomeEntity homeEntity=mhomeLists.get(postion);


                int shopId = mhomeLists.get(postion).getShop_id();
                String shopName=mhomeLists.get(postion).getShopname();
                Intent intent=new Intent(view.getContext(), StoreDetailsActivity.class);

                Bundle bundle=new Bundle();
                bundle.putSerializable("home_entity",homeEntity);
                intent.putExtras(bundle);


                view.getContext().startActivity(intent);
            }
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        HomeEntity homeEntity = mhomeLists.get(position);
        holder.foodName.setText(homeEntity.getShopname());
        holder.homeAddress.setText(homeEntity.getAddress());
        holder.materialRatingBar.setRating(homeEntity.getLevel());

        Glide.with(holder.itemView.getContext()).load(homeEntity.getPic()).into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return mhomeLists.size();
    }

    static class Viewholder extends RecyclerView.ViewHolder{
        View foods;
        ImageView imageView;
        TextView foodName;
        TextView homeAddress;
        MaterialRatingBar materialRatingBar;
        public Viewholder(View itemView) {
            super(itemView);
            foods=itemView;
            imageView=itemView.findViewById(R.id.home_food_image);
            foodName=itemView.findViewById(R.id.home_food_name);
            homeAddress=itemView.findViewById(R.id.home_address);
            materialRatingBar=itemView.findViewById(R.id.home_star);

        }

    }


    public HomebaseAdapter(List<HomeEntity> homeList){
            mhomeLists=homeList;
    }


}
