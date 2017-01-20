package common.test;

import android.app.Activity;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import common.utils.HandlerUtils;
import common.utils.ToastUtils;

/**
 * 描述:
 * Created by mjd on 2017/1/5.
 */

public class HandlerUtilsTest extends Activity implements HandlerUtils.OnReceiveMessageListener {

    private HandlerUtils.WrfHandler mHandler;
    private Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHandler = new HandlerUtils.WrfHandler(this);
        LinearLayout contentView = new LinearLayout(this);
        contentView.setOrientation(LinearLayout.VERTICAL);
        btn = new Button(this);
        btn.setText("发送一个延时 3秒 的消息");
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mHandler.sendEmptyMessageDelayed(0, 3000);
            }
        });
        contentView.addView(btn);
        setContentView(contentView);
    }

    @Override
    public void handlerMessage(Message msg) {
        ToastUtils.showShort(getApplicationContext(),"收到一个延时 3 秒的消息");
    }
}
