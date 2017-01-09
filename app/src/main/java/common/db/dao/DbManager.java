package common.db.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import java.util.ArrayList;

import common.db.DbHelper;
import common.db.TablesRepository;
import common.db.entity.ColumnEntity;
import common.db.entity.TableEntity;
import common.db.exception.TADBException;
import common.db.exception.TADBNotOpenException;
import common.utils.logger.Logger;

/**
 * 描述:
 * Created by mjd on 2017/1/9.
 */

public class DbManager {

    // default config
    private final static String DB_NAME = "common_android.db";
    private final static int DB_VERSION = 1;
    private static final String TAG = DbManager.class.getSimpleName();

    // 当前SQL指令
    private String queryStr = "";
    // 错误信息
    private String error = "";
    // 是否已经连接数据库
    private Boolean isConnect = false;
    // 执行oepn打开数据库时，保存返回的数据库对象
    private SQLiteDatabase mSQLiteDatabase = null;
    private DbHelper mDatabaseHelper = null;
    private DbUpdateListener mTadbUpdateListener;

    public DbManager(Context context) {
        TADBParams params = new TADBParams();
        this.mDatabaseHelper = new DbHelper(context, params.getDbName(), null,
                params.getDbVersion());
    }

    /**
     * 构造函数
     *
     * @param context 上下文
     * @param params  数据参数信息
     */
    public DbManager(Context context, TADBParams params) {
        this.mDatabaseHelper = new DbHelper(context, params.getDbName(), null,
                params.getDbVersion());
    }

    /**
     * 设置升级的的监听器
     *
     * @param dbUpdateListener
     */
    public void setOnDbUpdateListener(DbUpdateListener dbUpdateListener) {
        this.mTadbUpdateListener = dbUpdateListener;
        if (mTadbUpdateListener != null) {
            mDatabaseHelper.setOndbUpdateListener(mTadbUpdateListener);
        }
    }

    /**
     * 打开数据库
     *
     * @param dbUpdateListener 数据库升级回调接口，如果不需要传null即可
     * @param isWrite          true以读写方式打开数据库，一旦数据库的磁盘空间满了，会抛出错误。false以读写方式打开数据库，
     *                         一旦数据库的磁盘空间满了改为只读方式打开
     * @return 数据库操作实例
     */
    public SQLiteDatabase openDatabase(DbUpdateListener dbUpdateListener,
                                       Boolean isWrite) {

        if (isWrite) {
            mSQLiteDatabase = openWritable(mTadbUpdateListener);
        } else {
            mSQLiteDatabase = openReadable(mTadbUpdateListener);
        }
        return mSQLiteDatabase;

    }

    /**
     * 以读写方式打开数据库，一旦数据库的磁盘空间满了，数据库就只能读而不能写，倘若使用的是数据库写入方法，就会出错。
     *
     * @param dbUpdateListener
     * @return
     */
    public SQLiteDatabase openWritable(DbUpdateListener dbUpdateListener) {
        if (dbUpdateListener != null) {
            this.mTadbUpdateListener = dbUpdateListener;
        }
        if (mTadbUpdateListener != null) {
            mDatabaseHelper.setOndbUpdateListener(mTadbUpdateListener);
        }
        try {
            mSQLiteDatabase = mDatabaseHelper.getWritableDatabase();
            isConnect = true;
            // 注销数据库连接配置信息
            // 暂时不写
        } catch (Exception e) {
            isConnect = false;
        }

        return mSQLiteDatabase;
    }

