package edu.bjy.plugin.notepad.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

import java.util.List;

public class DBHelper extends SQLiteOpenHelper {
    private String TABLE_NOTE = "table_notepad";
    public DBHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createTableNote(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    private void createTableNote(SQLiteDatabase db){
        String sql = "create table " + TABLE_NOTE
                + "("
                + "id integer primary key autoincrement"
                + ", note text"
                + ", timestamp text"
                + ")";

        db.execSQL(sql);
    }

    public void insertTableNote(List<ContentValues> list) throws Exception{

    }

    public void insertTableNote(ContentValues contentValues) throws Exception{
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        sqLiteDatabase.insert(TABLE_NOTE, null, contentValues);
    }

    public void delete(String whereClause, String[] whereArgs) throws Exception{
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        sqLiteDatabase.delete(TABLE_NOTE, whereClause, whereArgs);
    }

    public void updateTableNote(ContentValues contentValues, String whereClause, String[] whereArgs) throws Exception{
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        sqLiteDatabase.update(TABLE_NOTE, contentValues, whereClause, whereArgs);
    }

    public Cursor queryTableNote(String selection, String[] selectionArgs) throws Exception{
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        return sqLiteDatabase.query(TABLE_NOTE, null, selection, selectionArgs, null, null, null);
    }

}
