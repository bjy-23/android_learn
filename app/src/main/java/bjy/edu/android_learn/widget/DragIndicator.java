package bjy.edu.android_learn.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import bjy.edu.android_learn.R;

public class DragIndicator extends View{
    private Context context;

    public DragIndicator(Context context) {
        this(context, null);
    }

    public DragIndicator(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        init();
    }

    public void init(){
        inflate(context, R.layout.drag_indicator, null);
    }
}
