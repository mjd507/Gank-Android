package common.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import common.db.dao.DbDao.DbUpdateListener;

/**
 * 描述:
 * Created by mjd on 2017/1/9.
 */

public class DbHelper extends SQLiteOpenHelper {
    private DbUpdateListener mUbUpdateListener;

    public DbHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public DbHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version,
                    DbUpdateListener dbUpdateListener) {
        super(context, name, factory, version);
        this.mUbUpdateListener = dbUpdateListener;
    }

    public void setOnDbUpdateListener(DbUpdateListener dbUpdateListener) {
        this.mUbUpdateListener = dbUpdateListener;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (mUbUpdateListener != null) {
            mUbUpdateListener.onUpgrade(db, oldVersion, newVersion);
        }
    }

}
