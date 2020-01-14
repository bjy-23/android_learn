package bjy.edu.android_learn.sqlite;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import bjy.edu.android_learn.R;

public class SqlResultActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sql_result);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        if (SqlHelper.getInstance() == null){
            Toast.makeText(this, "数据库未创建", Toast.LENGTH_SHORT).show();
            return;
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                SQLiteDatabase sqLiteDatabase = SqlHelper.getInstance().getReadableDatabase();

                final Cursor cursor = sqLiteDatabase.rawQuery("select * from huoying", null);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progressBar = new ProgressBar(SqlResultActivity.this);
                        progressBar.setMax(cursor.getCount());
                        progressBar.setProgress(0);
                    }
                });
                final List<HuoYing> data = new ArrayList<>();
                while (cursor.moveToNext()){
                    HuoYing huoYing = new HuoYing();
                    huoYing.setName(cursor.getString(cursor.getColumnIndex("name")));
                    huoYing.setPosition(cursor.getString(cursor.getColumnIndex("position")));
                    data.add(huoYing);
                }

                cursor.close();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        recyclerView.setAdapter(new RvAdapter(SqlResultActivity.this, data));
                    }
                });
            }
        }).start();
    }

    class RvAdapter extends RecyclerView.Adapter{
        private Context context;
        private List<HuoYing> data;

        public RvAdapter(Context context, List<HuoYing> data) {
            this.context = context;
            this.data = data;
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.rv_sql_result, viewGroup, false));
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int i) {
            ViewHolder viewHolder = (ViewHolder) holder;
            HuoYing huoYing = data.get(i);
            viewHolder.tv_index.setText(String.valueOf(i));
            viewHolder.tv_name.setText(huoYing.getName());
            viewHolder.tv_position.setText(huoYing.getPosition());
        }

        @Override
        public int getItemCount() {
            if (data != null)
                return data.size();
            return 0;
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        private TextView tv_index;
        private TextView tv_name;
        private TextView tv_position;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_index = itemView.findViewById(R.id.tv_index);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_position = itemView.findViewById(R.id.tv_position);
        }
    }
}
