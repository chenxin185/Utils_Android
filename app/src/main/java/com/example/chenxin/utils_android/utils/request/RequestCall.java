package com.example.chenxin.utils_android.utils.request;

import com.example.chenxin.utils_android.utils.callback.Callback;
import com.example.chenxin.utils_android.utils.utils.OkhttpUtils;

import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by momo on 2018/4/3.
 */

public class RequestCall {

    private OkhttpRequest mOkhttpRequest;
    private Request mRequest;
    private Call mCall;
    private long readTimeOut;
    private long writeTimeOut;
    private long connTimeOut;
    private OkHttpClient mClient;

    public RequestCall(OkhttpRequest okhttpRequest){
        this.mOkhttpRequest = okhttpRequest;
    }

    public RequestCall readTimeOut(long readTimeOut){
        this.readTimeOut = readTimeOut;
        return this;
    }
    public RequestCall writeTimeOut(long writeTimeOut){
        this.writeTimeOut = writeTimeOut;
        return this;
    }
    public RequestCall connTimeOut(long connTimeOut){
        this.connTimeOut = connTimeOut;
        return this;
    }

    public Call buildCall(Callback callback){
        mRequest = generateRequest(callback);
        if (readTimeOut>0||writeTimeOut>0||connTimeOut>0){
            readTimeOut = readTimeOut>0?readTimeOut: OkhttpUtils.DEFAULT_MILLISECONDS;
            writeTimeOut = writeTimeOut>0?writeTimeOut:OkhttpUtils.DEFAULT_MILLISECONDS;
            connTimeOut = connTimeOut>0?connTimeOut:OkhttpUtils.DEFAULT_MILLISECONDS;

            mClient = OkhttpUtils.getInstance().getOkHttpClient().newBuilder()
                    .readTimeout(readTimeOut, TimeUnit.MILLISECONDS)
                    .writeTimeout(writeTimeOut,TimeUnit.MILLISECONDS)
                    .connectTimeout(connTimeOut,TimeUnit.MILLISECONDS)
                    .build();
        }else {
            mCall = OkhttpUtils.getInstance().getOkHttpClient().newCall(mRequest);
        }
        return mCall;
    }

    private Request generateRequest(Callback callback){
        return mOkhttpRequest.generateRequest(callback);
    }

    public OkhttpRequest getOkhttpRequest(){
        return mOkhttpRequest;
    }


    public void execuce(Callback callback){
        buildCall(callback);
        OkhttpUtils.getInstance().execuce(this,callback);
    }
    public Call getCall(){
        return mCall;
    }


}
