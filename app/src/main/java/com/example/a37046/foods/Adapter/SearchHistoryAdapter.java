package com.example.a37046.foods.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.example.a37046.foods.R;
import com.example.a37046.foods.entity.FoodDetail;
import com.example.a37046.foods.util.HttpUtil;

import java.util.List;


public class SearchHistoryAdapter extends RecyclerView.Adapter<SearchHistoryAdapter.Viewholder> {

    private List<String> history;

    public SearchHistoryAdapter(List<String> history) {
        this.history = history;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext())
                .inflate(android.R.layout.simple_list_item_1,parent,false);

        Viewholder viewholder=new Viewholder(view);
        return viewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        final String s = history.get(position);
        holder.searchText.setText(s);

        holder.text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
//                        try {
//                            OkHttpClient okHttpClient=new OkHttpClient();
//                            Request request=new Request.Builder()
//                                    .url("http://"+ HttpUtil.SERVER+"/getFoodBySearch.do?search="+s)
//                                    .build();
//                            Response response=okHttpClient.newCall(request).execute();
//                            if (response.isSuccessful()){
//                                String json = response.body().string();
//                                List<FoodDetail> foodDetails = JSON.parseArray(json, FoodDetail.class);
//
//                            }
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
                    }
                }).start();
            }
        });
    }

    @Override
    public int getItemCount() {
        return history.size();
    }

    static class Viewholder extends RecyclerView.ViewHolder{
        View text;
        TextView searchText;
        public Viewholder(View itemView) {
            super(itemView);
            text=itemView;
            searchText=itemView.findViewById(android.R.id.text1);
        }
    }
}
