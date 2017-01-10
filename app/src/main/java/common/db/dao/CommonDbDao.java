package common.db.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import java.util.ArrayList;

import common.db.DbHelper;
import common.db.TablesManager;
import common.db.entity.ColumnEntity;
import common.db.entity.TableEntity;
import common.utils.logger.Logger;

/**
 * 描述:
 * Created by mjd on 2017/1/9.
 */
public class CommonDbDao {

    // default config
    private final static String DB_NAME = "common_android.db";
    private final static int DB_VERSION = 1;
    private static final String TAG = CommonDbDao.class.getSimpleName();

    private DbHelper dbHelper;
    private SQLiteDatabase db;
    private DbUpdateListener dbUpdateListener;

    public CommonDbDao(Context context) {
        DbParams params = new DbParams();
        this.dbHelper = new DbHelper(context, params.getDbName(), null, params.getDbVersion());
    }

    public CommonDbDao(Context context, DbParams params) {
        this.dbHelper = new DbHelper(context, params.getDbName(), null, params.getDbVersion());
    }

    public void setOnDbUpdateListener(DbUpdateListener dbUpdateListener) {
        this.dbUpdateListener = dbUpdateListener;
        if (this.dbUpdateListener != null) {
            dbHelper.setOnDbUpdateListener(this.dbUpdateListener);
        }
    }

    public SQLiteDatabase openDatabase(DbUpdateListener dbUpdateListener, Boolean isWritableDb) {
        if (dbUpdateListener != null) {
            this.dbUpdateListener = dbUpdateListener;
        }
        if (this.dbUpdateListener != null) {
            dbHelper.setOnDbUpdateListener(this.dbUpdateListener);
        }
        if (isWritableDb) {
            db = dbHelper.getWritableDatabase();
        } else {
            db = dbHelper.getReadableDatabase();
        }
        return db;

    }

    /**
     * INSERT, UPDATE, DELETE
     */
    public void execute(String sql, String[] bindArgs) throws Exception {
        Logger.i(TAG, "准备执行SQL[" + sql + "]语句");
        if (db.isOpen()) {
            if (!TextUtils.isEmpty(sql)) {
                if (bindArgs != null) {
                    db.execSQL(sql, bindArgs);
                } else {
                    db.execSQL(sql);
                }
                Logger.i(TAG, "执行完毕！");
            }
        } else {
            throw new Exception("数据库未打开！");
        }

    }

    public long insert(Class<?> tableClazz) {
        if (db.isOpen()) {
            TableEntity tableEntity = TablesManager.getInstance().find(tableClazz);
            return db.insert(tableEntity.getTableName(), null, createContentValues(tableEntity.getFields()));
        } else {
            Logger.e(TAG, "数据库未打开！");
            return -1;
        }
    }

    private ContentValues createContentValues(ArrayList<ColumnEntity> entities) {

        ContentValues contentValues = new ContentValues();

        for (ColumnEntity entity : entities) {
            if (entity.isPrimaryKey())
                continue;

            Object value = entity.getValue();
            if (value == null)
                continue;
            if (value instanceof String) {
                contentValues.put(entity.getName(), (String) value);
            } else if (value instanceof Short) {
                contentValues.put(entity.getName(), (Short) value);
            } else if (value instanceof Integer) {
                contentValues.put(entity.getName(), (Integer) value);
            } else if (value instanceof Long) {
                contentValues.put(entity.getName(), (Long) value);
            } else if (value instanceof Float) {
                contentValues.put(entity.getName(), (Float) value);
            } else if (value instanceof Double) {
                contentValues.put(entity.getName(), (Double) value);
            } else if (value instanceof Boolean) {
                contentValues.put(entity.getName(), (Boolean) value);
            } else {
                contentValues.put(entity.getName(), value.toString());
            }
        }

        return contentValues;
    }

    public Boolean delete(Class<?> tableClazz, String whereClause, String[] whereArgs) {
        if (db.isOpen()) {
            TableEntity tableEntity = TablesManager.getInstance().find(tableClazz);
            return db.delete(tableEntity.getTableName(), whereClause, whereArgs) > 0;
        } else {
            Logger.e(TAG, "数据库未打开！");
            return false;
        }
    }

    public Boolean update(Class<?> tableClazz, ArrayList<ColumnEntity> entities,
                          String whereClause, String[] whereArgs) {
        if (db.isOpen()) {
            TableEntity tableEntity = TablesManager.getInstance().find(tableClazz);
            return db.update(tableEntity.getTableName(), createContentValues(entities), whereClause,
                    whereArgs) > 0;
        } else {
            Logger.e(TAG, "数据库未打开！");
            return false;
        }
    }

    public <T> ArrayList query(String sql, String[] selectionArgs, Class<T> clazz) {
        Cursor cursor = db.rawQuery(sql, selectionArgs);
        return getQueryCursorData(cursor, clazz);
    }

    public <T> ArrayList<T> query(boolean distinct,
                                  Class<T> tableclazz, String where, String[] columns, String selection,
                                  String[] selectionArgs, String groupBy, String having,
                                  String orderBy, String limit) throws Exception {
        if (db.isOpen()) {
            TableEntity tableEntity = TablesManager.getInstance().find(tableclazz);
            // 获取结果集
            Cursor cursor = db.query(distinct,
                    tableEntity.getTableName(), columns, selection,
                    selectionArgs, groupBy, having, orderBy, limit);
            return getQueryCursorData(cursor, tableclazz);
        } else {
            throw new Exception("数据库连接不可用");
        }

    }

    public <T> ArrayList<T> getQueryCursorData(Cursor cursor, Class<T> tableclazz) {
        ArrayList<T> arrayList = new ArrayList<T>();
        try {
            int columnCount = cursor.getColumnCount();
            TableEntity tableEntity = TablesManager.getInstance().find(tableclazz);
            while (cursor.moveToNext()) {
                T t = tableclazz.newInstance();
                for (int i = 0; i < columnCount; i++) {
                    ColumnEntity columnEntity = tableEntity.getField(cursor.getColumnName(i));

                    Object value = null;
                    if (columnEntity.getType().equals(String.class)) {
                        value = cursor.getString(i);
                    } else if (columnEntity.getType().equals(short.class)) {
                        value = cursor.getShort(i);
                    } else if (columnEntity.getType().equals(long.class)) {
                        value = cursor.getLong(i);
                    } else if (columnEntity.getType().equals(float.class)) {
                        value = cursor.getFloat(i);
                    } else if (columnEntity.getType().equals(double.class)) {
                        value = cursor.getDouble(i);
                    } else if (columnEntity.getType().equals(boolean.class)) {
                        value = cursor.getInt(i);
                    } else {
                        value = cursor.getInt(i);
                    }
                    columnEntity.setValue((ColumnEntity) t, value);
                }
                arrayList.add(t);
            }
        } catch (Exception ex) {
            Logger.e(TAG, ex.getMessage());
        } finally {
            cursor.close();
        }
        return arrayList;
    }

    public void close() {
        db.close();
    }

    public static class DbParams {
        private String dbName = DB_NAME;
        private int dbVersion = DB_VERSION;

        public DbParams() {

        }

        public DbParams(String dbName, int dbVersion) {
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
