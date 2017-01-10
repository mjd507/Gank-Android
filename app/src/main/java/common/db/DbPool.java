package common.db;

import android.content.Context;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Vector;

import common.db.dao.DbDao;
import common.db.dao.DbDao.DbParams;
import common.db.dao.DbDao.DbUpdateListener;
import common.utils.logger.Logger;

/**
 * 描述:
 * Created by mjd on 2017/1/10.
 */

public class DbPool {
    private static final String TAG = DbPool.class.getSimpleName();
    private String testTable = "Sqlite_master"; // 测试连接是否可用的测试表名，默认Sqlite_master为测试表
    private int initialSQLiteDatabase = 2; // 连接池的初始大小
    private int incrementalSQLiteDatabase = 2;// 连接池自动增加的大小
    private int maxSQLiteDatabase = 10; // 连接池最大的大小
    private Vector<PooledSQLiteDatabase> pSQLiteDatabases = null; // 存放连接池中数据库连接的向量
    private Context context;
    private DbParams params;
    private DbUpdateListener mDBUpdateListener; // 升级时监听器
    private Boolean isWrite = false;
    private static HashMap<String, DbPool> poolMap = new HashMap<String, DbPool>();

    public synchronized static DbPool getInstance(Context context, DbParams params, Boolean isWrite) {
        String dbName = params.getDbName().trim();
        DbPool pool = poolMap.get(dbName);
        if (pool == null) {
            pool = new DbPool(context, params, isWrite);
            poolMap.put(dbName.trim(), pool);
        }
        return pool;
    }

    public static DbPool getInstance(Context context) {
        DbParams params = new DbParams();
        return getInstance(context, params, false);
    }

    public static DbPool getInstance(Context context, String dbName, int dbVersion, Boolean isWrite) {
        DbParams params = new DbParams(dbName, dbVersion);
        return getInstance(context, params, isWrite);
    }

    public DbPool(Context context, DbParams params, Boolean isWrite) {
        this.context = context;
        this.params = params;
        this.isWrite = isWrite;
    }

    public void setOnDbUpdateListener(DbUpdateListener dbUpdateListener) {
        this.mDBUpdateListener = dbUpdateListener;
    }

    public int getInitialSQLiteDatabase() {
        return initialSQLiteDatabase;
    }

    public void setInitialSQLiteDatabase(int initialSQLiteDatabase) {
        this.initialSQLiteDatabase = initialSQLiteDatabase;
    }

    public int getIncrementalSQLiteDatabase() {
        return incrementalSQLiteDatabase;
    }

    public void setIncrementalSQLiteDatabase(int incrementalSQLiteDatabase) {
        this.incrementalSQLiteDatabase = incrementalSQLiteDatabase;
    }

    public int getMaxSQLiteDatabase() {
        return maxSQLiteDatabase;
    }

    public void setMaxSQLiteDatabase(int maxSQLiteDatabase) {
        this.maxSQLiteDatabase = maxSQLiteDatabase;
    }

    public void setTestTable(String testTable) {
        this.testTable = testTable;
    }

    public String getTestTable() {
        return this.testTable;
    }

    /**
     * 创建一个数据库连接池，连接池中的可用连接的数量采用类成员 initialSQLiteDatabase 中设置的值
     */
    public synchronized void createPool() {
        if (pSQLiteDatabases != null) {
            return;
        }
        pSQLiteDatabases = new Vector<PooledSQLiteDatabase>();
        createSQLiteDatabase(this.initialSQLiteDatabase);
        Logger.i(TAG, " 数据库连接池创建成功！ ");
    }

    /**
     * 创建由 numSQLiteDatabase 指定数目的数据库连接 , 并把这些连接 放入 numSQLiteDatabase 向量中
     */
    private void createSQLiteDatabase(int numSQLiteDatabase) {

        // 循环创建指定数目的数据库连接
        for (int x = 0; x < numSQLiteDatabase; x++) {
            if (this.maxSQLiteDatabase > 0 && this.pSQLiteDatabases.size() >= this.maxSQLiteDatabase) {
                break;
            }
            try {
                pSQLiteDatabases.addElement(new PooledSQLiteDatabase(newSQLiteDatabase()));
            } catch (Exception e) {
                Logger.i(TAG, " 创建数据库连接失败！ " + e.getMessage());
            }
            Logger.i(TAG, "数据库连接己创建 ......");
        }
    }

    /**
     * 创建一个新的数据库连接并返回它
     */
    private DbDao newSQLiteDatabase() {
        DbDao dao = new DbDao(context, params);
        dao.openDatabase(mDBUpdateListener, isWrite);
        return dao;
    }

