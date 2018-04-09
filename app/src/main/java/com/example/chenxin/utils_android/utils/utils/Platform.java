package com.example.chenxin.utils_android.utils.utils;

import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by momo on 2018/4/4.
 */

public class Platform {
    public static Platform PLATFORM = findPlaform();

    private static Platform findPlaform() {
        try {
            Class.forName("android.os.Build");
            if (Build.VERSION.SDK_INT!=0){

            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return new Platform();
    }

    public static Platform get(){
        return PLATFORM;
    }

    public Executor defultCallbackExecutor(){
        return Executors.newCachedThreadPool();
    }

    public void execute(Runnable runnable){
        defultCallbackExecutor().execute(runnable);
    }

    private static class Android extends  Platform{
        @Override
        public Executor defultCallbackExecutor() {
            return new MainThreadExecutor();
        }

        public static class MainThreadExecutor implements Executor{

            private Handler mHandler = new Handler(Looper.getMainLooper());
            @Override
            public void execute(@NonNull Runnable command) {
                mHandler.post(command);
            }
        }
    }
}
