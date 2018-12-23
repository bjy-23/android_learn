package bjy.edu.android_learn.recyclerView;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

/**
 * Created by sogubaby on 2018/4/23.
 */

public class HeigthExpandableRecyclerView extends RecyclerView {
    public HeigthExpandableRecyclerView(Context context) {
        this(context, null);
    }

    public HeigthExpandableRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    protected void onMeasure(int widthSpec, int heightSpec) {

        int expandSpec = MeasureSpec.makeMeasureSpec(
                Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);

        super.onMeasure(widthSpec, expandSpec);
    }
}
