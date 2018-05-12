package com.example.a37046.foods.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.example.a37046.foods.R;
import com.example.a37046.foods.entity.Success;
import com.example.a37046.foods.util.HttpUtil;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class RegisterActivity extends AppCompatActivity {

    @BindView(R.id.activity_register_user)
    EditText user;
    @BindView(R.id.activity_register_password)
    EditText password;
    @BindView(R.id.activity_register_two_password)
    EditText twoPassword;
    @BindView(R.id.activity_register_phone)
    EditText phone;
    @BindView(R.id.activity_register_address)
    EditText address;
    @BindView(R.id.activity_register_other)
    EditText other;
    @BindView(R.id.activity_register_submit)
    Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (password.getText().toString().equals(twoPassword.getText().toString()))
                networkRegistration();
            }
        });
    }


    public void networkRegistration(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient okHttpClient=new OkHttpClient();
                    Request request=new Request.Builder()
                            .url("http://60.205.189.39/userRegister.do?username="+user.getText().toString()+"&userpass="+password.getText().toString()+"&mobilenum="+phone.getText().toString()+"&address="+address.getText().toString()+"&comment="+other.getText().toString())
                            .build();
                    Response response=okHttpClient.newCall(request).execute();
                    if (response.isSuccessful()){
                        String jsondata = response.body().toString();
                        Success success = JSON.parseObject(jsondata, Success.class);
                        if (success.getSuccess()==1){
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(RegisterActivity.this,"注册成功",Toast.LENGTH_SHORT).show();
                                    Intent intent=new Intent(RegisterActivity.this,LoginActivity.class);
                                    startActivity(intent);
                                }
                            });
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
