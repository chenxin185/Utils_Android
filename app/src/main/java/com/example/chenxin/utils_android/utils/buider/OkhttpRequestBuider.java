package com.example.chenxin.utils_android.utils.buider;

import java.util.Map;

import wangbin.graduation.com.myapplication.request.RequestCall;

/**
 * Created by momo on 2018/4/3.
 */

public abstract class OkhttpRequestBuider<T> {

    protected String url;
    protected Object tag;
    protected Map<String, String> headers;
    protected Map<String, String> params;
    protected int id;

    public T id(int id){
        this.id = id;
        return (T)this;
    }
    public T url(String url){
        this.url = url;
        return (T)this;
    }
    public T tag(Object tag){
        this.tag = tag;
        return (T)tag;
    }
    public T headers(Map<String,String> headers){
        this.headers = headers;
        return (T)headers;
    }
    public abstract RequestCall build();
}
