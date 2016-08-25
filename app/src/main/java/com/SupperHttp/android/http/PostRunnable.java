package com.SupperHttp.android.http;

import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by zhou on 2016/8/24.
 */
public class PostRunnable implements Runnable {
    private static Gson mGson;
    public Handler mainHandler = new Handler(Looper.getMainLooper());
    private RequestBean mRequestBean;
    private Call mCall;
    public PostRunnable(RequestBean requestBean,Call call) {
        mRequestBean = requestBean;
        mCall=call;
    }

    @Override
    public void run() {
        mCall.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                mainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mRequestBean.getHttpCallBack().onNetError();
                        HttpClientAgent.getInstance().removeCallCache(mRequestBean.getTag());
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String str = response.body().string();
                System.out.println(str);
                mainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        ParameterizedType type = mRequestBean.getHttpCallBack().getClassT();
                        Type t = type.getActualTypeArguments()[0];
                        mRequestBean.getHttpCallBack().onSuccess(getGson().fromJson(str,t));
                        HttpClientAgent.getInstance().removeCallCache(mRequestBean.getTag());

//                        mRequestBean.getHttpCallBack().onSuccess(getGson().fromJson(str, mRequestBean.login));
                    }
                });
            }
        });
    }

    private static Gson getGson() {
        if (mGson == null) {
            synchronized (PostRunnable.class) {
                if (mGson == null) {
                    mGson = new Gson();
                }
            }
        }
        return mGson;
    }
}
