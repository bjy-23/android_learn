package bjy.edu.android_learn.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;
import android.util.Log;

public class SqlHelper extends SQLiteOpenHelper {
    private static final String TAG = SqlHelper.class.getSimpleName();

    public SqlHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i(TAG, "onCreate" + "   线程：" + Thread.currentThread().getName());
        db.execSQL("CREATE TABLE huoying(id INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR(50), position VARCHAR(50))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
