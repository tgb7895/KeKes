package com.example.a37046.foods.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.example.a37046.foods.R;
import com.example.a37046.foods.entity.Success;
import com.example.a37046.foods.entity.UserById;

import org.json.JSONObject;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ModifyPersonalInformation extends AppCompatActivity {

    @BindView(R.id.activity_modify_personal_information_img)
    ImageView img;
    @BindView(R.id.activity_modify_personal_information_user)
    EditText user;
    @BindView(R.id.activity_modify_personal_information_password)
    EditText password;
    @BindView(R.id.activity_modify_personal_information_phone)
    EditText phone;
    @BindView(R.id.activity_modify_personal_information_address)
    EditText address;
    @BindView(R.id.activity_modify_personal_information_submit)
    Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_personal_information);
        ButterKnife.bind(this);
        getUserInformation();


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modifyPersonalInformation(user.getText().toString(),password.getText().toString()
                ,phone.getText().toString(),address.getText().toString());

            }
        });
    }

    public void getUserInformation(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient okHttpClient=new OkHttpClient();
                    Request request=new Request.Builder()
                            .url("http://60.205.189.39/getUserById.do?user_id="+getUserId())
                            .build();
                    Response response = okHttpClient.newCall(request).execute();
                    if (response.isSuccessful()){
                        String jsonData = response.body().string();
                        final UserById userById = JSON.parseObject(jsonData, UserById.class);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                user.setText(userById.getUsername());
                                phone.setText(userById.getMobilenum());
                                address.setText(userById.getAddress());
                            }
                        });
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void modifyPersonalInformation(final String username, final String password, final String phone, final String address){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient okHttpClient=new OkHttpClient();
                    Request request=new Request.Builder()
                            .url("http://60.205.189.39/updateUserById.do?user_id="+getUserId()+"&username="+username+"&userpass="+password+"&mobilenum="+phone+"&address="+address)
                            .build();
                    Response response = okHttpClient.newCall(request).execute();
                    if (response.isSuccessful()){
                        String jsonData = response.body().string();
                        final Success success = JSON.parseObject(jsonData, Success.class);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (success.getSuccess()==1)
                                Toast.makeText(ModifyPersonalInformation.this,"修改成功",Toast.LENGTH_SHORT).show();
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
        SharedPreferences pref=getSharedPreferences("login",MODE_PRIVATE);
        String id =pref.getString("login_id","");
        return id;
    }
}
