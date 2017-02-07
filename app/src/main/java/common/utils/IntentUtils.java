package common.utils;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

/**
 * 描述:
 * Created by mjd on 2017/2/7.
 */

public class IntentUtils {

    public static void enterActivity(Context context, Class<?> targetClass) {
        context.startActivity(new Intent(context, targetClass));
    }

    public static void enterActivity(Context context, Class<?> targetClass, Bundle bundle) {
        Intent intent = new Intent(context, targetClass);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

}
