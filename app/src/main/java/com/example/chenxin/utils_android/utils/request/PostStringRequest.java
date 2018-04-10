package com.example.chenxin.utils_android.utils.request;

import com.example.chenxin.utils_android.utils.utils.Exceptions;

import java.util.Map;

import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by momo on 2018/4/4.
 */

public class PostStringRequest extends OkhttpRequest {

    private MediaType MEDIA_TYPE_PLAIN = MediaType.parse("text/plain;charset=utf-8");
    private String content;
    private MediaType mMediaType;

    public PostStringRequest(String url, Object tag, Map<String, String> params,
                             Map<String, String> headers,String content,MediaType mediaType, int id) {
        super(url, tag, params, headers, id);
        this.content = content;
        this.mMediaType =mediaType;
        if (this.content == null){
            Exceptions.illegalArgument("the content can not be null !");
        }
        if (this.mMediaType == null){
            this.mMediaType = MEDIA_TYPE_PLAIN;
        }
    }

    @Override
    public RequestBody buildRequestBody() {
        return RequestBody.create(mMediaType,content);
    }

    @Override
    public Request buildRequest(RequestBody responseBody) {
        return getBuilder().post(responseBody).build();
    }
}
