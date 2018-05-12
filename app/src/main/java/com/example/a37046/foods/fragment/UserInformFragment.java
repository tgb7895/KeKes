package com.example.a37046.foods.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.example.a37046.foods.R;
import com.example.a37046.foods.activity.AllUserCommentActivity;
import com.example.a37046.foods.activity.AllUserOrderActivity;
import com.example.a37046.foods.activity.LoginActivity;
import com.example.a37046.foods.activity.ModifyPersonalInformation;
import com.example.a37046.foods.base.BaseFragment;
import com.example.a37046.foods.entity.UserById;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.LinkedTransferQueue;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class UserInformFragment extends BaseFragment {

    private static final String Tag=UserInformFragment.class.getSimpleName();//CommonFrameFragment

    LinearLayout user;
    Button order;
    Button comment;
    TextView username;
    TextView phone;

    Button exit;
    @Override
    protected View initView() {

        View view=View.inflate(mContext, R.layout.fragment_userinform,null);

        initViews(view);

        getNetworkData();
        return view;

    }

    public void initViews(View v){
        user=v.findViewById(R.id.fragment_user_inform);
        order=v.findViewById(R.id.fragment_userinform_order);
        comment=v.findViewById(R.id.fragment_userinform_comment);
        username=v.findViewById(R.id.fragment_userinform_user);
        phone=v.findViewById(R.id.fragment_userinform_phone);
        exit=v.findViewById(R.id.fragment_userinform_comment_exit);

        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mContext, AllUserOrderActivity.class);
                startActivity(intent);
            }
        });
        comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mContext,AllUserCommentActivity.class);
                startActivity(intent);
            }
        });

        user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mContext,ModifyPersonalInformation.class);
                startActivity(intent);
            }
        });

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(), LoginActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });
    }
    @Override
    protected void initData() {
        super.initData();
        Log.d(Tag,"页面数据初始化");
    }

    public void getNetworkData(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient okHttpClient=new OkHttpClient();
                    Request request=new Request.Builder()
                            .url("http://60.205.189.39/getUserById.do?user_id="+getUserId())
                            .build();
                    Response response=okHttpClient.newCall(request).execute();
                    if (response.isSuccessful()){
                        //获取个人信息没写完 记得写
                        String jsonData = response.body().string();
                        final UserById userByIds = JSON.parseObject(jsonData, UserById.class);

                        Objects.requireNonNull(getActivity()).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                username.setText(userByIds.getUsername());
                                phone.setText(userByIds.getMobilenum());

                            }
                        });
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


    public String getUserId(){
        SharedPreferences pref=mContext.getSharedPreferences("login", Context.MODE_PRIVATE);
        String userId=pref.getString("login_id","");
        return userId;
    }
}
