package com.SupperHttp.android.http;

/**
 * Created by 张臣周 on 2016/8/24.
 * json数据请求的callBack返回实际泛型类的Bean
 */
public class JsonCallBack<T> implements HttpCallBack<T>{
    @Override
    public void onNetError() {

    }

    @Override
    public void onOtherError() {

    }

    @Override
    public void onSuccess(T o) {

    }
}
