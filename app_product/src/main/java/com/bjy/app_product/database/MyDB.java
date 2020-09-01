package com.bjy.app_product.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

import com.bjy.app_product.MyApplication;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyDB extends SQLiteOpenHelper {
    private static MyDB myDB;
    private static Context sContext = MyApplication.getInstance();
    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "bjy.db3";
    private static final String TABLE_CONTACTS = "_contacts";

    public static MyDB getInstance(){
        if (myDB == null){
            synchronized (MyDB.class){
                if (myDB == null){
                    myDB = new MyDB(sContext, DB_NAME, null, DB_VERSION);
                }
            }
        }

        return myDB;
    }

    private MyDB(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createTableContacts(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    private void createTableContacts(SQLiteDatabase db){
        String sql = new StringBuilder().append("CREATE TABLE IF NOT EXISTS ")
                .append(TABLE_CONTACTS)
                .append("(id INTEGER PRIMARY KEY AUTOINCREMENT")
                .append(", raw_contact_id INTEGER")
                .append(", contact_name TEXT")
                .append(", phone_number TEXT")
                .append(", phone_id INTEGER")
                .append(")")
                .toString();

        db.execSQL(sql);
    }

    public void insertContacts(List<ContentValues> contentValuesList){
        if (contentValuesList == null)
            return;
        SQLiteDatabase database = getWritableDatabase();
        database.beginTransaction();
        try {
            for (ContentValues contentValues : contentValuesList){
                database.insert(TABLE_CONTACTS, null, contentValues);
            }
            database.setTransactionSuccessful();
        }finally {
            database.endTransaction();
        }
    }

    public void insertContacts(ContentValues contentValues){
        SQLiteDatabase database = getWritableDatabase();
        database.insert(TABLE_CONTACTS, null, contentValues);
    }

    public void deleteContacts(){
        SQLiteDatabase database = getWritableDatabase();
        database.delete(TABLE_CONTACTS, null, null);
    }

    private void updateContacts(){

    }

    public List<Map<String, String>> queryContacts(){
        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor = database.query(TABLE_CONTACTS, null,null, null, null, null, null);
        List<Map<String, String>> list = new ArrayList<>();
        while (cursor.moveToNext()){
            Map<String, String> contacts = new HashMap<>();
            contacts.put("raw_contact_id", cursor.getInt(cursor.getColumnIndex("raw_contact_id"))+"");
            contacts.put("contact_name", cursor.getString(cursor.getColumnIndex("contact_name")));
            contacts.put("phone_number", cursor.getString(cursor.getColumnIndex("phone_number")));
            contacts.put("phone_id", cursor.getInt(cursor.getColumnIndex("phone_id"))+"");

            list.add(contacts);
        }

        return list;
    }

    //判断表中是否有数据
    public boolean hasContactsData(){
        boolean hasData = false;
        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor = null;
        try {
            cursor = database.query(TABLE_CONTACTS, null,null, null, null, null, null);
            if (cursor != null && cursor.getCount() > 0){
                hasData = true;
            }
        }finally {
            if (cursor != null)
                cursor.close();
        }

        return hasData;
    }
}

