package common.test;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import common.ui.adapter.CommonAdapter;
import common.ui.adapter.CommonViewHolder;
import common.utils.ScreenUtils;

public class CommonAdapterTest extends Activity {

     ListView lv1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        lv1 = new ListView(this);
        List<String> list = new ArrayList<String>();
        for (int i = 0; i < 20; i++) {
            list.add("Merry Christmas" + " " + i);
        }
        MyAdapter myAdapter = new MyAdapter(this, list);
        lv1.setAdapter(myAdapter);

        setContentView(lv1);


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
            textView.setWidth(ScreenUtils.getScreenSize(context)[0]);
            textView.setGravity(Gravity.CENTER);
            return textView;
        }

        @Override
        public void refreshView(String s) {
            ((TextView) rootView).setText(s.toString());
        }
    }
}
