package com.example.chenxin.utils_android.utils.buider;

import com.example.chenxin.utils_android.utils.request.PostStringRequest;
import com.example.chenxin.utils_android.utils.request.RequestCall;

import okhttp3.MediaType;

/**
 * Created by momo on 2018/4/4.
 */

public class PostStringBuilder extends OkhttpRequestBuider<PostStringBuilder> {
    private String contnet;
    private MediaType mMediaType;
    @Override
    public RequestCall build() {
        return new PostStringRequest(url, tag, params, headers, contnet, mMediaType, id).build();
    }
    public PostStringBuilder content(String content){
        this.contnet = content;
        return this;
    }
    public PostStringBuilder mediaType(MediaType mediaType){
        this.mMediaType = mediaType;
        return this;
    }
}
