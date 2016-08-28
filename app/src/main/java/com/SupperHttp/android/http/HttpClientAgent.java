package com.SupperHttp.android.http;

import android.text.TextUtils;

import java.lang.reflect.Type;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by 张臣周 on 2016/8/24.
 */
public class HttpClientAgent {
    private static HttpClientAgent instance;
    private static OkHttpClient mOkHttp;
    private HashMap<Object, Call> callMap;
    private HttpClientAgent(){
        callMap = new HashMap<>();
    }
    public static HttpClientAgent getInstance() {
        if (instance == null) {
            synchronized (HttpClientAgent.class) {
                if (instance == null) {
                    instance = new HttpClientAgent();
                }
            }
        }
        return instance;
    }

    /**
     * 发送一个请求
     *
     * @param tag      用户取消请求
     * @param url      请求的url
     * @param isPost   是否是post ，true 则此次请求为post请求
     * @param callBack 请求结果的回调
     * @param params   利用可变参数的形式来进行参数书写   params 成对出现必须长度必须是2的整数倍
     * @return 返回本此请求的requestID  规则是本次请求的时间戳
     */
    public synchronized void sendRequest(Object tag, String url, boolean isPost, HttpCallBack callBack,String... params) {
        RequestBean requestBean = new RequestBean(tag, url, isPost, callBack, params);
        Request.Builder builder = new Request.Builder();
        if (requestBean.isPost()) {
            builder.url(requestBean.getUrl());
            builder.post(getFormBody(new FormBody.Builder(), requestBean.getParams()));
        } else {
            builder.url(requestBean.getUrl() + getParamsLine(requestBean.getParams()));
            builder.get();
        }
        Call call=getOKHttpClient().newCall(builder.build());
        callMap.put(tag,call);
        PostRunnable runnable = new PostRunnable(requestBean,call);
        AsyncHandler.getInstance().excute(AsyncHandler.THREAD_BG_HIGH,runnable);
    }

    public void cancelRequest(Object tag){
        if(callMap.containsKey(tag)){
            synchronized (HttpClientAgent.class){
                if (callMap.containsKey(tag)){
                    callMap.get(tag).cancel();
                }
            }
        }
    }

    public void removeCallCache(Object tag){
        if(callMap.containsKey(tag)){
            synchronized (HttpClientAgent.class){
                if (callMap.containsKey(tag)){
                    callMap.remove(tag);
                }
            }
        }
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
        if (TextUtils.isEmpty(paramStr)) {
            return "";
        }
        paramStr = paramStr.substring(0, paramStr.length() - 1);
        System.out.println("---->" + paramStr);
        return paramStr;
    }

    private static OkHttpClient getOKHttpClient() {
        if (mOkHttp == null) {
            synchronized (PostRunnable.class) {
                if (mOkHttp == null) {
                    mOkHttp = new OkHttpClient();
                }
            }
        }
        return mOkHttp;
    }

}
