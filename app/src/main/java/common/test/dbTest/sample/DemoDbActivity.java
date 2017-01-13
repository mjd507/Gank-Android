package common.test.dbTest.sample;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.ArrayList;

import common.db.DbManager;
import common.db.TablesManager;
import common.db.dao.DbDao;
import common.test.dbTest.been.Person;
import common.utils.ToastUtils;

/**
 * 描述:
 * Created by mjd on 2017/1/11.
 */

public class DemoDbActivity extends Activity {

    private DbManager dbManager;
    private DbDao dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LinearLayout contentView = new LinearLayout(this);
        contentView.setOrientation(LinearLayout.VERTICAL);

        MyApplication application = (MyApplication) getApplication();
        dbManager = application.getDbManager();

        Button btnCreateDb = new Button(this);
        btnCreateDb.setText("创建数据库");
        btnCreateDb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dao = dbManager.getDao();
                dao.openDatabase(null, true);
            }
        });

        Button btnCreateTable = new Button(this);
        btnCreateTable.setText("创建数据表");
        btnCreateTable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TablesManager tablesManager = TablesManager.getInstance();
                tablesManager.register(Person.class);
                tablesManager.createTables(false, dao);
            }
        });

        Button btnInsert = new Button(this);
        btnInsert.setText("插入数据");
        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = 0;
                boolean success = true;
                for (int i = 0; i < 10; i++) {
                    Person person = new Person();
                    person.setName("张三" + i);
                    person.setAge(20 + i);
                    long result = dao.insert(person);
                    if (result != -1) {
                        count++;
                    } else {
                        success = false;
                    }
                }
                if (success) {
                    ToastUtils.showShort(getApplicationContext(), "插入成功" + "共 " + count + " 条数据");
                } else {
                    ToastUtils.showShort(getApplicationContext(), "插入失败");
                }
            }
        });

        Button btnDelete = new Button(this);
        btnDelete.setText("删除数据");
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean delete = dao.delete(Person.class, "name=?", new String[]{"张三0"});
                if (delete) {
                    ToastUtils.showShort(getApplicationContext(), "删除成功");
                } else {
                    ToastUtils.showShort(getApplicationContext(), "删除失败");
                }
            }
        });

        Button btnUpdate = new Button(this);
        btnUpdate.setText("修改数据");
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Person person = new Person("李四", 18);
                long update = dao.update(Person.class, person, "name = ?", new String[]{"张三1"});
                if (update != -1) {
                    ToastUtils.showShort(getApplicationContext(), "修改成功");
                } else {
                    ToastUtils.showShort(getApplicationContext(), "修改失败");
                }
            }
        });
        Button btnQuery = new Button(this);
        btnQuery.setText("修改数据");
        btnQuery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<Person> personList = dao.query(false, Person.class, null, null, null, null, null, null, null);
                if (personList.size() > 0) {
                    ToastUtils.showShort(getApplicationContext(), "查询成功" + "共 " + personList.size() + " 条数据");
                } else {
                    ToastUtils.showShort(getApplicationContext(), "查询失败");
                }
            }
        });

        contentView.addView(btnCreateDb);
        contentView.addView(btnCreateTable);
        contentView.addView(btnInsert);
        contentView.addView(btnDelete);
        contentView.addView(btnUpdate);

        setContentView(contentView);
    }

}
