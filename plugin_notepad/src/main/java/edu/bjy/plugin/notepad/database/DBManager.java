package edu.bjy.plugin.notepad.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import edu.bjy.plugin.notepad.NotepadBean;

public class DBManager {
    private static final String TAG = NoteConstants.TAG;
    private Context context;
    private static final String DB_NAME = "bjy.db";
    private static final int DB_VERSION = 1;
    private DBHelper dbHelper;

    public DBManager(Context context) {
        this.context = context;
        init();
    }

    private void init(){
        dbHelper = new DBHelper(context, DB_NAME, null, DB_VERSION);
    }

    public void insertTableNote(List<NotepadBean> list){
        if (list == null && list.isEmpty()){
            Log.w(TAG, "insertTableNote list为空");
        }
    }

    public void insertTableNote(NotepadBean notepadBean){
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put("note", notepadBean.getNote());
            contentValues.put("timestamp", notepadBean.getTimestamp());
            dbHelper.insertTableNote(contentValues);
        }catch (Exception e){
            Log.w(TAG, "insertTableNote异常", e);
        }
    }

    public void deleteTableNoteAll(){

    }

    //删除单条数据
    public void deleteTableNote(NotepadBean notepadBean){
        try {
            dbHelper.delete("timestamp=?", new String[]{notepadBean.getTimestamp()});
        } catch (Exception e) {
            Log.w(TAG, "deleteTableNote异常", e);
        }
    }

    public void updateTableNote(NotepadBean notepadBean){
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put("note", notepadBean.getNote());
            contentValues.put("timestamp", notepadBean.getTimestamp());
            dbHelper.updateTableNote(contentValues, "id=?", new String[]{String.valueOf(notepadBean.getId())});
        }catch (Exception e){
            Log.w(TAG, "updateTableNote异常", e);
        }
    }

    public List<NotepadBean> queryTableNote(){
        List<NotepadBean> list = null;
        try {
            Cursor cursor = dbHelper.queryTableNote(null, null);
            if (cursor != null){
                list = new ArrayList<>();
                while (cursor.moveToNext()){
                    NotepadBean notepadBean = new NotepadBean();
                    notepadBean.setId(cursor.getInt(cursor.getColumnIndex("id")));
                    notepadBean.setNote(cursor.getString(cursor.getColumnIndex("note")));
                    notepadBean.setTimestamp(cursor.getString(cursor.getColumnIndex("timestamp")));
                    list.add(notepadBean);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
}
