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
    private static OkHttpClient mOkHttp;
    private static Gson mGson;
    public Handler mainHandler = new Handler(Looper.getMainLooper());
    private RequestBean mRequestBean;

    public PostRunnable(RequestBean requestBean) {
        mRequestBean = requestBean;
    }

    @Override
    public void run() {
        Request.Builder builder = new Request.Builder();
        if (mRequestBean.isPost()) {
            builder.url(mRequestBean.getUrl());
            builder.post(getFormBody(new FormBody.Builder(), mRequestBean.getParams()));
        } else {
            builder.url(mRequestBean.getUrl() + getParamsLine(mRequestBean.getParams()));
            builder.get();
        }
        getOKHttpClient().newCall(builder.build()).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                mainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mRequestBean.getHttpCallBack().onNetError();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String str= response.body().string();
                mainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        ParameterizedType type = mRequestBean.getHttpCallBack().getClassT();
                        Type t = type.getActualTypeArguments()[0];
                        System.out.println(str);
//                        Type type = ((ParameterizedType) mRequestBean.getHttpCallBack().getClass().getGenericSuperclass()).getActualTypeArguments()[0];
                        mRequestBean.getHttpCallBack().onSuccess(getGson().fromJson(str,t));
                    }
                });
            }
        });
    }

    /**
     * 获得Post请求的请求body
     *
     * @param builder
     * @param params
     * @return
     */
    private FormBody getFormBody(FormBody.Builder builder, String[] params) {
        if (params.length % 2 != 0) {
            throw new IllegalArgumentException("输入的参数应成对出现");
        }
        for (int i = 0; i < params.length; ) {
            if (i + 1 < params.length) {
                builder.add(params[i], params[i + 1]);
                i += 2;
            }
        }
        return builder.build();
    }

    /**
     * 获得GET请求的参数
     *
     * @param params
     * @return
     */
    public String getParamsLine(String params[]) {//暂时不用，可以提供给GET请求
        String paramStr = "";
        if (params.length % 2 != 0) {
            throw new IllegalArgumentException("请求参数不是成对出现，请检查！");
        }
        for (int i = 0; i < params.length; i++) {
            if (i % 2 == 0) {
                paramStr = paramStr + params[i] + "=";
            } else {
                paramStr = paramStr + params[i] + "&";
            }
        }
        if(TextUtils.isEmpty(paramStr)){
            return "";
        }
        paramStr = paramStr.substring(0, paramStr.length() - 1);
        System.out.println("---->" + paramStr);
        return paramStr;
    }

    private static OkHttpClient getOKHttpClient(){
        if(mOkHttp ==null){
            synchronized (PostRunnable.class){
                if (mOkHttp ==null){
                    mOkHttp = new OkHttpClient();
                }
            }
        }
        return mOkHttp;
    }

    private static Gson getGson(){
        if(mGson ==null){
            synchronized (PostRunnable.class){
                if (mGson ==null){
                    mGson = new Gson();
                }
            }
        }
        return mGson;
    }
}
