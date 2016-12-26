package com.cleaner.commonandroid;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.HorizontalScrollView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.common.ui.adapter.CommonAdapter;
import com.android.common.ui.adapter.CommonViewHolder;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

    private ListView lv1;
    private ListView lv2;
    private HorizontalScrollView hsv;
    private int widthPixels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        widthPixels = this.getResources().getDisplayMetrics().widthPixels;
        hsv = (HorizontalScrollView) findViewById(R.id.hsv);
        lv1 = (ListView) this.findViewById(R.id.lv1);
        lv2 = (ListView) this.findViewById(R.id.lv2);

        //让 listView 的宽度为 屏幕的宽度
        lv1.post(new Runnable() {
            @Override
            public void run() {
                List<String> list = new ArrayList<String>();
                for (int i = 0; i < 20; i++) {
                    list.add("Merry Christmas" + " " + i);
                }
                MyAdapter myAdapter = new MyAdapter(MainActivity.this, list);
                lv1.setAdapter(myAdapter);
            }
        });
        lv2.post(new Runnable() {
            @Override
            public void run() {

                List<String> list = new ArrayList<String>();
                for (int i = 0; i < 20; i++) {
                    list.add("Happy New Year " + " " + i);
                }
                MyAdapter myAdapter = new MyAdapter(MainActivity.this, list);
                lv2.setAdapter(myAdapter);
            }
        });

    }

    class MyAdapter<String> extends CommonAdapter<String> {

        public MyAdapter(Context context, List<String> list) {
            super(context, list);
        }

        @Override
        public CommonViewHolder<String> getViewHolder(Context context) {
            return new MyHolder(context);
        }
    }

    class MyHolder<String> extends CommonViewHolder<String> {

        public MyHolder(Context context) {
            super(context);
        }

        @Override
        public View initView(Context context) {
            TextView textView = new TextView(context);
            textView.setLayoutParams(new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT,
                    AbsListView.LayoutParams.WRAP_CONTENT));
            textView.setPadding(20, 20, 20, 20);
            textView.setWidth(widthPixels);
            textView.setGravity(Gravity.CENTER);
            return textView;
        }

        @Override
        public void refreshView(String s) {
            ((TextView) rootView).setText(s.toString());
        }
    }
}
