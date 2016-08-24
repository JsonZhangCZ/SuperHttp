package com.SupperHttp.android.http;

import java.lang.reflect.ParameterizedType;

/**
 * Created by zhou on 2016/8/24.
 */
public abstract class HttpCallBack<T> {
    public abstract void onNetError();//网络错误,链接不上或链接超时回调
    public abstract void onOtherError();//其他错误,发现不了正确页面,或者404 ,或者其他网站虽已返回但是不是正常的页面
    public abstract void onSuccess(T t);//正常返回想要的结果
    ParameterizedType getClassT() {
        return (ParameterizedType) getClass().getGenericSuperclass();
    }
}
