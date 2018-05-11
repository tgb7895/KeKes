package com.example.a37046.foods.fragment;

import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.a37046.foods.R;
import com.example.a37046.foods.base.BaseFragment;

public class SearchFragment extends BaseFragment {

    private static final String Tag=SearchFragment.class.getSimpleName();//CommonFrameFragment
    SearchView searchView;
    ListView listView;
    private String[] mStrs = {"aaa", "bbb", "ccc", "airsaid"};
    @Override
    protected View initView() {
        View view=View.inflate(mContext, R.layout.fragment_search,null);

        initViews(view);
        setSearch();

        return view;

    }

    public void initViews(View v){
        searchView=v.findViewById(R.id.fragment_search_view);
        listView=v.findViewById(R.id.fragment_search_list);

    }

    public void setSearch(){
        listView.setAdapter(new ArrayAdapter<String>(mContext, android.R.layout.simple_list_item_1, mStrs));
        listView.setTextFilterEnabled(true);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                Toast.makeText(mContext, query, Toast.LENGTH_SHORT).show();

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (!TextUtils.isEmpty(newText)){
                    listView.setFilterText(newText);
                }else{
                    listView.clearTextFilter();
                }
                return false;
            }
        });
    }
    @Override
    protected void initData() {
        super.initData();
    }

}
