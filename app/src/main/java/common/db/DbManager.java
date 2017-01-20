package common.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import common.db.dao.DbDao;

/**
 * 描述:
 * Created by mjd on 2017/1/10.
 */

public class DbManager {

    private final static String DB_NAME = "common_android.db";
    private final static int DB_VERSION = 1;

    private Context context;
    private DbParams params;

    private static DbManager dbManager;

    public static DbManager getInstance() {
        if (dbManager == null) {
            synchronized (DbManager.class) {
                if (dbManager == null) {
                    dbManager = new DbManager();
                }
            }
        }
        return dbManager;
    }

    public void init(Context context, DbParams params) {
        this.context = context;
        if (params == null) {
            params = new DbParams();
            params.dbName = DB_NAME;
            params.dbVersion = DB_VERSION;
        }
        this.params = params;
    }

    private DbDao mDao;

    public DbDao getDao(DbUpdateListener dbUpdateListener) {
        if (mDao == null) {
            mDao = new DbDao(context, params, dbUpdateListener);
        }
        return mDao;
    }

    public static class DbParams {
        public String dbName;
        public int dbVersion;
    }

    public interface DbUpdateListener {
        void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion);
    }


}
