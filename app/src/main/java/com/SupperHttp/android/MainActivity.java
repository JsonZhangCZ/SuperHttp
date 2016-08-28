package com.SupperHttp.android;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.SupperHttp.android.bean.Login;
import com.SupperHttp.android.bean.LoginData;
import com.SupperHttp.android.http.HttpCallBack;
import com.SupperHttp.android.http.HttpClientAgent;
import com.SupperHttp.android.http.JsonCallBack;
import com.SupperHttp.android.http.NetBaseBean;

import okhttp3.OkHttpClient;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        HttpClientAgent.getInstance().sendRequest(this,"http://api.m-diary.com/api/user/login?",true,new JsonCallBack<LoginData>(){
            @Override
            public void onNetError() {
                Toast.makeText(MainActivity.this,"网络错误",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onOtherError(String info) {
                Toast.makeText(MainActivity.this,""+info,Toast.LENGTH_LONG).show();
            }

            @Override
            public void onSuccess(LoginData s) {
                Toast.makeText(MainActivity.this,""+s.getNickName(),Toast.LENGTH_LONG).show();
            }
        },"mobile","18210956260","passWord","123456");
    }
}
