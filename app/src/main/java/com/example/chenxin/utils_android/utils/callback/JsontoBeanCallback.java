package com.example.chenxin.utils_android.utils.callback;

import com.google.gson.Gson;

import java.lang.reflect.ParameterizedType;

import okhttp3.Response;

/**
 * Created by momo on 2018/4/4.
 */

public abstract class JsontoBeanCallback<T> extends Callback<T> {

    @Override
    public T parseResponseFromNet(Response response, int id) throws Exception {
        String json = response.body().string();
        Class<T> entityClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        if (entityClass == String.class){
            return (T)json;
        }
        Gson gson = new Gson();
        T entity = gson.fromJson(json,entityClass);
        return entity;
    }
}
