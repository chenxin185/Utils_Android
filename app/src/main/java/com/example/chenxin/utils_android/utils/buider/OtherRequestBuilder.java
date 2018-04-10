package com.example.chenxin.utils_android.utils.buider;

import com.example.chenxin.utils_android.utils.request.OtherRequest;
import com.example.chenxin.utils_android.utils.request.RequestCall;

import okhttp3.RequestBody;


/**
 * Created by momo on 2018/4/4.
 */

public class OtherRequestBuilder extends OkhttpRequestBuider<OtherRequestBuilder> {

    private RequestBody mRequestBody;
    private String mContent;
    private String mMethod;

    public OtherRequestBuilder content(String content){
        this.mContent = content;
        return this;
    }

    public OtherRequestBuilder method(String method){
        this.mMethod = method;
        return this;
    }

    public OtherRequestBuilder requestBody(RequestBody requestBody){
        this.mRequestBody = requestBody;
        return this;
    }
    @Override
    public RequestCall build() {
        return new OtherRequest(mRequestBody, mContent, mMethod, url, tag, params, headers, id).build();
    }
}
