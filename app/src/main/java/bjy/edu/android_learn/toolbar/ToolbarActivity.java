package bjy.edu.android_learn.toolbar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import bjy.edu.android_learn.R;

public class ToolbarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toolbar);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //左边图标
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //隐藏标题和副标题
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: //左边图标的Id
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
