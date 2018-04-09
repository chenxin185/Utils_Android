package com.example.chenxin.utils_android.utils.callback;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by momo on 2018/4/3.
 */

public abstract class Callback<T> {

    public void inProgress(float progress, long total, int id) {

    }

    public abstract T parseResponseFromNet(Response response, int id) throws Exception;

    public abstract void onError(Call call, Exception e, int id);

    public abstract void onResponse(T response, int id);

    public boolean validateResponse(Response response,int id){
        return response.isSuccessful();
    }

    public static Callback CALLBACK_DEFULT = new Callback(){
        @Override
        public Object parseResponseFromNet(Response response, int id) throws Exception {
            return null;
        }


        @Override
        public void onError(Call call, Exception e, int id) {

        }

        @Override
        public void onResponse(Object response, int id) {

        }
    };
}
