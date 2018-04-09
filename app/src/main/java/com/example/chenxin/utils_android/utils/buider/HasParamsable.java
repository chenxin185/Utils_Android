package com.example.chenxin.utils_android.utils.buider;

import java.util.Map;

/**
 * Created by momo on 2018/4/4.
 */

public interface HasParamsable{
    OkhttpRequestBuider params(Map<String, String> params);
    OkhttpRequestBuider addParams(String key, String val);
}
