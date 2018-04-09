package com.example.chenxin.utils_android.utils.request;

import java.util.Map;

import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by momo on 2018/4/3.
 */

public class GetRequest extends OkhttpRequest {

    public GetRequest(String url, Object tag, Map<String, String> params, Map<String, String> headers, int id) {
        super(url, tag, params, headers, id);
    }

    @Override
    public RequestBody buildRequestBody() {
        return null;
    }

    @Override
    public Request buildRequest(RequestBody requestBody) {
        return getBuilder().get().build();

    }

}
