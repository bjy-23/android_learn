package bjy.edu.android_learn.recyclerView;

import android.content.Context;
import android.support.v7.widget.LinearSmoothScroller;

public class TopSmoothScroller extends LinearSmoothScroller {
    public TopSmoothScroller(Context context) {
        super(context);
    }

    @Override
    protected int getHorizontalSnapPreference() {
//        return super.getHorizontalSnapPreference();
        return SNAP_TO_START;
    }

    @Override
    protected int getVerticalSnapPreference() {
//        return super.getVerticalSnapPreference();
        return SNAP_TO_START;
    }
}