    public synchronized DbDao getSQLiteDatabase() {
        if (pSQLiteDatabases == null) {
            return null;
        }

        DbDao sqliteDatabase = getFreeSQLiteDatabase();

        while (sqliteDatabase == null) {
            Logger.i(TAG, "没有可用的数据库连接，等一会儿再试");
            wait(250);
            sqliteDatabase = getFreeSQLiteDatabase();
        }
        return sqliteDatabase;// 返回获得的可用的连接
    }

    private DbDao getFreeSQLiteDatabase() {
        DbDao sqLiteDatabase = findFreeSQLiteDatabase();
        if (sqLiteDatabase == null) {
            createSQLiteDatabase(incrementalSQLiteDatabase);
            sqLiteDatabase = findFreeSQLiteDatabase();
            if (sqLiteDatabase == null) {
                return null;
            }
        }
        return sqLiteDatabase;
    }

    private DbDao findFreeSQLiteDatabase() {
        DbDao dao = null;
        PooledSQLiteDatabase pSQLiteDatabase = null;

        Enumeration<PooledSQLiteDatabase> enumerate = pSQLiteDatabases.elements();

        while (enumerate.hasMoreElements()) {
            pSQLiteDatabase = enumerate.nextElement();
            if (!pSQLiteDatabase.isBusy()) {
                dao = pSQLiteDatabase.getDao();
                pSQLiteDatabase.setBusy(true);
                if (!isOpen(dao)) {
                    dao = newSQLiteDatabase();
                    pSQLiteDatabase.setDao(dao);
                }
                break;
                // 己经找到一个可用的连接，退出
            }
        }
        return dao;
    }

    private boolean isOpen(DbDao dao) {
        return dao != null && dao.isOpen();
    }

    public void releaseSQLiteDatabase(DbDao sqLiteDatabase) {
        if (pSQLiteDatabases == null) {
            Logger.d(TAG, " 连接池不存在，无法返回此连接到连接池中 !");
            return;
        }
        PooledSQLiteDatabase pSqLiteDatabase = null;

        Enumeration<PooledSQLiteDatabase> enumerate = pSQLiteDatabases.elements();

        while (enumerate.hasMoreElements()) {
            pSqLiteDatabase = enumerate.nextElement();

            if (sqLiteDatabase == pSqLiteDatabase.getDao()) {
                pSqLiteDatabase.setBusy(false);
                break;
            }
        }
    }

    public synchronized void refreshSQLiteDatabase() {
        if (pSQLiteDatabases == null) {
            Logger.d(TAG, " 连接池不存在，无法刷新 !");
            return;
        }

        PooledSQLiteDatabase pSqLiteDatabase = null;
        Enumeration<PooledSQLiteDatabase> enumerate = pSQLiteDatabases.elements();
        while (enumerate.hasMoreElements()) {

            pSqLiteDatabase = enumerate.nextElement();

            if (pSqLiteDatabase.isBusy()) {
                wait(5000);
            }

            closeSQLiteDatabase(pSqLiteDatabase.getDao());
            pSqLiteDatabase.setDao(newSQLiteDatabase());
            pSqLiteDatabase.setBusy(false);
        }
    }

    public synchronized void closeAllSQLiteDatabase() {
        if (pSQLiteDatabases == null) {
            Logger.d(TAG, "连接池不存在，无法关闭 !");
            return;
        }
        PooledSQLiteDatabase pSqLiteDatabase = null;
        Enumeration<PooledSQLiteDatabase> enumerate = pSQLiteDatabases.elements();
        while (enumerate.hasMoreElements()) {
            pSqLiteDatabase = enumerate.nextElement();

            if (pSqLiteDatabase.isBusy()) {
                wait(5000);
            }
            closeSQLiteDatabase(pSqLiteDatabase.getDao());
            pSQLiteDatabases.removeElement(pSqLiteDatabase);
        }
        pSQLiteDatabases = null;
    }

    private void closeSQLiteDatabase(DbDao sqlLiteDatabase) {
        sqlLiteDatabase.close();
    }

    private void wait(int mSeconds) {
        try {
            Thread.sleep(mSeconds);
        } catch (InterruptedException e) {
        }
    }

    class PooledSQLiteDatabase {
        DbDao dao = null;
        boolean busy = false;

        public PooledSQLiteDatabase(DbDao dao) {
            this.dao = dao;
        }

        public DbDao getDao() {
            return dao;
        }

        public void setDao(DbDao dao) {
            this.dao = dao;
        }

        public boolean isBusy() {
            return busy;
        }

        public void setBusy(boolean busy) {
            this.busy = busy;
        }
    }
}
