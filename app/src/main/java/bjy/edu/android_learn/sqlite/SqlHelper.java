package bjy.edu.android_learn.sqlite;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;
import android.util.Log;

import bjy.edu.android_learn.App;

public class SqlHelper extends SQLiteOpenHelper {
    private static final String TAG = SqlHelper.class.getSimpleName();
    private static SqlHelper sqlHelper;
    private static final String DB_NAME = "bjy.db3";
    private static final int DB_VERSION = 1;

    private SqlHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public static SqlHelper getInstance(){
        if (sqlHelper == null){
            synchronized (SqlHelper.class){
                if (sqlHelper == null){
                    sqlHelper = new SqlHelper(App.getInstance(), DB_NAME, null, DB_VERSION);
                }
            }
        }
        return sqlHelper;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i(TAG, "onCreate" + "   线程：" + Thread.currentThread().getName());
        db.execSQL("CREATE TABLE huoying(id INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR(50), position VARCHAR(50))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.i(TAG, "onUpgrade");

        //删除表
//        db.execSQL("drop table huoying");

        //增加字段
//        db.execSQL("alter table huoying add power INTEGER default 100");

        //修改表名
//        db.execSQL("alter table huoying rename to muye");
    }
}
