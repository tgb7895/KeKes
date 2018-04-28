package com.example.a37046.foods;

import android.nfc.Tag;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.ashokvarma.bottomnavigation.ShapeBadgeItem;
import com.ashokvarma.bottomnavigation.TextBadgeItem;
import com.example.a37046.foods.base.BaseFragment;
import com.example.a37046.foods.fragment.CommonFrameFragment;
import com.example.a37046.foods.fragment.CustomFragment;
import com.example.a37046.foods.fragment.OtherFragment;
import com.example.a37046.foods.fragment.ThirdPartyFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    BottomNavigationBar bottomNavigationBar;
    private List<BaseFragment> mBaseFragments;
    private BaseFragment mfragment;
    /**
     * 选中的fragment位置
     */
    private int post;

    /**
     * 上次切换的Fragment
     */
    private Fragment mContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //初始化组件
        initView();
        //初始化碎片
        initFragment();
        //初始化底部导航
        titleBarSet();
        //设置监听器底部导航监听器
        setListenner();


    }

    private void setListenner() {
        bottomNavigationBar.setTabSelectedListener(new MyOnCheckedChangeListener());
        //第一次启动需要默认开启一个
        //   new MyOnCheckedChangeListener().switchFragment(mBaseFragments.get(0));

        post=0;
        //根据位置得到fragment
        BaseFragment to = getFragment();
        //替换
        switchFragment(mContent, to);
    }

    /**
     * @param from 刚显示的fragment，马上要被隐藏
     * @param to   马上要切换到的fragment,一会要显示
     */
    private void switchFragment(Fragment from, Fragment to) {

        if (from != to) {
            mContent = to;
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            //才切换
            //判断有没有被添加
            if (!to.isAdded()) {
                //没有被添加
                //from隐藏
                if (from!=null){
                    ft.hide(from);
                }

                //添加to
                if (to!=null){
                    ft.add(R.id.fl_context,to).commit();
                }
            } else {
                //to已经被添加了
                //from隐藏
                if (from!=null){
                    ft.hide(from);
                }
                //显示to
                if (to!=null){
                    ft.show(to).commit();
                }
            }
        }

    }
    /**
     * 根据位置得到fragment
     *
     * @return
     */
    private BaseFragment getFragment() {
        BaseFragment fragment = mBaseFragments.get(post);
        return fragment;
    }
    class MyOnCheckedChangeListener implements BottomNavigationBar.OnTabSelectedListener {

        @Override
        public void onTabSelected(int position) {
            post = position;
            Toast.makeText(MainActivity.this, "我是" + post, Toast.LENGTH_SHORT).show();
//            switch (position){
//                case 0:
//                    post=0;
//                    break;
//                case 1:
//                    post=0;
//                    break;
//                case 2:
//                    break;
//                case 3:
//                    break;
//                case 4:
//                    break;
//            }

            //根据位置得到fragment
            BaseFragment to = getFragment();
            //替换
            switchFragment(mContent,to);
        }


//        private void switchFragment(BaseFragment fragment) {
//            FragmentManager fm=getSupportFragmentManager();
//            FragmentTransaction fragmentTransaction = fm.beginTransaction();
//            fragmentTransaction.replace(R.id.fl_context,fragment);
//            fragmentTransaction.commit();
//        }



        @Override
        public void onTabUnselected(int position) {

        }

        @Override
        public void onTabReselected(int position) {

        }


    }
//    private void setListenner() {
//
//        bottomNavigationBar.setTabSelectedListener(new BottomNavigationBar.OnTabSelectedListener(){
//            @Override
//            public void onTabSelected(int position) {
//
//            }
//            @Override
//            public void onTabUnselected(int position) {
//
//            }
//            @Override
//            public void onTabReselected(int position) {
//                FragmentManager fm=getSupportFragmentManager();
//                FragmentTransaction fragmentTransaction = fm.beginTransaction();
//                fragmentTransaction.replace(R.id.fl_context,mfragment);
//                fragmentTransaction.commit();
//            }
//        });
//
//    }

    private void initFragment() {

        mBaseFragments = new ArrayList<>();
        mBaseFragments.add(new CommonFrameFragment()); //常用框架
        mBaseFragments.add(new ThirdPartyFragment()); //第三方框架
        mBaseFragments.add(new CustomFragment()); //自定义框架
        mBaseFragments.add(new OtherFragment()); //其他框架

    }

    public void initView() {
        bottomNavigationBar = findViewById(R.id.bottom_navigation_bar);
    }

    public void titleBarSet() {
        TextBadgeItem numberBadgeItem = new TextBadgeItem();
        ShapeBadgeItem shapeBadgeItem = new ShapeBadgeItem();
        numberBadgeItem.setText("你好");
        bottomNavigationBar.setMode(BottomNavigationBar.MODE_SHIFTING);
        bottomNavigationBar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_RIPPLE);
        bottomNavigationBar
                .addItem(new BottomNavigationItem(R.drawable.ic_home_white_24dp, "首页"))
                .addItem(new BottomNavigationItem(R.drawable.ic_book_white_24dp, "收藏"))
                .addItem(new BottomNavigationItem(R.drawable.ic_music_note_white_24dp, "搜索"))
                .addItem(new BottomNavigationItem(R.drawable.ic_tv_white_24dp, "我"))
                .initialise();

    }

    public void twoTest() {
        TextBadgeItem numberBadgeItem = new TextBadgeItem();
        ShapeBadgeItem shapeBadgeItem = new ShapeBadgeItem();
        bottomNavigationBar
                .addItem(new BottomNavigationItem(R.drawable.ic_home_white_24dp, "Home").setActiveColorResource(R.color.orange).setBadgeItem(numberBadgeItem));

        bottomNavigationBar
                .addItem(new BottomNavigationItem(R.drawable.ic_home_white_24dp, "Home").setActiveColorResource(R.color.orange).setBadgeItem(shapeBadgeItem));

        numberBadgeItem.setText("你好");// whenever you need to update code
    }


}
