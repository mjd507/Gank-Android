package common.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.cleaner.commonandroid.R;


/**
 * 描述: 普通对话框
 * Created by mjd on 2016/11/24.
 */

public class CommonDialog extends Dialog {

    private TextView mTitleTextView;
    private TextView mMsgTextView;
    private Button mNegativeBtn;
    private Button mPositiveBtn;

    public CommonDialog(Context context) {
        this(context, R.style.CommonDialogStyle);
    }

    public CommonDialog(Context context, int theme) {
        super(context, theme);
        initView(context);
    }

    private void initView(Context context) {
        View rootView = LayoutInflater.from(context).inflate(R.layout.layout_dialog_common, null);
        mTitleTextView = (TextView) rootView.findViewById(R.id.tv_title);
        mMsgTextView = (TextView) rootView.findViewById(R.id.tv_message);
        mNegativeBtn = (Button) rootView.findViewById(R.id.btn_negative);
        mPositiveBtn = (Button) rootView.findViewById(R.id.btn_positive);
        this.setContentView(rootView);
        this.setCancelable(true);
        this.setCanceledOnTouchOutside(false);
    }

    public void setTitleText(String title){
        mTitleTextView.setText(title);
    }

    public void setMsgText(String msg){
        mMsgTextView.setText(msg);
    }

    public void setPositiveBtn(String text, final OnDialogClickListener listener){
        mPositiveBtn.setText(text);
        mPositiveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener != null){
                    listener.onClick(CommonDialog.this,1);
                }
            }
        });
    }

    public void setNegativeBtn(String text, final OnDialogClickListener listener){
        mNegativeBtn.setText(text);
        mNegativeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener != null){
                    listener.onClick(CommonDialog.this,0);
                }
            }
        });
    }

    public interface OnDialogClickListener {
        void onClick(Dialog dialog, int which);
    }

}
