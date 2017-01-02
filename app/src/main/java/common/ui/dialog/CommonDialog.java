package common.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.cleaner.commonandroid.R;


/**
 * 描述: 普通对话框
 * Created by mjd on 2016/11/24.
 */

public class CommonDialog extends Dialog {

    private TextView mTitle;
    private TextView mMessage;
    private TextView mCancelBtn;
    private TextView mOkBtn;

    public CommonDialog(Context context) {
        this(context, R.style.CommonDialogStyle);
    }

    public CommonDialog(Context context, int theme) {
        super(context, theme);
        initView(context);
    }

    private void initView(Context context) {
        View rootView = LayoutInflater.from(context).inflate(R.layout.layout_dialog, null);
        mTitle = (TextView) rootView.findViewById(R.id.tv_dialog_title);
        mMessage = (TextView) rootView.findViewById(R.id.tv_dialog_message);
        mCancelBtn = (TextView) rootView.findViewById(R.id.dialog_left_btn);
        mOkBtn = (TextView) rootView.findViewById(R.id.dialog_right_btn);
        this.setContentView(rootView);
        this.setCancelable(false);
        this.setCanceledOnTouchOutside(false);
    }

    public void setTitle(String title){
        mTitle.setText(title);
    }

    public void setMsg(String msg){
        mMessage.setText(msg);
    }

    public void setPositiveBtn(String text, final OnDialogClickListener listener){
        mOkBtn.setText(text);
        mOkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener != null){
                    listener.onClick(CommonDialog.this,1);
                }
            }
        });
    }

    public void setNegativeBtn(String text, final OnDialogClickListener listener){
        mCancelBtn.setText(text);
        mCancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener != null){
                    listener.onClick(CommonDialog.this,1);
                }
            }
        });
    }

    interface OnDialogClickListener {
        void onClick(Dialog dialog, int which);
    }

}
