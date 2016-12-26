package common.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.android.common.db.ComSQLiteDatabase.DBUpdateListener;

/**
 * 描述: 管理数据库的创建和版本更新
 * Created by mjd on 2016/11/26.
 */

public class DBHelper extends SQLiteOpenHelper {

    private DBUpdateListener mDbUpdateListener;

    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DBUpdateListener dbUpdateListener) {
        super(context, name, factory, version);
        this.mDbUpdateListener = dbUpdateListener;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (mDbUpdateListener != null) {
            mDbUpdateListener.onUpgrade(db, oldVersion, newVersion);
        }
    }

}
