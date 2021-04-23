package com.example.test_listview;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class ColorDecoration extends RecyclerView.ItemDecoration {
    int color;
    int offset;
    int orientation = VERTICAL;
    Paint paint;
    public static final int HORIZONTAL = 0;
    public static final int VERTICAL = 1;
    public static final int GRID = 3;

    public ColorDecoration(int color, int offset, int orientation) {
        this.color = color;
        this.offset = offset;
        this.orientation = orientation;

        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setDither(true);
        paint.setColor(color);
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

        if (parent.getChildAdapterPosition(view) != 0){
            if (orientation == VERTICAL)
                outRect.top = offset;
            else if (orientation == HORIZONTAL)
                outRect.left = offset;
            else{
                outRect.left = offset;
                outRect.top = offset;
            }
        }
    }

    @Override
    public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.onDraw(c, parent, state);

        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View view = parent.getChildAt(i);
            if (parent.getChildAdapterPosition(view) == 0)
                continue;

            if (orientation == VERTICAL){
                c.drawRect(parent.getPaddingLeft(), view.getTop()-offset, parent.getWidth()-parent.getPaddingRight(), view.getTop(), paint);
            }else if (orientation == HORIZONTAL){
                c.drawRect(view.getLeft()-offset, parent.getPaddingTop(), view.getLeft(), view.getHeight()-parent.getPaddingBottom(), paint);
            }else {
                c.drawRect(parent.getPaddingLeft(), view.getTop()-offset, parent.getWidth()-parent.getPaddingRight(), view.getTop(), paint);
                c.drawRect(view.getLeft()-offset, parent.getPaddingTop(), view.getLeft(), view.getHeight()-parent.getPaddingBottom(), paint);
            }
        }
    }
}
