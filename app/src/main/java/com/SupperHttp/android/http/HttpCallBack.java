package com.SupperHttp.android.http;

/**
 * Created by 张臣周 on 2016/8/24.
 */
public interface HttpCallBack<T> {
    void onNetError();//网络错误,链接不上或链接超时回调
    void onOtherError();//其他错误,发现不了正确页面,或者404 ,或者其他网站虽已返回但是不是正常的页面
    void onSuccess(T t);//正常返回想要的结果
}
