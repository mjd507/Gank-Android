package common.test.dbTest.sample;

import android.app.Application;

import common.db.DbManager;

/**
 * 描述:
 * Created by mjd on 2017/1/12.
 */

public class MyApplication extends Application {

    public DbManager dbManager;

    @Override
    public void onCreate() {
        super.onCreate();

        //初始化 数据库
        DbManager.DbParams params = new DbManager.DbParams();
        params.setDbName("TuHu.db");
        params.setDbVersion(1);
        dbManager = DbManager.getInstance(this, params);

    }

    public DbManager getDbManager() {
        return dbManager;
    }

}
