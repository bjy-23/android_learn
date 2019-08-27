package bjy.edu.android_learn_ipc;

import android.content.ContentValues;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/*
* ContentProvider跨进程访问
* */
public class ContentProviderActivity extends AppCompatActivity {

    String authority = "bjy.book";
    Uri bookUri = Uri.parse("content://" + authority +"");

    TextView tv_sum;
    TextView tv_query;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_provider);

        tv_sum = findViewById(R.id.tv_sum);
        tv_query = findViewById(R.id.tv_query);

        //跨进城访问时，exported = false 会报错
        //需要配置读写权限

        Button btn_insert = findViewById(R.id.btn_insert);
        btn_insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues values = new ContentValues();
                values.put("name", "java vm");
                values.put("price", "80");
                ContentProviderActivity.this.getContentResolver().insert(bookUri, values);
            }
        });

        Button btn_query = findViewById(R.id.btn_query);
        btn_query.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor cursor = ContentProviderActivity.this.getContentResolver().query(bookUri, new String[]{"name", "price"}, null, null, null);
                String content = "";
                if (cursor.moveToLast()){
                    content = cursor.getString(cursor.getColumnIndex("name")) + "-" + cursor.getString(cursor.getColumnIndex("price"));
                }
                cursor.close();
                tv_query.setText(content);
            }
        });

        //ContentObserver监听变化
        //需要 unregister
        //自定义的Provider需要在insert/delete/update时调用 getContext().getContentResolver().notifyChange(uri, null) 才能触发监听;
        ContentProviderActivity.this.getContentResolver().registerContentObserver(bookUri, true, new ContentObserver(new Handler()) {
            @Override
            public void onChange(boolean selfChange, Uri uri) {
                super.onChange(selfChange, uri);

                Cursor cursor = ContentProviderActivity.this.getContentResolver().query(uri, null, null, null, null);
                tv_sum.setText("当前数据条数：" + cursor.getCount());

            }
        });
    }
}
