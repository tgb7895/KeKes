package com.example.a37046.foods.activity;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.a37046.foods.R;
import com.example.a37046.foods.entity.UserLogin;
import com.example.a37046.foods.util.HttpUtil;
import com.google.gson.Gson;


import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity {
    EditText mloginName;
    EditText mloginPassword;
    Button loginButton;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        setListen();

    }


    public void initView(){
        mloginName=findViewById(R.id.login_name);
        mloginPassword=findViewById(R.id.login_password);
        loginButton=findViewById(R.id.login_button);
    }

    public void setListen(){
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                landingJudgment(mloginName.getText().toString(),mloginPassword.getText().toString());

            }
        });
    }
    public void landingJudgment(String username,String password){
        final String s=username;
        final String p=password;
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient okHttpClient=new OkHttpClient();
                    Request request=new Request.Builder()
                            .url("http://"+ HttpUtil.SERVER+"/userLogin.do?username="+s+"&userpass="+p)
                            .build();
                    Response response=okHttpClient.newCall(request).execute();
                    if (response.isSuccessful()){
                        String jsonLogin = response.body().string();
                        UserLogin userLogin = new Gson().fromJson(jsonLogin, UserLogin.class);
                        Log.d("登陆信息",userLogin.getUserid());


                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


}
