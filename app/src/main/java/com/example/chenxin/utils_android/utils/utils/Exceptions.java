package com.example.chenxin.utils_android.utils.utils;

import java.io.IOException;

/**
 * Created by momo on 2018/4/3.
 */

public class Exceptions {

    public static void illegalArgument(String msg){
        throw new IllegalArgumentException(String.format(msg));
    }
    public static IOException requestFailExCeption(int code){
        return new IOException("request failed , reponse's code is : " + code);
    }
}
