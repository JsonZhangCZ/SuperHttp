package com.SupperHttp.android;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.SupperHttp.android.http.HttpCallBack;
import com.SupperHttp.android.http.HttpClientAgent;
import com.SupperHttp.android.http.JsonCallBack;

import okhttp3.OkHttpClient;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        HttpClientAgent.getInstance().sendRequest(this,"http://www.jianshu.com/p/e740196225a4",false,String.class,new HttpCallBack<String>(){

            @Override
            public void onNetError() {
                Toast.makeText(MainActivity.this,"网络错误",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onOtherError() {
                Toast.makeText(MainActivity.this,"请求错误",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onSuccess(String s) {
                Toast.makeText(MainActivity.this,"请求成功 String="+s,Toast.LENGTH_LONG).show();
            }
        });
    }
}
