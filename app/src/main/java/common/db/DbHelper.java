package common.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import common.db.dao.DbManager.DbUpdateListener;

/**
 * 描述:
 * Created by mjd on 2017/1/9.
 */

public class DbHelper extends SQLiteOpenHelper {
    private DbUpdateListener dbUpdateListener;

    public DbHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public DbHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version,
                    DbUpdateListener dbUpdateListener) {
        super(context, name, factory, version);
        this.dbUpdateListener = dbUpdateListener;
    }

    public void setOnDbUpdateListener(DbUpdateListener dbUpdateListener) {
        this.dbUpdateListener = dbUpdateListener;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (dbUpdateListener != null) {
            dbUpdateListener.onUpgrade(db, oldVersion, newVersion);
        }
    }

}
