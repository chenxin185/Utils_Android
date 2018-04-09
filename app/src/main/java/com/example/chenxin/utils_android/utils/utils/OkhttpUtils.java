package com.example.chenxin.utils_android.utils.utils;


import java.io.IOException;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import wangbin.graduation.com.myapplication.buider.GetBuilder;
import wangbin.graduation.com.myapplication.callback.Callback;
import wangbin.graduation.com.myapplication.request.RequestCall;

/**
 * Created by momo on 2018/4/3.
 */

public class OkhttpUtils {
    public static final long DEFAULT_MILLISECONDS = 10_000L;
    private static OkhttpUtils mInstance;
    private OkHttpClient mOkHttpClient;
    private OkhttpUtils(){
        mOkHttpClient = new OkHttpClient();
    }
    public static OkhttpUtils getInstance(){
        if (mInstance == null){
            synchronized (OkhttpUtils.class){
                if (mInstance == null){
                    mInstance = new OkhttpUtils();
                }
            }
        }
        return mInstance;
    }

    public OkHttpClient getOkHttpClient(){
        return mOkHttpClient;
    }

    public void execuce(RequestCall requestCall, Callback callback){
        if (callback == null)
            callback = Callback.CALLBACK_DEFULT;
        final Callback finalCallback = callback;
        final int id = requestCall.getOkhttpRequest().getId();

        requestCall.getCall().enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                sendFailResultCallback(call,e,finalCallback,id);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    if (!finalCallback.validateResponse(response,id)){
                        sendFailResultCallback(call,Exceptions.requestFailExCeption(response.code()),finalCallback,id);
                        return;
                    }
                    Object o =finalCallback.parseResponseFromNet(response,id);
                    sendSuccessResultCallback(o,finalCallback,id);
                } catch (Exception e) {
                    sendFailResultCallback(call,e,finalCallback,id);
                }finally {
                    if (response.body()!=null)
                        response.body().close();
                }
            }
        });

    }
    private void sendFailResultCallback(Call call,Exception e,Callback callback,int id){
        if (callback ==null)
            return;

    }
    public void sendSuccessResultCallback(final Object object, final Callback callback, final int id) throws Exception {
        if (callback == null)
            return;
                try {
                    callback.onResponse(object,id);
                } catch (Exception e) {
                    e.printStackTrace();
                }
    }

    public static GetBuilder get(){
        return new GetBuilder();
    }
    public void cancelCallFromTag(Object tag){
        for (Call call:mOkHttpClient.dispatcher().queuedCalls()){
            if (tag.equals(call.request().tag())){
                call.cancel();
            }
        }
        for (Call call:mOkHttpClient.dispatcher().runningCalls()){
            if (tag.equals(call.request().tag())){
                call.cancel();
            }
        }
    }

    public static class METHOD
    {
        public static final String HEAD = "HEAD";
        public static final String DELETE = "DELETE";
        public static final String PUT = "PUT";
        public static final String PATCH = "PATCH";
    }



}
