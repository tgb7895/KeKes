package com.example.a37046.foods.Adapter;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.example.a37046.foods.R;
import com.example.a37046.foods.activity.MenudetailsActivity;
import com.example.a37046.foods.entity.FoodDetail;
import com.example.a37046.foods.entity.HomeEntity;
import com.example.a37046.foods.util.HttpUtil;

import java.util.List;


public class SearchHistoryAdapter extends RecyclerView.Adapter<SearchHistoryAdapter.Viewholder> {

    private List<FoodDetail> history;


    public SearchHistoryAdapter(List<FoodDetail> history) {
        this.history = history;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext())
                .inflate(android.R.layout.simple_list_item_1,parent,false);

        Viewholder viewholder=new Viewholder(view);
        return viewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        final FoodDetail foodDetail = history.get(position);
        holder.searchText.setText(foodDetail.getFoodname());

        holder.text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomeEntity homeEntity=new HomeEntity();
                homeEntity.setShopname("");
                homeEntity.setShop_id(foodDetail.getShop_id());
                Intent intent=new Intent(v.getContext(),MenudetailsActivity.class);
                intent.putExtra("food_id",foodDetail.getFood_id());
                Bundle bundle=new Bundle();
                bundle.putSerializable("home_Entity_one",homeEntity);

                intent.putExtras(bundle);
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return history.size();
    }

    static class Viewholder extends RecyclerView.ViewHolder{
        View text;
        TextView searchText;
        public Viewholder(View itemView) {
            super(itemView);
            text=itemView;
            searchText=itemView.findViewById(android.R.id.text1);
        }
    }
}
