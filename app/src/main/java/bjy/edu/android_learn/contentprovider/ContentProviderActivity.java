package bjy.edu.android_learn.contentprovider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import bjy.edu.android_learn.R;

public class ContentProviderActivity extends AppCompatActivity {

    String authority = "bjy.book";
    Uri bookUri = Uri.parse("content://" + authority +"");

    TextView tv_query;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_provider);

        tv_query = findViewById(R.id.tv_query);

        Button btn_insert = findViewById(R.id.btn_insert);
        btn_insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues values = new ContentValues();
                values.put("name", "android开发艺术");
                values.put("price", "50");
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
    }
}
