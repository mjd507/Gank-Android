package common.test;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import common.utils.BarUtils;
import common.utils.ToastUtils;

/**
 * 描述:
 * Created by mjd on 2016/12/29.
 */

public class BarUtilsTest extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LinearLayout contentView = new LinearLayout(this);
        contentView.setOrientation(LinearLayout.VERTICAL);
        Button btnBarHeight = new Button(this);
        btnBarHeight.setText("状态栏高度");
        btnBarHeight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int statusBarHeight = BarUtils.getStatusBarHeight(getApplicationContext());
                ToastUtils.showShort(getApplicationContext(), "状态栏高度: " + statusBarHeight);
            }
        });
        contentView.addView(btnBarHeight);
        setContentView(contentView);
    }
}
