package com.example.a37046.kekes.fragment;

import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Adapter;
import android.widget.ListView;

import com.example.a37046.kekes.R;
import com.example.a37046.kekes.adapter.CommonFragmentAdapter;
import com.example.a37046.kekes.base.BaseFragment;

public class CommonFrameFragment extends BaseFragment {

    private static final String Tag=CommonFrameFragment.class.getSimpleName();//CommonFrameFragment
    private CommonFragmentAdapter adapter;
    private ListView listView;

    private String[] datas;
    @Override
    protected View initView() {

        Log.d(Tag,"常用框架Fragment初始化");
        View view=View.inflate(mContext,R.layout.fragment_common_frame,null);

        listView=view.findViewById(R.id.listview);

        return view;

    }

    @Override
    protected void initData() {
        super.initData();
        Log.d(Tag,"页面数据初始化");

        datas=new String[]{"OKHttp", "xUtils3","Retrofit2","Fresco","Glide","greenDao","RxJava","volley","Gson","FastJson","picasso","evenBus","jcvideoplayer","pulltorefresh","Expandablelistview","UniversalVideoView","....."};

        adapter=new CommonFragmentAdapter(mContext,datas);

        listView.setAdapter(adapter);

    }
}
