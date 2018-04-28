package com.example.a37046.foods.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.example.a37046.foods.Adapter.HomebaseAdapter;
import com.example.a37046.foods.Adapter.StoreDetailAdapter;
import com.example.a37046.foods.R;
import com.example.a37046.foods.entity.FoodByShop;
import com.example.a37046.foods.util.HttpUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class StoreDetailsActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    List<FoodByShop> foodByShops;
    StoreDetailAdapter storeDetailAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_details);


        foodByShops=new ArrayList<>();

        recyclerView=findViewById(R.id.food_shop_list);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        storeDetailAdapter = new StoreDetailAdapter(foodByShops);
        recyclerView.setAdapter(storeDetailAdapter);

        loadingNetworkData();

    }

    /**
     * 获取上个活动传来的shopId
     */
    public int getShopId(){
        int shopId = getIntent().getIntExtra("shopId",-1);
        if (shopId!=-1){
            return shopId;
        }
        return shopId;
    }


    public void loadingNetworkData(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient okHttpClient = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url("http://" + HttpUtil.SERVER + "/getFoodByShop.do?shop_id="+getShopId())
                            .build();
                    Response response = okHttpClient.newCall(request).execute();
                    if (response.isSuccessful()){
                        String jsonData=response.body().string();
                        final List<FoodByShop> foodByShopsData= JSON.parseArray(jsonData,FoodByShop.class);
                        Log.d("价格",String.valueOf(foodByShopsData.get(0).getPrice()));
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                foodByShops.addAll(foodByShopsData);
                                storeDetailAdapter.notifyDataSetChanged();
                            }
                        });
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
