package com.example.chenxin.utils_android.utils.buider;

import wangbin.graduation.com.myapplication.request.OtherRequest;
import wangbin.graduation.com.myapplication.request.RequestCall;
import wangbin.graduation.com.myapplication.utils.OkhttpUtils;

/**
 * Created by momo on 2018/4/4.
 */

public class HeadBuilder extends GetBuilder {
    @Override
    public RequestCall build() {
        return new OtherRequest(null,null,
                OkhttpUtils.METHOD.HEAD,url,tag,params,headers,id).build();
    }
}
