package com.SupperHttp.android;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.SupperHttp.android.bean.Login;
import com.SupperHttp.android.http.HttpCallBack;
import com.SupperHttp.android.http.HttpClientAgent;
import com.SupperHttp.android.http.JsonCallBack;

import okhttp3.OkHttpClient;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        HttpClientAgent.getInstance().sendRequest(this,"http://api.m-diary.com/api/user/login?",true,new JsonCallBack<Login>(){
            @Override
            public void onNetError() {
                Toast.makeText(MainActivity.this,"网络错误",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onOtherError() {
                Toast.makeText(MainActivity.this,"请求错误",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onSuccess(Login s) {
                Toast.makeText(MainActivity.this,""+s.getData().getNickName(),Toast.LENGTH_LONG).show();
            }
        },"mobile","18210956260","passWord","123456");
    }
}
