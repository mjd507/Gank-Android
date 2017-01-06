package common.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * 描述: 吐司工具类
 * Created by mjd on 2016/11/21.
 */

public class ToastUtils {

    private static Toast toast;

    public static void showShort(Context context, CharSequence sequence) {
        if (toast == null) {
            toast = Toast.makeText(context.getApplicationContext(), sequence, Toast.LENGTH_SHORT);
        } else {
            toast.setText(sequence);
        }
        toast.show();
    }

    public static void showShort(Context context, int message) {
        showShort(context, context.getString(message));
    }

    public static void showLong(Context context, CharSequence message) {
        if (null == toast) {
            toast = Toast.makeText(context.getApplicationContext(), message, Toast.LENGTH_LONG);
        } else {
            toast.setText(message);
        }
        toast.show();
    }

    public static void showLong(Context context, int message) {
        showLong(context, context.getString(message));
    }

    public static void hideToast() {
        if (toast != null) {
            toast.cancel();
            toast = null;
        }
    }

}
