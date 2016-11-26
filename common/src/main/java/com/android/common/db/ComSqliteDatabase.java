package com.android.common.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import com.android.common.db.entity.BaseDB;
import com.android.common.db.entity.EntityMetadata;
import com.android.common.db.entity.FieldMetadata;
import com.android.common.db.exception.DBException;
import com.android.common.db.exception.DBNotOpenException;
import com.android.common.utils.logger.Logger;

import java.util.ArrayList;

/**
 * 描述: 数据库管理类，通过此类进行数据库的操作
 * Created by mjd on 2016/11/26.
 */

public class ComSQLiteDatabase {

    // 数据库默认设置
    private final static String DB_NAME = "think_android.db"; // 默认数据库名字
    private final static int DB_VERSION = 1;// 默认数据库版本
    // 当前SQL指令
    private String queryStr = "";
    // 错误信息
    private String error = "";
    // 是否已经连接数据库
    private Boolean isConnect = false;
    // 执行oepn打开数据库时，保存返回的数据库对象
    private SQLiteDatabase mSQLiteDatabase = null;
    private DBHelper mDatabaseHelper = null;
    private DBUpdateListener mDbUpdateListener;
    private String tag = this.getClass().getName();


    public ComSQLiteDatabase(Context context) {
        DBParams params = new DBParams();
        this.mDatabaseHelper = new DBHelper(context, params.getDbName(), null, params.getDbVersion());

    }

    public ComSQLiteDatabase(Context context, DBParams params) {
        this.mDatabaseHelper = new DBHelper(context, params.getDbName(), null, params.getDbVersion(), null);
    }

    public ComSQLiteDatabase(Context context, DBUpdateListener dbUpdateListener) {
        DBParams params = new DBParams();
        this.mDatabaseHelper = new DBHelper(context, params.getDbName(), null, params.getDbVersion(), dbUpdateListener);
    }

    /**
     * 打开数据库
     *
     * @param isWrite true以读写方式打开数据库，一旦数据库的磁盘空间满了，会抛出错误。false以读写方式打开数据库，
     *                一旦数据库的磁盘空间满了改为只读方式打开
     */
    public SQLiteDatabase openDatabase(Boolean isWrite) {

        if (isWrite) {
            mSQLiteDatabase = openWritable();
        } else {
            mSQLiteDatabase = openReadable();
        }
        return mSQLiteDatabase;

    }

