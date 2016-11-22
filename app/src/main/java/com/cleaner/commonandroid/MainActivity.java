package com.cleaner.commonandroid;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import universal.utils.ToastUtils;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ToastUtils.showLong(this.getApplicationContext(),"test");
    }
    public void toast(View view){
        ToastUtils.showShort(this.getApplicationContext(), R.string.app_name);
    }
    public void hide(View view){
        ToastUtils.hideToast();
    }
}
