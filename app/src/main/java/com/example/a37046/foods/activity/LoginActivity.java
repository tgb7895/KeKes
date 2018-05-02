package com.example.a37046.foods.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.a37046.foods.MainActivity;
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

    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            UserLogin userLogin= (UserLogin) msg.obj;

            if(!(userLogin.getUserid().equals("0"))){
                SharedPreferences.Editor editor=getSharedPreferences("login",MODE_PRIVATE).edit();
                editor.putString("login_id",userLogin.getUserid());
                editor.apply();
                Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }else{
                Toast.makeText(getApplicationContext(),"密码或账户错误",Toast.LENGTH_SHORT).show();
            }
        }
    };
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
        persistentStorage(s,p);
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
                        Message message=new Message();
                        message.obj=userLogin;
                        handler.sendMessage(message);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void persistentStorage(String user,String password){
        SharedPreferences.Editor editor=getSharedPreferences("login",MODE_PRIVATE).edit();
        editor.putString("username",user);
        editor.putString("password",password);
        editor.apply();
    }

//      public void buildProgressDialog(int id) {
//        if (progressDialog == null) {
//            progressDialog = new ProgressDialog();
//            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//        }
//        progressDialog.setMessage(getString(id));
//        progressDialog.setCancelable(true);
//        progressDialog.show();
//    }


}
