package com.example.a37046.foods.Adapter;

import android.support.annotation.NonNull;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.a37046.foods.R;
import com.example.a37046.foods.entity.FoodByShop;

import java.util.List;

public class StoreDetailAdapter extends RecyclerView.Adapter<StoreDetailAdapter.Viewholder>{

    List<FoodByShop> mfoodByShops;

    public StoreDetailAdapter(List<FoodByShop> foodByShops) {
        mfoodByShops=foodByShops;
    }

    @NonNull
    @Override
    public StoreDetailAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       final View view= LayoutInflater.from(parent.getContext())
               .inflate(R.layout.home_food_shop_adapter,parent,false);
       final Viewholder holder=new Viewholder(view);


        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final StoreDetailAdapter.Viewholder holder,int position) {

        FoodByShop foodByShop=mfoodByShops.get(position);
        Glide.with(holder.itemView.getContext()).load(foodByShop.getPic()).into(holder.imageView);
        holder.foodName.setText(foodByShop.getFoodname());
        holder.price.setText(String.valueOf(foodByShop.getPrice()));
        holder.intro.setText(foodByShop.getIntro());


        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return mfoodByShops.size();
    }

    static class Viewholder extends RecyclerView.ViewHolder{
        View view;
        ImageView imageView;
        TextView foodName;
        TextView price;
        TextView intro;
        public Viewholder(View itemView) {
            super(itemView);
            view=itemView;
            imageView=itemView.findViewById(R.id.food_shop_pic);
            foodName=itemView.findViewById(R.id.food_shop_foodname);
            price=itemView.findViewById(R.id.food_shop_price);
            intro=itemView.findViewById(R.id.food_shop_intro);
        }
    }



}
