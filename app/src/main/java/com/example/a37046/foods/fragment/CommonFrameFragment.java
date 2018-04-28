package com.example.a37046.foods.fragment;

import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;


import com.alibaba.fastjson.JSON;
import com.example.a37046.foods.Adapter.HomebaseAdapter;
import com.example.a37046.foods.R;
import com.example.a37046.foods.base.BaseFragment;
import com.example.a37046.foods.entity.HomeEntity;
import com.example.a37046.foods.util.HttpUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class CommonFrameFragment extends BaseFragment {
    RecyclerView recyclerView;
    List<HomeEntity> mhomeLists;
    HomebaseAdapter homebaseAdapter;

    /**
     *  //CommonFrameFragment
     */
    private static final String Tag=CommonFrameFragment.class.getSimpleName();

    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            List<HomeEntity> homeEntities= (List<HomeEntity>) msg.obj;
            mhomeLists.addAll(homeEntities);
            homebaseAdapter.notifyDataSetChanged();
        }
    };

    @Override
    protected View initView() {

        View view=View.inflate(mContext,R.layout.fragment_home,null);

       mhomeLists= new ArrayList<>();

        recyclerView=view.findViewById(R.id.fragment_home_recycler_view);
        LinearLayoutManager layoutManager=new LinearLayoutManager(mContext);
        recyclerView.setLayoutManager(layoutManager);
        homebaseAdapter = new HomebaseAdapter(mhomeLists);
        recyclerView.setAdapter(homebaseAdapter);

        loadingNetworkData();
        return view;
    }


    @Override
    protected void initData() {
        super.initData();
        Log.d(Tag,"页面数据初始化");

    }

    public void loadingNetworkData(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient okHttpClient = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url("http://" + HttpUtil.SERVER + "/getAllShops.do")
                            .build();
                    Response response = okHttpClient.newCall(request).execute();

                    if (response.isSuccessful()) {
                        String jsonData = response.body().string();
                        List<HomeEntity> homeEntities = JSON.parseArray(jsonData, HomeEntity.class);
                        Message message=new Message();
                        message.obj=homeEntities;
                        handler.sendMessage(message);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
//        thread.start();
//        try {
//            thread.join();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

    }

}