    /**
     * 测试 TASQLiteDatabase是否可用
     *
     * @return
     */
    public Boolean testSQLiteDatabase() {
        if (isConnect) {
            if (mSQLiteDatabase.isOpen()) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * 先以读写方式打开数据库，如果数据库的磁盘空间满了，就会打开失败，当打开失败后会继续尝试以只读方式打开数据库。
     *
     * @param dbUpdateListener
     * @return
     */
    public SQLiteDatabase openReadable(DbUpdateListener dbUpdateListener) {
        if (dbUpdateListener != null) {
            this.mTadbUpdateListener = dbUpdateListener;
        }
        if (mTadbUpdateListener != null) {
            mDatabaseHelper.setOndbUpdateListener(mTadbUpdateListener);
        }
        try {
            mSQLiteDatabase = mDatabaseHelper.getReadableDatabase();
            isConnect = true;
            // 注销数据库连接配置信息
            // 暂时不写
        } catch (Exception e) {
            isConnect = false;
        }

        return mSQLiteDatabase;
    }

    /**
     * 执行查询，主要是SELECT, SHOW 等指令 返回数据集
     *
     * @param sql           select语句(可包含占位符，占位符？)
     * @param selectionArgs select语句中占位符参数的值
     * @return 返回数据集合，没有数据则集合长度为0
     */
    public <T> ArrayList query(String sql,
                               String[] selectionArgs, Class<T> clazz) {
        Cursor queryCursor = mSQLiteDatabase.rawQuery(sql, selectionArgs);
        return getQueryCursorData(queryCursor, clazz);
    }

    /**
     * 执行查询，主要是SELECT, SHOW 等指令 返回数据集
     *
     * @param distinct      限制重复，如过为true则限制,false则不用管
     * @param clazz         数据库表对应的类
     * @param columns       要查询的列。如：new String[]{"city","country"}，填null查询所有列
     * @param selection     含占位符的条件。如：city=? and country=?
     * @param selectionArgs 填充selection占位符的值。如：new String[]{"BeiJing","China"}
     * @param groupBy       groupBy语句
     * @param having        having语句
     * @param orderBy       orderBy语句
     * @param limit         limit语句
     * @return 返回查询的数据，如何没有数据返回空集合
     */
    public <T> ArrayList<T> query(boolean distinct,
                                  Class<T> clazz, String where, String[] columns, String selection,
                                  String[] selectionArgs, String groupBy, String having,
                                  String orderBy, String limit) throws TADBException {
        ArrayList<T> arrayList;//
        if (testSQLiteDatabase()) {
            arrayList = new ArrayList<T>();
            TableEntity entityMetadata = TablesRepository.getInstance()
                    .find(clazz);
            // 获取结果集
            Cursor queryCursor = mSQLiteDatabase.query(distinct,
                    entityMetadata.getTableName(), columns, selection,
                    selectionArgs, groupBy, having, orderBy, limit);
            return getQueryCursorData(queryCursor, clazz);
        }
        throw new TADBException("数据库连接不可用");
    }

    /**
     * INSERT, UPDATE 以及DELETE
     *
     * @param sql      语句
     * @param bindArgs
     */
    public void execute(String sql, String[] bindArgs)
            throws TADBNotOpenException {
        Logger.i(TAG, "准备执行SQL[" + sql + "]语句");
        if (testSQLiteDatabase()) {
            if (!TextUtils.isEmpty(sql)) {
                this.queryStr = sql;
                if (bindArgs != null) {
                    mSQLiteDatabase.execSQL(sql, bindArgs);
                } else {
                    mSQLiteDatabase.execSQL(sql);
                }
                Logger.i(TAG, "执行完毕！");
            }
        } else {
            throw new TADBNotOpenException("数据库未打开！");
        }

    }

    /**
     * 获得所有的查询数据集中的数据
     *
     * @return 返回查询的数据，如何没有数据返回空集合
     */
    public <T> ArrayList<T> getQueryCursorData(Cursor queryCursor, Class<T> clazz) {
        ArrayList<T> arrayList = null;
        arrayList = new ArrayList<T>();
        int columnCount = queryCursor.getColumnCount();
        TableEntity entityMetadata = TablesRepository.getInstance().find(clazz);
        try {
            while (queryCursor.moveToNext()) {
                T t = null;
                t = clazz.newInstance();
                for (int i = 0; i < columnCount; i++) {
                    ColumnEntity fieldMetadata = entityMetadata.getField(queryCursor.getColumnName(i));

                    Object value = null;
                    if (fieldMetadata.getType().equals(String.class)) {
                        value = queryCursor.getString(i);
                    } else if (fieldMetadata.getType().equals(short.class)) {
                        value = queryCursor.getShort(i);
                    } else if (fieldMetadata.getType().equals(long.class)) {
                        value = queryCursor.getLong(i);
                    } else if (fieldMetadata.getType().equals(float.class)) {
                        value = queryCursor.getFloat(i);
                    } else if (fieldMetadata.getType().equals(double.class)) {
                        value = queryCursor.getDouble(i);
                    } else if (fieldMetadata.getType().equals(boolean.class)) {
                        value = queryCursor.getInt(i);
                    } else {
                        value = queryCursor.getInt(i);
                    }
                    fieldMetadata.setValue((ColumnEntity) t, value);
                }
                arrayList.add(t);
            }
        } catch (Exception ex) {
            Logger.e(TAG, ex.getMessage());
        } finally {
            if (queryCursor != null) {
                queryCursor.close();
            }
        }
        return arrayList;
    }

    /**
     * 插入记录
     */
    public long insert(ColumnEntity entity) {
        if (testSQLiteDatabase()) {
            TableEntity entityMetadata = TablesRepository.getInstance()
                    .find(entity.getClass());
            return mSQLiteDatabase.insert(entityMetadata.getTableName(), null,
                    entityMetadata.createContentValues(entity));
        } else {
            Logger.e(TAG, "数据库未打开！");
            return -1;
        }
    }

    /**
     * 删除记录
     *
     * @param tableClazz  被删除的表
     * @param whereClause 设置的WHERE子句时，删除指定的数据 ,如果null会删除所有的行。填入含占位符的条件。如：city=? and
     *                    country=?
     * @param whereArgs   填充whereClause占位符的值。如：new String[]{"BeiJing","China"}
     * @return 返回true执行成功，否则执行失败
     */
    public Boolean delete(Class<?> tableClazz,
                          String whereClause, String[] whereArgs) {
        if (testSQLiteDatabase()) {
            TableEntity entityMetadata = TablesRepository.getInstance()
                    .find(tableClazz);
            return mSQLiteDatabase.delete(entityMetadata.getTableName(),
                    whereClause, whereArgs) > 0;
        } else {
            Logger.e(TAG, "数据库未打开！");
            return false;
        }
    }

    /**
     * 更新记录
     *
     * @param tableClazz  表对应的类
     * @param entity
     * @param whereClause 设置的WHERE子句时，更新指定的数据 ,如果null会更新所有的行。填入含占位符的条件。如：city=? and
     *                    country=?
     * @param whereArgs   填充whereClause占位符的值。如：new String[]{"BeiJing","China"}
     * @return 返回true执行成功，否则执行失败
     */
    public Boolean update(Class<?> tableClazz, ColumnEntity entity,
                          String whereClause, String[] whereArgs) {
        if (testSQLiteDatabase()) {
            TableEntity entityMetadata = TablesRepository.getInstance()
                    .find(tableClazz);
            return mSQLiteDatabase.update(entityMetadata.getTableName(),
                    entityMetadata.createContentValues(entity), whereClause,
                    whereArgs) > 0;
        } else {
            Logger.e(TAG, "数据库未打开！");
            return false;
        }
    }

    /**
     * 关闭数据库
     */
    public void close() {
        mSQLiteDatabase.close();
    }

    /**
     * 数据库配置参数
     */
    public static class TADBParams {
        private String dbName = DB_NAME;
        private int dbVersion = DB_VERSION;

        public TADBParams() {

        }

        public TADBParams(String dbName, int dbVersion) {
            this.dbName = dbName;
            this.dbVersion = dbVersion;
        }

        public String getDbName() {
            return dbName;
        }

        public void setDbName(String dbName) {
            this.dbName = dbName;
        }

        public int getDbVersion() {
            return dbVersion;
        }

        public void setDbVersion(int dbVersion) {
            this.dbVersion = dbVersion;
        }
    }


    public interface DbUpdateListener {
        void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion);
    }


}
