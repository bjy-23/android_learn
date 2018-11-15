package bjy.edu.android_learn.recyclerView;

import android.graphics.Rect;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import bjy.edu.android_learn.R;

public class RvActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rv);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setAdapter(new Adapter());
        recyclerView.setLayoutManager(new SmoothScrollLayoutManager(RvActivity.this));
//        recyclerView.setLayoutManager(new LinearLayoutManager(RvActivity.this, LinearLayoutManager.HORIZONTAL, false));
//        recyclerView.setLayoutManager(new GridLayoutManager(RvActivity.this, 3, LinearLayoutManager.VERTICAL, false));
//        recyclerView.setLayoutManager(new GridLayoutManager(RvActivity.this, 3, LinearLayoutManager.HORIZONTAL, false));
//        recyclerView.setLayoutManager(new MyLayoutManager());

        //ItemDecoration
//        recyclerView.addItemDecoration(new MyItemDecoration());
        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                outRect.set(0, 0, 0, 10);
            }
        });

//        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        dividerItemDecoration.setDrawable(getResources().getDrawable(R.drawable.rect_red));
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setNestedScrollingEnabled(false);
        //滑动监听
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                Log.e("onScrollStateChanged", newState + "");
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                Log.e("onScrolled", "   dx: " + dx);
                Log.e("onScrolled", "   dy: " + dy);

                super.onScrolled(recyclerView, dx, dy);
            }
        });


        final Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                recyclerView.smoothScrollToPosition(position);

                position++;
                handler.postDelayed(this, 1000);
            }
        };
        handler.post(runnable);
    }

    class Adapter extends RecyclerView.Adapter{
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(RvActivity.this).inflate(R.layout.rv_, parent, false));
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            ViewHolder viewHolder = (ViewHolder) holder;
            viewHolder.textView.setText("哎呀， " + position);
        }

        @Override
        public int getItemCount() {
            return 100;
        }

        class ViewHolder extends RecyclerView.ViewHolder{
            TextView textView;
            public ViewHolder(View itemView) {
                super(itemView);

                textView = itemView.findViewById(R.id.tv);
            }
        }
    }
}
