package com.example.a37046.foods.Adapter;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.example.a37046.foods.R;
import com.example.a37046.foods.activity.StoreDetailsActivity;
import com.example.a37046.foods.entity.AllUserCollection;
import com.example.a37046.foods.entity.HomeEntity;
import com.example.a37046.foods.entity.Success;
import com.example.a37046.foods.util.HttpUtil;


import java.io.IOException;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static android.content.Context.MODE_PRIVATE;

public class AllUserCollectionAdapter extends RecyclerView.Adapter<AllUserCollectionAdapter.Viewholder>{
    private List<AllUserCollection> allUserCollectionList;
    private int type;
    public AllUserCollectionAdapter(List<AllUserCollection> allUserCollectionList) {
        this.allUserCollectionList = allUserCollectionList;

    }

    public AllUserCollectionAdapter(List<AllUserCollection> allUserCollectionList, int type) {
        this.allUserCollectionList = allUserCollectionList;
        this.type = type;
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
    public void onBindViewHolder(@NonNull Viewholder holder, final int position) {
        final AllUserCollection allUserCollection = allUserCollectionList.get(position);
        Glide.with(holder.imageView.getContext()).load(allUserCollection.getPic()).into(holder.imageView);
        if (type==0){
            holder.shopName.setText(allUserCollection.getShopname());
            holder.location.setText(allUserCollection.getAddress());


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

            holder.cannel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    allUserCollectionList.remove(position);
                    notifyDataSetChanged();
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                OkHttpClient okHttpClient=new OkHttpClient();
                                Request request1=new Request.Builder()
                                        .url("http://" + HttpUtil.SERVER + "/userCollectShop.do?user_id="+getUserId(v)+"&shop_id="+allUserCollection.getShop_id())
                                        .build();
                                Response response1=okHttpClient.newCall(request1).execute();
                                //未使用  等待优化
                                Success suc1;
                                if (response1.isSuccessful()){
                                    String json=response1.body().string();
                                    suc1= JSON.parseObject(json,Success.class);
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                }
            });

        }else{
            holder.shopName.setText(allUserCollection.getFoodname());
            holder.location.setText(String.valueOf(allUserCollection.getPrice()+"元"));
            holder.enter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(v.getContext(),"enter",Toast.LENGTH_SHORT);
                }
            });
        }

    }
    /**
     * 获取用户id
     */
    public String getUserId(View v){
        SharedPreferences pref=v.getContext().getSharedPreferences("login",MODE_PRIVATE);
        String id =pref.getString("login_id","");
        return id;
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
