package com.example.a37046.foods.Adapter;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import java.io.PipedOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TimeSpinnerAdapter implements SpinnerAdapter {
    List<String> times;
    Context mContext;

    public TimeSpinnerAdapter(Context con) {

        times=new ArrayList<>();

        String cou = null;
        /**
         * 计算时间
         */
        for (int i=1,m=0;i<=6;i++){

            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.MINUTE, +m);
            SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss", Locale.CHINA);
            String format = formatter.format(cal.getTime());

            if (i%2==0){
                times.add(cou+"-----"+format);
            }
            m+=30;
            cou=format;
        }
        mContext=con;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        TextView textView = new TextView(mContext);
        textView.setTextSize(30);
        textView.setText(times.get(position));
        return textView;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public int getCount() {
        return times.size();
    }

    @Override
    public Object getItem(int position) {
        return times.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView textView = new TextView(mContext);
        textView.setTextSize(30);
        textView.setText(times.get(position));
        return textView;
    }

    @Override
    public int getItemViewType(int position) {
        return 1;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }
}
