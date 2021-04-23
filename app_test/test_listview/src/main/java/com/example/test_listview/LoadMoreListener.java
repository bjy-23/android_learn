package com.example.test_listview;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.List;

public abstract class LoadMoreListener extends RecyclerView.OnScrollListener {
    private LinearLayoutManager linearLayoutManager;
    private List data;

    private boolean hasOnScrollStateChanged = false;
    private int minLoadLimit; // 刷新数据的最小个数
    private int loadMoreState;
    public static final int LOAD_MORE_INIT = 0;
    public static final int LOAD_MORE_RUNNING = 1;

    public LoadMoreListener() {
    }

    public LoadMoreListener(LinearLayoutManager linearLayoutManager, List data, int minLoadLimit) {
        this.linearLayoutManager = linearLayoutManager;
        this.data = data;
        this.minLoadLimit = minLoadLimit;
    }

    @Override
    public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);

        hasOnScrollStateChanged = true;
    }

    @Override
    public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        int position = linearLayoutManager.findLastCompletelyVisibleItemPosition();

        Log.i("111222", "loadMoreState : " + loadMoreState);
        if (loadMoreEnable(position) && loadMoreState == LOAD_MORE_INIT){
            loadMoreState = LOAD_MORE_RUNNING;
//            if (hasOnScrollStateChanged)
                loadMore();
//            else {
//                loadMoreWithoutStateChange();
//            }
        }
    }

    //加载更多操作
    public abstract void loadMore();

    public abstract void loadMoreWithoutStateChange();

    //可以重写
    public boolean loadMoreEnable(int lastCompletelyVisibleItemPosition){
        if (data == null)
            return false;
        return lastCompletelyVisibleItemPosition >= minLoadLimit -1 && lastCompletelyVisibleItemPosition >= data.size()-1 ;
    }

    public int getLoadMoreState() {
        return loadMoreState;
    }

    public void setLoadMoreState(int loadMoreState) {
        this.loadMoreState = loadMoreState;
    }

    public int getMinLoadLimit() {
        return minLoadLimit;
    }

    public void setMinLoadLimit(int minLoadLimit) {
        this.minLoadLimit = minLoadLimit;
    }
}
