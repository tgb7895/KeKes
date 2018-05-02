package com.example.a37046.foods.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.example.a37046.foods.Adapter.HomebaseAdapter;
import com.example.a37046.foods.Adapter.StoreDetailAdapter;
import com.example.a37046.foods.R;
import com.example.a37046.foods.entity.FoodByShop;
import com.example.a37046.foods.entity.HomeEntity;
import com.example.a37046.foods.entity.Success;
import com.example.a37046.foods.util.HttpUtil;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class StoreDetailsActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    List<FoodByShop> foodByShops;
    StoreDetailAdapter storeDetailAdapter;
    //判断收藏
    private int flagCollect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_details);
        Intent intent = getIntent();
        HomeEntity home_entity = (HomeEntity) intent.getSerializableExtra("home_entity");



        //收藏按钮
        Button collectButton=findViewById(R.id.details_like_button);
        collectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                collectionStore();
            }
        });

        TextView detailsName=findViewById(R.id.details_name);
        detailsName.setText(getShopName());

        foodByShops=new ArrayList<>();
        recyclerView=findViewById(R.id.food_shop_list);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        storeDetailAdapter = new StoreDetailAdapter(foodByShops,home_entity);
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

    /**
     * 获取上个互动传来的shopname
     * @return
     */
    public String getShopName(){
        String shopName=getIntent().getStringExtra("shopname");

        return shopName;
    }
    /**
     * 获取用户id
     */
    public String getUserId(){
        SharedPreferences pref=getSharedPreferences("login",MODE_PRIVATE);
        String id =pref.getString("login_id","");
        return id;
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

    public void collectionStore(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient okHttpClient=new OkHttpClient();
                    Request request1=new Request.Builder()
                            .url("http://" + HttpUtil.SERVER + "/userCollectShop.do?user_id="+getUserId()+"&shop_id="+getShopId())
                            .build();
                    Response response1=okHttpClient.newCall(request1).execute();
                    //未使用  等待优化
                    Success suc1;
                    if (response1.isSuccessful()){
                        String json=response1.body().string();
                        suc1=JSON.parseObject(json,Success.class);
                    }
                    Request request2=new Request.Builder()
                            .url("http://" + HttpUtil.SERVER + "/isCollected.do?user_id="+getUserId()+"&shop_food_id="+getShopId()+"&flag=0")
                            .build();
                    Response response2=okHttpClient.newCall(request2).execute();
                    Success suc2 = null;
                    if (response1.isSuccessful()){
                        String json=response2.body().string();
                        suc2=JSON.parseObject(json,Success.class);
                    }

                    final Success finalSuc = suc2;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (1 == finalSuc.getCollected()){
                                Toast.makeText(StoreDetailsActivity.this,"已经取消收藏",Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(StoreDetailsActivity.this,"收藏成功",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
