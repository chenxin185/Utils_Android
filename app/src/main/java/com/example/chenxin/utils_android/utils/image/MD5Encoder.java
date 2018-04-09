package com.example.chenxin.utils_android.utils.image;

import java.security.MessageDigest;

/**
 * Created by chenxin on 2018/4/8.
 */

public class MD5Encoder {

    /**
     * 将String转成MD5
     *
     * @param string
     * @return
     * @throws Exception
     */
    public static String encode(String string) throws Exception {
        byte[] hash = MessageDigest.getInstance("MD5").digest(
                string.getBytes("UTF-8"));
        StringBuilder hex = new StringBuilder(hash.length * 2);
        for (byte b : hash) {
            if ((b & 0xFF) < 0x10) {
                hex.append("0");
            }
            hex.append(Integer.toHexString(b & 0xFF));
        }
        return hex.toString();
    }

}
