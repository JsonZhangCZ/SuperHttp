package com.SupperHttp.android.http;

import android.os.Handler;
import android.os.Looper;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by 张臣周 on 2016/8/24.
 */
public class HttpClientAgent {
    private static HttpClientAgent instance;
    private HttpClientAgent(){
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
    public synchronized void sendRequest(Object tag, String url, boolean isPost,Class<?> classz, HttpCallBack callBack,String... params) {
        RequestBean requestBean = new RequestBean(tag, url, isPost, callBack, params);
        PostRunnable runnable = new PostRunnable<String>(requestBean);
        AsyncHandler.getInstance().excute(AsyncHandler.THREAD_BG_HIGH,runnable);
    }
}
