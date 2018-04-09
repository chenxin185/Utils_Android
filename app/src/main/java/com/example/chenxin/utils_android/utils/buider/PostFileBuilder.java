package com.example.chenxin.utils_android.utils.buider;

import java.io.File;

import okhttp3.MediaType;
import wangbin.graduation.com.myapplication.request.PostFileRequest;
import wangbin.graduation.com.myapplication.request.RequestCall;

/**
 * Created by momo on 2018/4/4.
 */

public class PostFileBuilder extends OkhttpRequestBuider<PostFileBuilder> {

    private File mFile;
    private MediaType mMediaType;

    @Override
    public RequestCall build() {
        return new PostFileRequest(url,tag,params,headers,mFile,mMediaType,id).build();
    }

    public PostFileBuilder file(File file){
        this.mFile = file;
        return this;
    }
    public PostFileBuilder mediaType(MediaType mediaType){
        this.mMediaType = mediaType;
        return this;
    }
}
