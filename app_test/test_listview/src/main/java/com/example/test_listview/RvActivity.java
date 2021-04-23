package com.example.test_listview;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.NonNull;
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
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class RvActivity extends AppCompatActivity {
    List<String> list = new ArrayList<>();
    int count = 10;
    int loadMoreTimes=0;
    private static final String TAG = "111222";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rv);


        for (int i=0; i<count; i++){
            list.add("hello___"+i);
        }

        RecyclerView recyclerView = findViewById(R.id.rv);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(RvActivity.this, LinearLayoutManager.HORIZONTAL, false);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(RvActivity.this, 3, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(new Adapter(list));
//        recyclerView.addItemDecoration(new ColorDecoration(Color.RED, 5, ColorDecoration.GRID));
        recyclerView.addItemDecoration(new ColorGridDecoration(3, ColorGridDecoration.VERTICAL, Color.RED, 5, 5));

//        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
//                super.onScrollStateChanged(recyclerView, newState);
//
//                Log.i(TAG, String.format("onScrollStateChanged  newState[%d]", newState));
//
//                int s1 = RecyclerView.SCROLL_STATE_DRAGGING;
//            }
//
//            @Override
//            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
//
//                Log.i(TAG, String.format("onScrolled  dx[%d] dy[%d]", dx, dy));
//               Log.i(TAG, "findLastCompletelyVisibleItemPosition : " + linearLayoutManager.findLastCompletelyVisibleItemPosition());
//
//
//            }
//        });

        recyclerView.addOnScrollListener(new LoadMoreListener(linearLayoutManager, list, 10) {
            @Override
            public void loadMore() {
                if (loadMoreTimes < 2){
                    count += 10;
                    loadMoreTimes++;
                }else {
                    return;
                }
                Log.i(TAG, String.format("loadMore() count[%d] loadMoreTimes[%d]", count, loadMoreTimes));
                for (int i=count-10; i<count; i++){
                    list.add("hello___"+i);
                }
//                recyclerView.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
                recyclerView.post(new Runnable() {
                    @Override
                    public void run() {
                        recyclerView.getAdapter().notifyDataSetChanged();
                        setLoadMoreState(LoadMoreListener.LOAD_MORE_INIT);
                    }
                });

//                    }
//                }, 3000);

            }

            @Override
            public void loadMoreWithoutStateChange() {
                List<String> tempList = new ArrayList<>();
                tempList.addAll(list);
                if (loadMoreTimes < 2){
                    count += 10;
                    loadMoreTimes++;
                }else {
                    return;
                }
                Log.i(TAG, String.format("loadMore() count[%d] loadMoreTimes[%d]", count, loadMoreTimes));
                for (int i=count-10; i<count; i++){
                    tempList.add("hello___"+i);
                }

                list = tempList;
                recyclerView.post(new Runnable() {
                    @Override
                    public void run() {
                        recyclerView.setAdapter(new Adapter(list));
                    }
                });
            }
        });
    }

    class Adapter extends RecyclerView.Adapter{
        List<String> list;

        public Adapter(List<String> list) {
            this.list = list;
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.rv_test, null));
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
            ViewHolder holder = (ViewHolder) viewHolder;
            holder.textView.setText(list.get(i));
        }

        @Override
        public int getItemCount() {
            return list.size();
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView textView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textView = itemView.findViewById(R.id.tv_content);
        }
    }
}