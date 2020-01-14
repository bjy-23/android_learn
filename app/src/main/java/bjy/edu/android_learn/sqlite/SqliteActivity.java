package bjy.edu.android_learn.sqlite;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import bjy.edu.android_learn.R;

public class SqliteActivity extends AppCompatActivity {
    private static final String TAG = SqliteActivity.class.getSimpleName();
    SqlHelper sqlHelper;
    TextView tv_query;

    int position = 0;
    private static final String[] HY_NAME = {"千手柱间", "千手扉间", "袁飞日斩"};
    private static final String[] HY_POSITION = {"初代火影", "二代火影", "三代火影"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sqlite);

        tv_query = findViewById(R.id.tv_query);

        sqlHelper = SqlHelper.getInstance();

        TextView tv_db = findViewById(R.id.tv_db);
        tv_db.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SqliteActivity.this, SqlResultActivity.class));
            }
        });

        final Button btn_insert = findViewById(R.id.btn_insert);
        btn_insert.setText("INSERT " + HY_NAME[position] + " " + HY_POSITION[position]);
        btn_insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (position >= HY_NAME.length)
                    return;

                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        SQLiteDatabase db = sqlHelper.getWritableDatabase();
                        //SQL语句
                        db.execSQL("insert into huoying(name, position) values ('" + HY_NAME[position] + "', '" + HY_POSITION[position] + "')");

                        //原生API
//                ContentValues v1 = new ContentValues();
//                v1.put("name", "千手柱间");
//                v1.put("position", "初代火影");
//                db.insert("huoying", null, v1);

                        position++;
                        if (position >= HY_NAME.length)
                            return;

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                btn_insert.setText("INSERT " + HY_NAME[position] + " " + HY_POSITION[position]);
                            }
                        });
                    }
                }).start();
            }
        });

        //根据条件查询
        Button btn_query_4 = findViewById(R.id.btn_query_4);
        final TextView tv_query_4 = findViewById(R.id.tv_query_4);
        btn_query_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        SQLiteDatabase db = sqlHelper.getReadableDatabase();
                        String name = "";

//                        //SQL语句
                        //一个条件
//                        Cursor cursor = db.rawQuery("select * from huoying where position = ?", new String[]{"初代火影"});
                        //多个条件
                        Cursor cursor = db.rawQuery("select * from huoying where position = ? and name = ?", new String[]{HY_POSITION[0], HY_NAME[0]});
                        while (cursor.moveToNext()) {
                            name += cursor.getString(cursor.getColumnIndex("name")) + "、";
                        }
                        cursor.close();

                        //原生API
//                        //一个条件
////                        Cursor cursor = db.query("huoying", new String[]{"name"}, "position = ?", new String[]{HY_POSITION[0]}, null, null, null);
//                        //多个条件
//                        Cursor cursor = db.query("huoying", new String[]{"name"}, "position = ? and name = ?", new String[]{HY_POSITION[0], HY_NAME[0]}, null, null, null);
//                        while (cursor.moveToNext()) {
//                            name += cursor.getString(cursor.getColumnIndex("name")) + "、";
//                        }
//                        cursor.close();

                        final String name2 = name.substring(0, name.length()-1);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                tv_query_4.setText(name2);
                            }
                        });

                    }
                }).start();

            }
        });

        //根据条件查询、distinct
        Button btn_query_5 = findViewById(R.id.btn_query_5);
        final TextView tv_query_5 = findViewById(R.id.tv_query_5);
        btn_query_5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        SQLiteDatabase db = sqlHelper.getReadableDatabase();
                        String name = "";

//                        //SQL语句
//                        Cursor cursor = db.rawQuery("select distinct name from huoying where position = ?", new String[]{"初代火影"});
//                        //多个参数去重
////                        Cursor cursor = db.rawQuery("select distinct name, id from huoying where position = ?", new String[]{"初代火影"});

                        //原生API
//                        Cursor cursor = db.query(true, "huoying", new String[]{"name"}, "position = ?", new String[]{HY_POSITION[0]}, null, null, null, null);
                        //多个参数去重
                        Cursor cursor = db.query(true, "huoying", new String[]{"name", "id"}, "position = ?", new String[]{HY_POSITION[0]}, null, null, null, null);

                        while (cursor.moveToNext()){
                            name += cursor.getString(cursor.getColumnIndex("name")) + "、";
                        }
                        cursor.close();
                        final String name2 = name;
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                tv_query_5.setText(name2);
                            }
                        });

                    }
                }).start();

            }
        });

        //根据条件查询、group by
        Button btn_query_6 = findViewById(R.id.btn_query_6);
        final TextView tv_query_6 = findViewById(R.id.tv_query_6);
        btn_query_6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        SQLiteDatabase db = sqlHelper.getReadableDatabase();
                        String name = "";

//                        //SQL语句
//                        Cursor cursor = db.rawQuery("select  * from huoying where position = ? group by name", new String[]{HY_POSITION[0]});
//                        //多个参数
////                        Cursor cursor = db.rawQuery("select * from huoying where position = ? group by name, id", new String[]{HY_POSITION[0]});
//                        while (cursor.moveToNext()) {
//                            name += cursor.getString(cursor.getColumnIndex("name")) + "、";
//                        }
//                        cursor.close();

                        //原生API
