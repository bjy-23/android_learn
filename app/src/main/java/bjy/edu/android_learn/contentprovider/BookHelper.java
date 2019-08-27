package bjy.edu.android_learn.contentprovider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

public class BookHelper extends SQLiteOpenHelper {
    public static String table_name = "book";
    StringBuilder stringBuilder = new StringBuilder()
            .append("CREATE TABLE IF NOT EXISTS")
            .append(" ")
            .append(table_name)
            .append("(")
            .append("id INTEGER PRIMARY KEY,")
            .append("name TEXT VARCHAR(20) NOT NULL,")
            .append("price TEXT VARCHAR(20) NOT NULL")
            .append(")");

    public static BookHelper getInstance(Context context){
        return new BookHelper(context, "bjy.db3", null, 1);
    }

    public BookHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(stringBuilder.toString());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
