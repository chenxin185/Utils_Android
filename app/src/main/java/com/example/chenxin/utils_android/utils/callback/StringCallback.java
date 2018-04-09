package com.example.chenxin.utils_android.utils.callback;

import okhttp3.Response;

/**
 * Created by momo on 2018/4/3.
 */

public abstract class StringCallback extends Callback<String> {
    @Override
    public String parseResponseFromNet(Response response, int id) throws Exception {
        return response.body().string();
    }
}
