package com.android.common.utils;

import java.io.Closeable;
import java.io.IOException;

import static android.content.ContentValues.TAG;

/**
 * 描述:
 * Created by mjd on 2016/12/19.
 */

public class CloseUtils {

    public static void closeIO(Closeable... closeables) {
        if (closeables == null) return;
        for (Closeable closeable : closeables) {
            if (closeable != null) {
                try {
                    closeable.close();
                } catch (IOException e) {
                    LogUtils.d(TAG, e.getMessage());
                }
            }
        }
    }

}
