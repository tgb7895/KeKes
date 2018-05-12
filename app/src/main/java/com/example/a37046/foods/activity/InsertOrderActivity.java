package com.example.a37046.foods.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.a37046.foods.R;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class InsertOrderActivity extends AppCompatActivity {
    int count;
    String time;
    String user_id;
    String sum;
    String food_id;
    @BindView(R.id.activity_insert_order_message)
    TextView Message;
    String substring;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_order);
        ButterKnife.bind(this);
        Intent intent = getIntent();

        count = intent.getIntExtra("count", -1);
        sum = intent.getStringExtra("sum");
        user_id = intent.getStringExtra("user_id");
        food_id = intent.getStringExtra("food_id");
        time = intent.getStringExtra("time");

        substring = sum.substring(0, sum.length() - 1);
        getNetworkData();
    }

    public void getNetworkData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient okHttpClient = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url("http://60.205.189.39/insertOrder.do?user_id=" + user_id + "&food_id=" + food_id + "&num=" + count + "&sum=" + substring + "&suggesttime=" + time)
                            .build();
                    Response response = okHttpClient.newCall(request).execute();
                    if (response.isSuccessful()) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Message.setText("购买成功");
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
