package com.example.a37046.foods.fragment.collectionFragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.example.a37046.foods.Adapter.AllUserCollectionStoreAdapter;
import com.example.a37046.foods.R;
import com.example.a37046.foods.base.BaseFragment;
import com.example.a37046.foods.entity.AllUserCollection;
import com.example.a37046.foods.util.HttpUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class StoresFragment extends BaseFragment{

    RecyclerView recyclerView;
    List<AllUserCollection> collections;
    AllUserCollectionStoreAdapter allUserCollectionStoreAdapter;
    @Override
    protected View initView() {
        View view=View.inflate(mContext, R.layout.fragment_connection_stores,null);
        initViews(view);
        collections=new ArrayList<>();
        LinearLayoutManager layoutManager=new LinearLayoutManager(mContext);
        recyclerView.setLayoutManager(layoutManager);

        allUserCollectionStoreAdapter = new AllUserCollectionStoreAdapter(collections);
        getNetworkData();
        recyclerView.setAdapter(allUserCollectionStoreAdapter);

        return view;
    }

    public void initViews(View v){
        recyclerView=v.findViewById(R.id.all_user_connection_list_adapter);

    }

    public void getNetworkData(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient okHttpClient=new OkHttpClient();
                    Request request=new Request.Builder()
                            .url("http://"+HttpUtil.SERVER+"/getAllUserCollection.do?user_id="+getUserId()+"&flag=0")
                            .build();
                    Response response=okHttpClient.newCall(request).execute();


                    if (response.isSuccessful()){
                        String jsondata=response.body().string();
                        Log.d("json数据",jsondata);
                        final List<AllUserCollection> allUserCollections= JSON.parseArray(jsondata,AllUserCollection.class);

                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                collections.addAll(allUserCollections);

                                allUserCollectionStoreAdapter.notifyDataSetChanged();

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
        SharedPreferences pref = mContext.getSharedPreferences("login", Context.MODE_PRIVATE);
        return pref.getString("login_id","");
    }
}
