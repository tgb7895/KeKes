package com.example.a37046.foods.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.example.a37046.foods.Adapter.CommentDetailAdapter;
import com.example.a37046.foods.R;
import com.example.a37046.foods.entity.CommentDetail;
import com.example.a37046.foods.entity.FoodByShop;
import com.example.a37046.foods.entity.FoodDetail;
import com.example.a37046.foods.entity.HomeEntity;
import com.example.a37046.foods.util.HttpUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MenudetailsActivity extends AppCompatActivity {
    ImageView foodPic;
    TextView foodName;
    TextView foodPrice;
    TextView foodIntro;


    HomeEntity homeEntity;
    Button back;

    Button comment;
    //联系商家
    Button contactTheMerchant;
    //收藏
    Button collect;

    //评论列表
    List<CommentDetail> mCommentDetails;

    RecyclerView recyclerView;
    CommentDetailAdapter commentDetailAdapter;

    Button buy;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menudetails);
        Intent intent=getIntent();
        homeEntity=(HomeEntity)intent.getSerializableExtra("home_Entity_one");


        initializationModule();
        setUpTheListener();
        mCommentDetails=new ArrayList<>();

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        commentDetailAdapter=new CommentDetailAdapter(mCommentDetails);
        recyclerView.setAdapter(commentDetailAdapter);

        getPageData();
        getListData();



    }

    public void initializationModule(){
        foodPic=findViewById(R.id.menu_detail_food_pic);
        foodName=findViewById(R.id.menu_detail_food_name);
        foodPrice=findViewById(R.id.menu_detail_food_price);
        foodIntro=findViewById(R.id.menu_detail_food_intro);
        comment=findViewById(R.id.menu_detail_food_com);
        collect=findViewById(R.id.menu_deatil_collect);
        recyclerView=findViewById(R.id.menu_detail_food_list_com);

        back=findViewById(R.id.menu_detail_back);

        buy=findViewById(R.id.menu_deatil_buy);

    }

    /**
     * 设置监听器
     */
    public void setUpTheListener(){
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(MenudetailsActivity.this,PlaceOrderActivity.class);

                Bundle bundle=new Bundle();
                bundle.putSerializable("home_entity_two",homeEntity);

                intent.putExtras(bundle);
                intent.putExtra("food_name",foodName.getText());
                intent.putExtra("food_price",foodPrice.getText());
                intent.putExtra("food_id",getFoodId());

                startActivity(intent);
            }
        });
        comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                new MaterialDialog.Builder(v.getContext())
                        .title("评价")
                        .inputType(InputType.TYPE_CLASS_TEXT)
                        .input("请输入评价", "", new MaterialDialog.InputCallback() {
                            @Override
                            public void onInput(MaterialDialog dialog, CharSequence input) {
                            }
                        }).show();
            }
        });
    }


    public void loadingPictures(String pic){
        Glide.with(MenudetailsActivity.this).load(pic).into(foodPic);
    }

    public String getFoodId(){
        int foodId=getIntent().getIntExtra("food_id",-1);


        return String.valueOf(foodId);
    }


    public void getPageData(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient okHttpClient=new OkHttpClient();
                    Request request=new Request.Builder()
                            .url("http://" + HttpUtil.SERVER + "/getFoodById.do?food_id="+getFoodId())
                            .build();
                    Response response=okHttpClient.newCall(request).execute();
                    if (response.isSuccessful()){
                        String jsonData=response.body().string();
                        final FoodDetail foodDetail = JSON.parseObject(jsonData, FoodDetail.class);

               //         Log.d("详细信息",foodDetail.getFoodname());

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                loadingPictures(foodDetail.getPic());
                                foodName.setText(foodDetail.getFoodname());
                                foodPrice.setText(String.valueOf(foodDetail.getPrice()));
                                foodIntro.setText(foodDetail.getIntro());
                            }
                        });
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void getListData(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient okHttpClient = new OkHttpClient();
                    Request request=new Request.Builder()
                            .url("http://" + HttpUtil.SERVER +"/getAllUserFoodOrder.do?food_id="+getFoodId())
                            .build();
                    Response response=okHttpClient.newCall(request).execute();
                    if (response.isSuccessful()){
                        String json=response.body().string();
                        List<CommentDetail> commentDetails = JSON.parseArray(json, CommentDetail.class);
                        mCommentDetails.addAll(commentDetails);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                commentDetailAdapter.notifyDataSetChanged();
                            }
                        });
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }
}
