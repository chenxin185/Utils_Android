package com.example.chenxin.utils_android.utils.request;

import android.text.TextUtils;

import com.example.chenxin.utils_android.utils.utils.Exceptions;
import com.example.chenxin.utils_android.utils.utils.OkhttpUtils;

import java.util.Map;

import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.internal.http.HttpMethod;

/**
 * Created by momo on 2018/4/4.
 */

public class OtherRequest extends OkhttpRequest {

    private static MediaType MEDIA_TYPE_PLAIN = MediaType.parse("text/plain;charset=utf-8");
    private RequestBody mRequestBody;
    private String mMethod;
    private String mContent;
    public OtherRequest(RequestBody requestBody,String content,String method,String url, Object tag, Map<String, String> params, Map<String, String> headers, int id) {
        super(url, tag, params, headers, id);
        this.mRequestBody = requestBody;
        this.mMethod =method;
        this.mContent = content;
    }

    @Override
    public RequestBody buildRequestBody() {
        if (mRequestBody ==null&& TextUtils.isEmpty(mContent)&&
                HttpMethod.requiresRequestBody(mMethod)){
            Exceptions.illegalArgument("requestBody and content can not be null in method:" + mMethod);
        }
        if (mRequestBody ==null&&!TextUtils.isEmpty(mContent)){
            mRequestBody = RequestBody.create(MEDIA_TYPE_PLAIN,mContent);
        }
        return mRequestBody;
    }

    @Override
    public Request buildRequest(RequestBody requestBody) {
        if (mMethod.equals(OkhttpUtils.METHOD.PUT)){
            getBuilder().put(requestBody);
        }else if (mMethod.equals(OkhttpUtils.METHOD.DELETE)){
            if (requestBody== null){
                getBuilder().delete();
            }else {
                getBuilder().delete(requestBody);
            }
        }else if (mMethod.equals(OkhttpUtils.METHOD.HEAD)){
            getBuilder().head();
        }else if (mMethod.equals(OkhttpUtils.METHOD.PATCH)){
            getBuilder().patch(requestBody);
        }
        return getBuilder().build();
    }
}
