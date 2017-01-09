package common.db.sample;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 描述:
 * Created by mjd on 2017/1/7.
 */

public class PersonDbHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "person";
    public String TABLE_NAME = "person_info";
    public String COLUMN_NAME = "name";
    public String COLUMN_AGE = "age";

    public PersonDbHelper(Context context) {
        super(context, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "("
                + COLUMN_NAME + " varchar(20)"
                + COLUMN_AGE + "int "
                + ")";
        db.execSQL(sql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


}
