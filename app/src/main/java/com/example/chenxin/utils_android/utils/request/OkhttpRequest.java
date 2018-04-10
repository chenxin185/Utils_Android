package com.example.chenxin.utils_android.utils.request;

import com.example.chenxin.utils_android.utils.callback.Callback;
import com.example.chenxin.utils_android.utils.utils.Exceptions;

import java.util.Map;

import okhttp3.Headers;
import okhttp3.Request;
import okhttp3.RequestBody;


/**
 * Created by momo on 2018/4/3.
 */

public abstract class OkhttpRequest {

    public String url;
    public Object tag;
    public Map<String,String> params;
    public Map<String,String> headers;
    private int id;

    private Request.Builder mBuilder = new Request.Builder();

    public OkhttpRequest(String url,Object tag,Map<String,String> params,Map<String,String> headers,
                         int id){
        this.url = url;
        this.tag = tag;
        this.params = params;
        this.headers = headers;
        this.id = id;
        if (url ==null){
            Exceptions.illegalArgument("url can't be null");
        }
        initHeader();
    }

    private void initHeader() {
        mBuilder.url(url).tag(tag);
        appeanHeaders();
    }

    public Request.Builder getBuilder(){
        return mBuilder;
    }

    private void appeanHeaders() {
        Headers.Builder headerBuilder = new Headers.Builder();
        if (headers ==null||headers.isEmpty())
            return;
        for (String key : headers.keySet()){
            headerBuilder.add(key,headers.get(key));
        }
        mBuilder.headers(headerBuilder.build());
    }

    public RequestCall build(){
        return new RequestCall(this);
    }
    public abstract RequestBody buildRequestBody();

    public abstract Request buildRequest(RequestBody requestBody);

    public RequestBody wrappedRequestBody(RequestBody requestBody, Callback callback){
        return requestBody;
    }

    public Request generateRequest(Callback callback){
        RequestBody responseBody = buildRequestBody();
        RequestBody wrappeRequestBody = wrappedRequestBody(responseBody,callback);
        Request request = buildRequest(wrappeRequestBody);
        return request;
    }

    public int getId(){
        return id;
    }


}
