package common.test.dbTest.sample;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import common.db.DbManager;
import common.db.dao.DbDao;

/**
 * 描述:
 * Created by mjd on 2017/1/11.
 */

public class DemoDbActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LinearLayout contentView = new LinearLayout(this);
        contentView.setOrientation(LinearLayout.VERTICAL);

        MyApplication  application = (MyApplication) getApplication();
        DbManager dbManager = application.getDbManager();
        DbDao dao = dbManager.getDao();

        Button btnCreateDb = new Button(this);
        btnCreateDb.setText("创建数据库");
        btnCreateDb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        setContentView(contentView);
    }

    private void createDb(){

    }
}