//                        Cursor cursor = db.query("huoying", new String[]{"name"}, "position = ?", new String[]{HY_POSITION[0]}, "name", null, null, null);
                        //多个参数去重
                        Cursor cursor = db.query("huoying", new String[]{"name", "id"}, "position = ?", new String[]{HY_POSITION[0]}, null, null, null, null);
                        if (cursor.moveToFirst()){
                            name += cursor.getString(cursor.getColumnIndex("name")) + "、";
                        }
                        cursor.close();


                        final String name2 = name.substring(0, name.length()-1);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                tv_query_6.setText(name2);
                            }
                        });

                    }
                }).start();

            }
        });

        //根据条件查询、limit
        // limit 的第一个参数是索引的起始位置，第二个参数是偏移值；
        // 使用limit很容易 查询第一条、第 N 条、最后一条（配合 order by 使用）
        // 起始位置为0时，第一个参数可以省略
        // limit 1, 2 :不受, 后的空格长度影响
        // limit 的第一个参数可以超过表中数据的数目，此时cursor为空的集合
        Button btn_query_7 = findViewById(R.id.btn_query_7);
        final TextView tv_query_7 = findViewById(R.id.tv_query_7);
        btn_query_7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        SQLiteDatabase db = sqlHelper.getReadableDatabase();
                        String name = "";

                        //SQL语句
//                        Cursor cursor = db.rawQuery("select name from huoying limit 1, 2", null);
//                        while (cursor.moveToNext()) {
//                            name += cursor.getString(cursor.getColumnIndex("name")) + "、";
//                        }
//                        cursor.close();

                        //原生API
                        Cursor cursor = db.query("huoying", new String[]{"name"}, null, null, null, null, null, "1,2");
                        while (cursor.moveToNext()){
                            name += cursor.getString(cursor.getColumnIndex("name")) + "、";
                        }
                        cursor.close();

                        if (TextUtils.isEmpty(name)){
                            name = "无数据啊";
                        }
                        final String name2 = name.substring(0, name.length()-1);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                tv_query_7.setText(name2);
                            }
                        });

                    }
                }).start();

            }
        });

        //根据条件查询、order by
        // asc 升序；desc 降序
        // 配合limit 方便查询最后一条
        Button btn_query_8 = findViewById(R.id.btn_query_8);
        final TextView tv_query_8 = findViewById(R.id.tv_query_8);
        btn_query_8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        SQLiteDatabase db = sqlHelper.getReadableDatabase();
                        String name = "";

                        //SQL语句
//                        Cursor cursor = db.rawQuery("select name from huoying order by id desc limit 1", null);
//                        while (cursor.moveToNext()) {
//                            name += cursor.getString(cursor.getColumnIndex("name")) + "、";
//                        }
//                        cursor.close();

                        //原生API
                        Cursor cursor = db.query("huoying", new String[]{"name"}, null, null, null, null, "id desc", "1");
                        while (cursor.moveToNext()){
                            name += cursor.getString(cursor.getColumnIndex("name")) + "、";
                        }
                        cursor.close();


                        final String name2 = name.substring(0, name.length()-1);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                tv_query_8.setText(name2);
                            }
                        });

                    }
                }).start();

            }
        });

        Button btn_delete = findViewById(R.id.btn_delete);
        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        SQLiteDatabase db = sqlHelper.getWritableDatabase();

                        //SQL语句
//                db.execSQL("delete from huoying where position = '初代火影'");

                        //原生API
                        db.delete("huoying", null, null);
                    }
                }).start();
            }
        });

        Button btn_delete_2 = findViewById(R.id.btn_delete_2);
        btn_delete_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        SQLiteDatabase db = sqlHelper.getWritableDatabase();

                        //SQL语句
//                db.execSQL("delete from huoying where position = '初代火影'");

                        //原生API
                        db.delete("huoying", "position = ?", new String[]{"初代火影"});
                    }
                }).start();
            }
        });

        final int num = 10000;
        Button btn_insert_more = findViewById(R.id.btn_insert_more);
        final TextView tv_insert_more = findViewById(R.id.tv_insert_more);
        btn_insert_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        SQLiteDatabase db = sqlHelper.getWritableDatabase();
                        int num2 = num;
                        long l1 = System.currentTimeMillis();
                        for (; num2 > 0; num2--) {
                            ContentValues v1 = new ContentValues();
                            v1.put("name", "旋涡鸣人"+num2);
                            v1.put("position", "七代火影"+num2);
                            db.insert("huoying", null, v1);
                        }
                        float l = (float)(System.currentTimeMillis() - l1) / 1000;
                        final String s = String.format("%.1f秒", l);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                tv_insert_more.setText("用时：" + s);
                            }
                        });
                    }
                }).start();
            }
        });

        Button btn_insert_more2 = findViewById(R.id.btn_insert_more2);
        final TextView tv_insert_more2 = findViewById(R.id.tv_insert_more2);
        btn_insert_more2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        SQLiteDatabase db = sqlHelper.getWritableDatabase();
                        int num2 = num;
                        long l1 = System.currentTimeMillis();
                        db.beginTransaction();
                        try{
                            for (; num2 > 0; num2--) {
                                ContentValues v1 = new ContentValues();
                                v1.put("name", "旋涡鸣人"+num2);
                                v1.put("position", "七代火影"+num2);
                                db.insert("huoying", null, v1);
                            }
                            db.setTransactionSuccessful();
                        }finally {
                            db.endTransaction();
                            float l = (float)(System.currentTimeMillis() - l1) / 1000;
                            final String s = String.format("%.1f秒", l);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    tv_insert_more2.setText("用时：" + s);
                                }
                            });
                        }
                    }
                }).start();
            }
        });
    }
}
