package com.example.chenxin.utils_android.utils.image;

import android.graphics.Bitmap;
import android.widget.ImageView;

/**
 * Created by chenxin on 2018/4/8.
 */

public class ImageUtils {

    private Bitmap bitmap;

    private NetCacheUtils netCacheUtils;
    private LocalCacheUtils localCacheUtils;
    private MemoryCacheUtils memoryCacheUtils;

    private ImageUtils() {
        netCacheUtils = new NetCacheUtils(memoryCacheUtils, localCacheUtils);
        localCacheUtils = new LocalCacheUtils();
        memoryCacheUtils = new MemoryCacheUtils();
    }

    /**
     * 加载图片，将当前URL对应的图片显示到ivPic的控件上
     *
     * @param ivPic
     *            ImageView控件
     * @param url
     *            图片的地址
     */
    public void display(ImageView ivPic, String url, int defaultImage, int errorImage) {
        // 设置默认显示的图片
        ivPic.setImageResource(defaultImage);

        // 1、内存缓存
        bitmap = memoryCacheUtils.getBitmapFromMemory(url);
        if (bitmap != null) {
            ivPic.setImageBitmap(bitmap);
            System.out.println("从内存缓存中加载图片");
            return;
        }
        // 2、本地磁盘缓存
        bitmap = localCacheUtils.getBitmapFromLocal(url);
        if (bitmap != null) {
            ivPic.setImageBitmap(bitmap);
            System.out.println("从本地SD卡加载的图片");
            memoryCacheUtils.setBitmap2Memory(url, bitmap);// 将图片保存到内存
            return;
        }
        // 3、网络缓存
        netCacheUtils.getBitmapFromNet(ivPic, url, errorImage);
        /*
         * 从网络获取图片之后，将图片保存到手机SD卡中，在进行图片展示的时候，优先从SD卡中读取缓存,key是图片的URL的MD5值，
		 * value是保存的图片bitmap
		 */
    }

    public static ImageUtils get() {
        return ImageUtilsHolder.INSTANCE;
    }

    private static class ImageUtilsHolder {
        private static final ImageUtils INSTANCE = new ImageUtils();
    }

}
