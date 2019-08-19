package bjy.edu.android_learn.sqlite;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import bjy.edu.android_learn.R;

public class SqliteActivity extends AppCompatActivity {

    SqlHelper sqlHelper;
    TextView tv_query;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sqlite);

        tv_query = findViewById(R.id.tv_query);

        sqlHelper = new SqlHelper(this, "bjy.db3", null, 1);

        Button btn_insert = findViewById(R.id.btn_insert);
        btn_insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = sqlHelper.getWritableDatabase();

                //SQL语句
                db.execSQL("insert into huoying(name, position) values ('千手柱间', '初代火影')");

                //原生API
//                ContentValues v1 = new ContentValues();
//                v1.put("name", "千手柱间");
//                v1.put("position", "初代火影");
//                db.insert("huoying", null, v1);
            }
        });

        Button btn_query = findViewById(R.id.btn_query);
        btn_query.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = sqlHelper.getReadableDatabase();
                String name = "";

                //SQL语句
                Cursor cursor = db.rawQuery("select * from huoying where position = ?", new String[]{"初代火影"});
                if (cursor.moveToFirst()) {
                    Log.i("ColumnIndex", cursor.getColumnIndex("name") + "");
                    name = cursor.getString(cursor.getColumnIndex("name"));
                }

                //原生API
//                Cursor cursor = db.query("huoying", new String[]{"name"}, null, null, null, null, null);
//                while (cursor.moveToNext()) {
//                    name = cursor.getString(cursor.getColumnIndex("name"));
//                }

                tv_query.setText(name);
            }
        });

        Button btn_delete = findViewById(R.id.btn_delete);
        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = sqlHelper.getWritableDatabase();

                //SQL语句
                db.execSQL("delete from huoying where position = '初代火影'");

                //原生API
//                db.delete("huoying", "position = ?", new String[]{"初代火影"});
            }
        });

        Button btn_insert_more = findViewById(R.id.btn_insert_more);
        btn_insert_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //插入10000条数据
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        int num = 10000;
                        SQLiteDatabase db = sqlHelper.getWritableDatabase();

//                        System.out.println("开始");
//                        long l1 = System.currentTimeMillis();
//                        for (; num > 0; num--) {
//                            ContentValues v1 = new ContentValues();
//                            v1.put("name", "旋涡鸣人"+num);
//                            v1.put("position", "七代火影"+num);
//                            db.insert("huoying", null, v1);
//                        }
//                        System.out.println("结束");
//                        System.out.println("用时"+ (System.currentTimeMillis()-l1) + "毫秒");

                        db.beginTransaction();
                        System.out.println("开始");
                        long l2 = System.currentTimeMillis();
                        try{
                            for (; num > 0; num--) {
                                ContentValues v1 = new ContentValues();
                                v1.put("name", "旋涡鸣人"+num);
                                v1.put("position", "七代火影"+num);
                                db.insert("huoying", null, v1);
                            }
                            db.setTransactionSuccessful();
                        }finally {
                            db.endTransaction();
                            System.out.println("结束");
                            System.out.println("用时"+ (System.currentTimeMillis()-l2) + "毫秒");
                        }
                    }
                }).start();
            }
        });

    }
}