    /**
     * 以读写方式打开数据库，一旦数据库的磁盘空间满了，数据库就只能读而不能写，倘若使用的是数据库写入方法，就会出错。
     */
    public SQLiteDatabase openWritable() {
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
     * 先以读写方式打开数据库，如果数据库的磁盘空间满了，就会打开失败，当打开失败后会继续尝试以只读方式打开数据库。
     */
    public SQLiteDatabase openReadable() {
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
     * 测试 SQLiteDatabase是否可用
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
     * 执行查询，主要是SELECT, SHOW 等指令 返回数据集
     *
     * @param sql           select语句(可包含占位符，占位符？)
     * @param selectionArgs select语句中占位符参数的值
     * @return 返回数据集合，没有数据则集合长度为0
     */
    public <T extends BaseDB> ArrayList query(String sql, String[] selectionArgs, Class<T> clazz) {
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
     * @throws DBException 数据库连接不可用,没有连接或者数据库没有打开
     */
    public <T extends BaseDB> ArrayList<T> query(boolean distinct,
                                                 Class<T> clazz, String where, String[] columns, String selection,
                                                 String[] selectionArgs, String groupBy, String having,
                                                 String orderBy, String limit) throws DBException {
        ArrayList<T> arrayList;//
        if (testSQLiteDatabase()) {
            arrayList = new ArrayList<T>();
            EntityMetadata entityMetadata = EntityRepository.getInstance()
                    .find(clazz);
            // 获取结果集
            Cursor queryCursor = mSQLiteDatabase.query(distinct,
                    entityMetadata.getTableName(), columns, selection,
                    selectionArgs, groupBy, having, orderBy, limit);
            return getQueryCursorData(queryCursor, clazz);
        }
        throw new DBException("数据库连接不可用");
    }

    /**
     * INSERT, UPDATE 以及DELETE
     *
     * @param sql      语句
     * @param bindArgs
     * @throws DBNotOpenException
     */
    public void execute(String sql, String[] bindArgs)
            throws DBNotOpenException {
        Logger.i("准备执行SQL[" + sql + "]语句");
        if (testSQLiteDatabase()) {
            if (!TextUtils.isEmpty(sql)) {
                this.queryStr = sql;
                if (bindArgs != null) {
                    mSQLiteDatabase.execSQL(sql, bindArgs);
                } else {
                    mSQLiteDatabase.execSQL(sql);
                }
                Logger.i("执行完毕！");
            }
        } else {
            throw new DBNotOpenException("数据库未打开！");
        }

    }

    /**
     * 获得所有的查询数据集中的数据
     *
     * @return 返回查询的数据，如何没有数据返回空集合
     */
    public <T extends BaseDB> ArrayList<T> getQueryCursorData(
            Cursor queryCursor, Class<T> clazz) {
        ArrayList<T> arrayList = null;
        arrayList = new ArrayList<T>();
        int columnCount = queryCursor.getColumnCount();
        EntityMetadata entityMetadata = EntityRepository.getInstance().find(
                clazz);
        try {
            while (queryCursor.moveToNext()) {
                T t = null;
                t = clazz.newInstance();
                for (int i = 0; i < columnCount; i++) {
                    FieldMetadata fieldMetadata = entityMetadata
                            .getField(queryCursor.getColumnName(i));
                    // if (fieldMetadata != null) {
                    // fieldMetadata.getField().set(t,
                    // queryCursor.get);
                    // }
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
                    fieldMetadata.setValue(t, value);
                }
                arrayList.add(t);
            }
        } catch (Exception ex) {
            Logger.e(ex.getMessage());
        } finally {
            if (queryCursor != null) {
                queryCursor.close();
            }
        }
        return arrayList;
    }

    /**
     * 插入记录
     *
     * @param entity 插入的值
     * @return 返回-1插入失败，成功则返回当前行的主键id
     */
    public long insert(BaseDB entity) {
        if (testSQLiteDatabase()) {
            EntityMetadata entityMetadata = EntityRepository.getInstance()
                    .find(entity.getClass());
            return mSQLiteDatabase.insert(entityMetadata.getTableName(), null,
                    entityMetadata.createContentValues(entity));
        } else {
            Logger.e("数据库未打开！");
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
    public Boolean delete(Class<? extends BaseDB> tableClazz,
                          String whereClause, String[] whereArgs) {
        if (testSQLiteDatabase()) {
            EntityMetadata entityMetadata = EntityRepository.getInstance()
                    .find(tableClazz);
            return mSQLiteDatabase.delete(entityMetadata.getTableName(),
                    whereClause, whereArgs) > 0;
        } else {
            Logger.e("数据库未打开！");
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
    public Boolean update(Class<? extends BaseDB> tableClazz, BaseDB entity,
                          String whereClause, String[] whereArgs) {
        if (testSQLiteDatabase()) {
            EntityMetadata entityMetadata = EntityRepository.getInstance()
                    .find(tableClazz);
            return mSQLiteDatabase.update(entityMetadata.getTableName(),
                    entityMetadata.createContentValues(entity), whereClause,
                    whereArgs) > 0;
        } else {
            Logger.e("数据库未打开！");
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
    public static class DBParams {
        private String dbName = DB_NAME;
        private int dbVersion = DB_VERSION;

        public DBParams() {

        }

        public DBParams(String dbName, int dbVersion) {
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

    /**
     * 数据库升级回调
     */
    public interface DBUpdateListener {
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion);
    }

}



