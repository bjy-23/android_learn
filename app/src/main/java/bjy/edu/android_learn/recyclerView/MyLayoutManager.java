package bjy.edu.android_learn.recyclerView;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by sogubaby on 2018/6/28.
 */

public class MyLayoutManager extends RecyclerView.LayoutManager {
    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(RecyclerView.LayoutParams.WRAP_CONTENT, RecyclerView.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        super.onLayoutChildren(recycler, state);

        detachAndScrapAttachedViews(recycler);

        rePosition(recycler);
    }

    private void rePosition(RecyclerView.Recycler recycler){
        int totalHeight = 0;
        for (int i=0; i<getItemCount(); i++){
            View view = recycler.getViewForPosition(i);

            addView(view);
            measureChildWithMargins(view, 0, 0);
            int width = getDecoratedMeasuredWidth(view); // 计算view实际大小，包括了ItemDecorator中设置的偏移量。
            int height = getDecoratedMeasuredHeight(view);
            calculateItemDecorationsForChild(view, new Rect());
            layoutDecorated(view, 0, totalHeight, width, totalHeight + height);
            totalHeight += height;
        }
    }
}
