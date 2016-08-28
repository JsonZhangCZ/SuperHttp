package com.SupperHttp.android.http;

import com.google.gson.JsonObject;

/**
 * Created by zhou on 2016/7/4.
 */
public class NetBaseBean {
    private int code;
    private String info;
    private JsonObject data;
    public int getCode() {
        return code;
    }

    public JsonObject getData() {
        return data;
    }

    public String getInfo() {
        return info;
    }
}
