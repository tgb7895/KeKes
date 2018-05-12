package com.example.a37046.foods.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.a37046.foods.Adapter.TimeSpinnerAdapter;
import com.example.a37046.foods.R;
import com.example.a37046.foods.entity.HomeEntity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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
    String foodId;

    HomeEntity homeEntity;

    int count;//购买数量


    Button buy;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_order);
        Intent intent = getIntent();
        homeEntity=(HomeEntity) intent.getSerializableExtra("home_entity_two");
        foodNames = intent.getStringExtra("food_name");
        foodPrices=intent.getStringExtra("food_price");
        foodId=intent.getStringExtra("food_id");

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

        buy=findViewById(R.id.place_order_commit);

        final TimeSpinnerAdapter timeSpinnerAdapter=new TimeSpinnerAdapter(this);
        spinner.setAdapter(timeSpinnerAdapter);

        add.setOnClickListener(this);
        red.setOnClickListener(this);

        final int[] num = new int[1];
        buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int buyCount=count;
              String sumPrices=sumPrice.getText().toString();
                String userId = getUserId();
                String foodIds=foodId;
           final List<String> times = timeSpinnerAdapter.getTimes();

                Intent intent=new Intent(PlaceOrderActivity.this,InsertOrderActivity.class);
                intent.putExtra("count",buyCount);
                intent.putExtra("sum",sumPrices);
                intent.putExtra("user_id",userId);
                intent.putExtra("food_id",foodIds);
             intent.putExtra("time",times.get(num[0]));
                startActivity(intent);

            }
        });
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                num[0] =position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

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

    public String getUserId() {
        SharedPreferences pref = getSharedPreferences("login", Context.MODE_PRIVATE);
        String userId = pref.getString("login_id", "");
        return userId;
    }
}
