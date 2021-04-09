package com.example.test_k_line;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

public class SimpleKlineView  extends KLineView{
    public SimpleKlineView(Context context) {
        super(context);
    }

    public SimpleKlineView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        if (linePaddingBottomRate == 0)
            linePaddingTopRate = 0.0f;
        if (linePaddingBottomRate == 0)
            linePaddingBottomRate = 0.2f;
    }
}
