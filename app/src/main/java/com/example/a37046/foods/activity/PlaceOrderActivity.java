package com.example.a37046.foods.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.a37046.foods.Adapter.TimeSpinnerAdapter;
import com.example.a37046.foods.R;
import com.example.a37046.foods.entity.HomeEntity;

import java.text.SimpleDateFormat;
import java.util.Date;

public class PlaceOrderActivity extends AppCompatActivity implements View.OnClickListener{

    TextView userName;
    TextView shopName;
    TextView foodName;
    TextView foodPrice;
    TextView foodPricez;
    TextView orderTime;
    TextView buyCount;
    TextView sumPrice;

    Button add;
    Button red;

    Spinner spinner;


    String mUserName;
    String mPassword;
    String foodNames;
    String foodPrices;


    HomeEntity homeEntity;

    int count;//购买数量
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_order);
        Intent intent = getIntent();
        homeEntity=(HomeEntity) intent.getSerializableExtra("home_entity_two");
        foodNames = intent.getStringExtra("food_name");
        foodPrices=intent.getStringExtra("food_price");

        initView();
        getAccountPassword();
        downloadData();





    }

    public void initView(){
        userName=findViewById(R.id.place_order_username);
        shopName=findViewById(R.id.place_order_shop_name);
        foodName=findViewById(R.id.place_order_food_name);
        foodPrice=findViewById(R.id.place_order_food_price);
        foodPricez=findViewById(R.id.place_order_food_price_x);
        orderTime=findViewById(R.id.place_order_time);
        spinner=findViewById(R.id.place_order_spinner);
        add=findViewById(R.id.place_order_buy_add);
        red=findViewById(R.id.place_order_buy_red);
        buyCount=findViewById(R.id.place_order_buy_count);
        sumPrice=findViewById(R.id.place_order_sum_price);

        TimeSpinnerAdapter timeSpinnerAdapter=new TimeSpinnerAdapter(this);
        spinner.setAdapter(timeSpinnerAdapter);

        add.setOnClickListener(this);
        red.setOnClickListener(this);

    }

    public void getAccountPassword(){
        SharedPreferences pref = getSharedPreferences("login", MODE_PRIVATE);
        mUserName = pref.getString("username", "未获取");
        mPassword=pref.getString("password","未获取");
    }
    public void downloadData(){
        shopName.setText(homeEntity.getShopname());
        userName.setText(mUserName);
        foodName.setText(foodNames);
        foodPrice.setText(foodPrices);
        foodPricez.setText(foodPrices);
        orderTime.setText(getSystemTime());
    }

    public String getSystemTime(){
        SimpleDateFormat   formatter   =   new SimpleDateFormat("yyyy年MM月dd日HH:mm:ss");
        Date curDate =  new Date(System.currentTimeMillis());
        return formatter.format(curDate);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.place_order_buy_add:
                if (count>=0)
                count++;
                break;
            case R.id.place_order_buy_red:
                if (count>=0)
                count--;
                break;
        }
        buyCount.setText(String.valueOf(count));
        int price = Integer.parseInt(foodPrices);
        sumPrice.setText(String.valueOf(price*count)+"￥");

    }
}
