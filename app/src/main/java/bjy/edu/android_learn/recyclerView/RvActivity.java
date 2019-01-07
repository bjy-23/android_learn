package bjy.edu.android_learn.recyclerView;

import android.graphics.Rect;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import bjy.edu.android_learn.R;

public class RvActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rv);

        recyclerView = findViewById(R.id.recyclerView);
        List<String> data = new ArrayList<>();
        for (int i=0; i< 30; i++){
            data.add("hh:   " + i);
        }
        Adapter adapter = new Adapter(data);
        ItemDragCallback itemDragCallback = new ItemDragCallback(adapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(itemDragCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
        recyclerView.setAdapter(adapter);
//        recyclerView.setLayoutManager(new SmoothScrollLayoutManager(RvActivity.this));
//        recyclerView.setLayoutManager(new LinearLayoutManager(RvActivity.this, LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setLayoutManager(new GridLayoutManager(RvActivity.this, 3, LinearLayoutManager.VERTICAL, false));
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

                // 0:   SCROLL_STATE_IDLE 已停止
                // 1:   SCROLL_STATE_DRAGGING 正在被拖动
                // 2:   SCROLL_STATE_SETTLING 不再被拖动，将要停止

                Log.e("onScrollStateChanged", newState + "");
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                // dx：水平方向上的改变距离，当前的scrollX 与上一次记录的ScrollX的差值； >0 表示向右滑动

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
        private List<String> data;

        public Adapter(List<String> data) {
            this.data = data;
        }

        public List<String> getData() {
            return data;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(RvActivity.this).inflate(R.layout.rv_, parent, false));
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            ViewHolder viewHolder = (ViewHolder) holder;
            viewHolder.textView.setText(data.get(position));
        }

        @Override
        public int getItemCount() {
            return data.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder{
            TextView textView;
            public ViewHolder(View itemView) {
                super(itemView);

                textView = itemView.findViewById(R.id.tv);
            }
        }
    }

    class ItemDragCallback extends ItemTouchHelper.Callback {
        private Adapter adapter;

        public ItemDragCallback(Adapter adapter) {
            this.adapter = adapter;
        }

        @Override
        public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
            if (recyclerView.getLayoutManager() instanceof GridLayoutManager) {
                final int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN |
                        ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
                final int swipeFlags = 0;
                return makeMovementFlags(dragFlags, swipeFlags);
            } else {
                final int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
                final int swipeFlags = 0;
                return makeMovementFlags(dragFlags, swipeFlags);
            }
        }

        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            //得到当拖拽的viewHolder的Position
            int fromPosition = viewHolder.getAdapterPosition();
            //拿到当前拖拽到的item的viewHolder
            int toPosition = target.getAdapterPosition();
            if (fromPosition < toPosition) {
                for (int i = fromPosition; i < toPosition; i++) {
                    Collections.swap(adapter.getData(), i, i + 1);
                }
            } else {
                for (int i = fromPosition; i > toPosition; i--) {
                    Collections.swap(adapter.getData(), i, i - 1);
                }
            }
            adapter.notifyItemMoved(fromPosition, toPosition);
            return true;
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

        }
    }
}
