package bjy.edu.android_learn.popupwindow;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import bjy.edu.android_learn.R;

public class PopupwindowActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popupwindow);

        final TextView textView = findViewById(R.id.tv);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //简单使用
                ImageView imageView = new ImageView(PopupwindowActivity.this);
                imageView.setImageResource(R.mipmap.ic_launcher);
                imageView.setScaleType(ImageView.ScaleType.FIT_START);
                //view 是直接new出来的，需要设置宽高
                PopupWindow popupWindow = new PopupWindow(imageView, 250, 100);
                //true :点击其他区域会使popupwindow消失
                popupWindow.setFocusable(true);
                popupWindow.setBackgroundDrawable(new ColorDrawable(Color.BLUE));

                //显示在某个view下方，与textview的左边界对齐，但也要考虑到屏幕的宽度限制
                popupWindow.showAsDropDown(textView);

                //显示在某个view下方, 并设置 x、y方向的偏移值，左负右正
//                popupWindow.showAsDropDown(textView, 50, 0);
            }
        });

    }
}
