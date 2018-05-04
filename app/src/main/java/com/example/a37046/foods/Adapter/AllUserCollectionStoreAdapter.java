package com.example.a37046.foods.Adapter;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.a37046.foods.R;
import com.example.a37046.foods.activity.StoreDetailsActivity;
import com.example.a37046.foods.entity.AllUserCollection;
import com.example.a37046.foods.entity.HomeEntity;


import java.util.List;

public class AllUserCollectionStoreAdapter extends RecyclerView.Adapter<AllUserCollectionStoreAdapter.Viewholder>{
    private List<AllUserCollection> allUserCollectionList;

    public AllUserCollectionStoreAdapter(List<AllUserCollection> allUserCollectionList) {
        this.allUserCollectionList = allUserCollectionList;

    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_all_user_connect_list,parent,false);

        Viewholder viewholder=new Viewholder(view);

        return viewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        final AllUserCollection allUserCollection = allUserCollectionList.get(position);

        holder.shopName.setText(allUserCollection.getShopname());
        holder.location.setText(allUserCollection.getAddress());

        Glide.with(holder.imageView.getContext()).load(allUserCollection.getPic()).into(holder.imageView);

        holder.enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomeEntity homeEntity=new HomeEntity();
                homeEntity.setShopname(allUserCollection.getShopname());
                homeEntity.setShop_id(allUserCollection.getShop_id());

                Bundle bundle = new Bundle();
                bundle.putSerializable("home_entity",homeEntity);

                Intent intent=new Intent(v.getContext(),StoreDetailsActivity.class);
                intent.putExtras(bundle);

                v.getContext().startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return allUserCollectionList.size();
    }

    static class Viewholder extends RecyclerView.ViewHolder{

        View UserConnect;
        ImageView imageView;
        TextView shopName;
        TextView location;
        Button enter;
        Button cannel;

        public Viewholder(View itemView) {
            super(itemView);
            UserConnect=itemView;
            imageView=itemView.findViewById(R.id.adapter_all_user_connect_list_img);
            shopName=itemView.findViewById(R.id.adapter_all_user_connect_list_shop_name);
            location=itemView.findViewById(R.id.adapter_all_user_connect_list_address);
            enter=itemView.findViewById(R.id.adapter_all_user_connect_list_enter);
            cannel=itemView.findViewById(R.id.adapter_all_user_connect_list_cancel);


        }
    }
}
