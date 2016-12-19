package com.cleaner.commonandroid;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.widget.ListView;

import com.android.common.ui.adapter.CommonAdapter;
import com.android.common.ui.adapter.CommonViewHolder;

import java.util.List;

public class MainActivity extends Activity {

    private ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lv = (ListView) this.findViewById(R.id.listview);

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
