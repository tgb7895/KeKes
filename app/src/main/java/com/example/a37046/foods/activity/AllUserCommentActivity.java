package com.example.a37046.foods.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.example.a37046.foods.Adapter.AllUserCommentAdapter;
import com.example.a37046.foods.R;
import com.example.a37046.foods.entity.AllUserOrder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class AllUserCommentActivity extends AppCompatActivity {
    List<AllUserOrder> allUsercomments;

    @BindView(R.id.activity_all_user_comment_recycler)
    RecyclerView recyclerView;
    AllUserCommentAdapter allUserCommentAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_user_comment);
        ButterKnife.bind(this);
        allUsercomments=new ArrayList<>();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        allUserCommentAdapter=new AllUserCommentAdapter(allUsercomments);

        recyclerView.setAdapter(allUserCommentAdapter);

        getNetworkData();

    }


    public void getNetworkData(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient okHttpClient=new OkHttpClient();
                    Request request=new Request.Builder()
                            .url("http://60.205.189.39/getAllUserComment.do?user_id="+getUserId())
                            .build();
                    Response response = okHttpClient.newCall(request).execute();

                    if (response.isSuccessful()){
                        String jsonData = response.body().string();
                        List<AllUserOrder> jsonClass = JSON.parseArray(jsonData, AllUserOrder.class);
                        allUsercomments.addAll(jsonClass);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                allUserCommentAdapter.notifyDataSetChanged();
                            }
                        });
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }
    public String getUserId() {
        SharedPreferences pref = getSharedPreferences("login", Context.MODE_PRIVATE);
        String userId = pref.getString("login_id", "");
        return userId;
    }
}
