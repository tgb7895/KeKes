package com.example.a37046.kekes.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class CommonFragmentAdapter extends BaseAdapter {


    private final Context Context;
    private final String[] datas;



    public CommonFragmentAdapter(Context context, String[] datas){
        this.Context=context;
        this.datas=datas;
    }
    @Override
    public int getCount() {
        return datas.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView textView=new TextView(Context);
        textView.setPadding(20,20,20,20);
        textView.setTextColor(Color.BLACK);
        textView.setTextSize(20);
        textView.setText(datas[position]);
        return textView;
    }
}
