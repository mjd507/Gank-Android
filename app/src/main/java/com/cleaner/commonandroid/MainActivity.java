package com.cleaner.commonandroid;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.android.common.adapter.CommonViewHolder;
import com.android.common.adapter.CommonAdapter;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lv = this.findViewById(R.id.listview);

    }

    class MyAdapter<String> extends CommonAdapter<String> {

        public MyAdapter(Context context, List<String> list) {
            super(context, list);
        }

        @Override
        public CommonViewHolder<String> getViewHolder(Context context) {
            return null;
        }
    }
}
