package com.example.test_listview;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class ColorGridDecoration extends RecyclerView.ItemDecoration {
    private Paint paint;
    private int color;
    private int offsetL;
    private int offsetT;
    private int spaceCount;
    int orientation = VERTICAL;
    public static final int HORIZONTAL = 0;
    public static final int VERTICAL = 1;

    public ColorGridDecoration(int spaceCount, int orientation, int color, int offsetL, int offsetT) {
        this.spaceCount = spaceCount;
        this.orientation = orientation;
        this.color = color;
        this.offsetL = offsetL;
        this.offsetT = offsetT;

        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setDither(true);
        paint.setColor(color);
    }

    @Override
    public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.onDraw(c, parent, state);

        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View view = parent.getChildAt(i);
            if (orientation == VERTICAL){
                if (parent.getChildAdapterPosition(view) / spaceCount != 0) {
                    //只画一条
                    if (parent.getChildAdapterPosition(view) % spaceCount == 0){
                        c.drawRect(parent.getPaddingLeft(), view.getTop()-offsetT, parent.getWidth()-parent.getPaddingRight(), view.getTop(), paint);
                    }
                }

                if (parent.getChildAdapterPosition(view) % spaceCount != 0){
                    c.drawRect(view.getLeft()-offsetL, view.getTop(), view.getLeft(), view.getBottom(), paint);
                }
            }else {
                if (parent.getChildAdapterPosition(view) / spaceCount != 0) {
                    //只画一条
                    if (parent.getChildAdapterPosition(view) % spaceCount == 0){
                        c.drawRect(view.getLeft()-offsetL, parent.getPaddingTop(), view.getLeft(), parent.getHeight()-parent.getPaddingBottom(), paint);
                    }
                }

                if (parent.getChildAdapterPosition(view) % spaceCount != 0){
                    c.drawRect(view.getLeft(), view.getTop()-offsetT, view.getRight(), view.getTop(), paint);
                }
            }
        }
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

        if (orientation == VERTICAL) {
            //第一行没有上边距
            if (parent.getChildAdapterPosition(view) / spaceCount != 0) {
                outRect.top = offsetT;
            }
            //第一列没有左边距
            if (parent.getChildAdapterPosition(view) % spaceCount != 0) {
                outRect.left = offsetT;
            }
        } else {
            //第一行没有上边距
            if (parent.getChildAdapterPosition(view) % spaceCount != 0) {
                outRect.top = offsetT;
            }
            //第一列没有左边距
            if (parent.getChildAdapterPosition(view) / spaceCount != 0) {
                outRect.left = offsetT;
            }
        }
    }
}
