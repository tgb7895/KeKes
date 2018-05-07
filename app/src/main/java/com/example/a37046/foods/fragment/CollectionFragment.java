package com.example.a37046.foods.fragment;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.a37046.foods.R;
import com.example.a37046.foods.base.BaseFragment;
import com.example.a37046.foods.fragment.collectionFragments.MenuFragment;
import com.example.a37046.foods.fragment.collectionFragments.StoresFragment;

import java.util.ArrayList;
import java.util.List;

public class CollectionFragment extends BaseFragment {

    private static final String Tag=CollectionFragment.class.getSimpleName();//CommonFrameFragment

    List<BaseFragment> baseFragmentList;
    Fragment fragment;
    ViewPager viewPager;
    Button shop;
    Button menu;
    @Override
    protected View initView() {

        Log.d(Tag,"收藏初始化");
        View view=View.inflate(mContext, R.layout.fragment_collection,null);

        initViews(view);
        initFragment();

        setViewPager();


        return view;

    }

    @Override
    protected void initData() {
        super.initData();
        Log.d(Tag,"页面数据初始化");
    }

    public void initFragment(){
        baseFragmentList=new ArrayList<>();
        baseFragmentList.add(new StoresFragment());
        baseFragmentList.add(new MenuFragment());
    }

    public void initViews(View v){
        viewPager=v.findViewById(R.id.fragment_collection_viewpager);
        shop=v.findViewById(R.id.fragment_collection_shop);
        menu=v.findViewById(R.id.fragment_collection_menu);

        shop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(0);
                shop.setBackgroundResource(R.color.fuchsia);
                menu.setBackgroundResource(R.color.mediumslateblue);
            }
        });
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(1);
                menu.setBackgroundResource(R.color.fuchsia);
                shop.setBackgroundResource(R.color.mediumslateblue);
            }
        });
    }

    public void setViewPager(){
        FragmentManager fragmentManager=getActivity().getSupportFragmentManager();
        viewPager.setAdapter(new FragmentPagerAdapter(fragmentManager) {
            @Override
            public Fragment getItem(int position) {
                BaseFragment baseFragment=baseFragmentList.get(position);
                return baseFragment;
            }
            @Override
            public int getCount() {
                return baseFragmentList.size();
            }

        });
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position==0){
                    shop.setBackgroundResource(R.color.fuchsia);
                    menu.setBackgroundResource(R.color.mediumslateblue);
                }else{
                    menu.setBackgroundResource(R.color.fuchsia);
                    shop.setBackgroundResource(R.color.mediumslateblue);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }
}
