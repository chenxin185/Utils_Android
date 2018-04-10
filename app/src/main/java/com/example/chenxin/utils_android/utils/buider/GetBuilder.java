package com.example.chenxin.utils_android.utils.buider;

import android.net.Uri;

import com.example.chenxin.utils_android.utils.request.GetRequest;
import com.example.chenxin.utils_android.utils.request.RequestCall;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;


/**
 * Created by momo on 2018/4/3.
 */

public class GetBuilder extends OkhttpRequestBuider<GetBuilder> implements HasParamsable,PathParams {

    @Override
    public RequestCall build() {
        if (params!=null){
            url = appeandParams(url,params);
        }
        return new GetRequest(url, tag, params, headers, id).build();
    }

    private String appeandParams(String url, Map<String, String> params) {

        if (url ==null||params ==null||params.isEmpty()) {
            return url;
        }
        Uri.Builder builder =Uri.parse(url).buildUpon();
        Set<String> keys = params.keySet();
        Iterator<String> iterator = keys.iterator();
        while (iterator.hasNext()){
            String key = iterator.next();
            builder.appendQueryParameter(key,params.get(key));
        }
        return builder.build().toString();
    }

    @Override
    public GetBuilder params(Map<String, String> params) {
        this.params = params;
        return this;
    }

    @Override
    public GetBuilder addParams(String key, String val) {
        if (this.params==null){
            params = new LinkedHashMap<>();
        }
        params.put(key,val);
        return this;
    }

    @Override
    public GetBuilder addpath(String path) {
        if (path!=null&&url!=null)
        this.url = url+"/"+path;
        return this;
    }
}
