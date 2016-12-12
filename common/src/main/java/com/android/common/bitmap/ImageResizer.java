package com.android.common.bitmap;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * 描述: 设定图片大小
 * Created by mjd on 2016/12/12.
 */

public class ImageResizer {


    /**
     * 根据指定大小等比例压缩图片
     */
    public Bitmap decodeSampledBitmapFromResource(Resources res, int resId, int reqWidth, int reqHeight) {

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        //不加载图片,只检查图片大小
        BitmapFactory.decodeResource(res, resId, options);

        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        //加载压缩后的图片
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);

    }

    private int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        int inSampleSize = 1;

        if (reqWidth == 0 || reqHeight == 0) {
            return inSampleSize;
        }

        int w = options.outWidth;
        int h = options.outHeight;

        if (w > reqWidth || h > reqHeight) {
            w = w / 2;
            h = h / 2;
            while (w >= reqWidth && h >= reqHeight) {
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }

}
