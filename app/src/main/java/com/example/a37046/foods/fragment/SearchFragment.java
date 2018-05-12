package com.example.a37046.foods.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.example.a37046.foods.Adapter.SearchHistoryAdapter;
import com.example.a37046.foods.R;
import com.example.a37046.foods.base.BaseFragment;
import com.example.a37046.foods.entity.FoodDetail;
import com.example.a37046.foods.util.HttpUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SearchFragment extends BaseFragment {

    private static final String Tag=SearchFragment.class.getSimpleName();//CommonFrameFragment
    SearchView searchView;
    RecyclerView recyclerView;
    SearchHistoryAdapter searchHistoryAdapter;
    List<FoodDetail> foodDetails;
    @Override
    protected View initView() {
        View view=View.inflate(mContext, R.layout.fragment_search,null);

        initViews(view);
        setSearch();

        foodDetails=new ArrayList<>();
        LinearLayoutManager layoutManager=new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        searchHistoryAdapter=new SearchHistoryAdapter(foodDetails);
        recyclerView.setAdapter(searchHistoryAdapter);

        return view;

    }

    public void initViews(View v){
        searchView=v.findViewById(R.id.fragment_search_view);
        recyclerView=v.findViewById(R.id.fragment_search_list);

    }

    public void setSearch(){
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(final String query) {


                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            OkHttpClient okHttpClient=new OkHttpClient();
                            Request request=new Request.Builder()
                                    .url("http://"+ HttpUtil.SERVER+"/getFoodBySearch.do?search="+query)
                                    .build();
                            Response response=okHttpClient.newCall(request).execute();


                            if (response.isSuccessful()){
                                String json = response.body().string();
                                List<FoodDetail> jsonbody = JSON.parseArray(json, FoodDetail.class);
                                foodDetails.addAll(jsonbody);
                                Objects.requireNonNull(getActivity()).runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        searchHistoryAdapter.notifyDataSetChanged();
                                    }
                                });
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();

                return false;
            }

            @Override
            public boolean onQueryTextChange(final String newText) {

                return false;
            }
        });
    }
    @Override
    protected void initData() {
        super.initData();
    }

}
