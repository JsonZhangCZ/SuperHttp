package com.SupperHttp.android.http;

/**
 * Created by 张臣周 on 2016/8/24.
 */
public class RequestBean {
    private Object tag;
    private String url;
    private boolean isPost;
    private HttpCallBack httpCallBack;
    private String params[];

    public RequestBean(Object tag, String url, boolean isPost, HttpCallBack httpCallBack, String params[]) {
        this.tag = tag;
        this.url = url;
        this.isPost = isPost;
        this.httpCallBack = httpCallBack;
        this.params = params;
    }

    public Object getTag() {
        return this.tag;
    }

    public void setTag(Object tag) {
        this.tag = tag;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isPost() {
        return isPost;
    }

    public void setPost(boolean post) {
        isPost = post;
    }

    public HttpCallBack getHttpCallBack() {
        return httpCallBack;
    }

    public void setHttpCallBack(HttpCallBack httpCallBack) {
        this.httpCallBack = httpCallBack;
    }

    public String[] getParams() {
        return params;
    }

    public void setParams(String[] params) {
        this.params = params;
    }
}
