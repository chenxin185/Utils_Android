package com.example.chenxin.utils_android.utils.buider;

import com.example.chenxin.utils_android.utils.request.OtherRequest;
import com.example.chenxin.utils_android.utils.request.RequestCall;
import com.example.chenxin.utils_android.utils.utils.OkhttpUtils;

/**
 * Created by momo on 2018/4/4.
 */

public class HeadBuilder extends GetBuilder {
    @Override
    public RequestCall build() {
        return new OtherRequest(null, null,
                                OkhttpUtils.METHOD.HEAD, url, tag, params, headers, id).build();
    }
}
