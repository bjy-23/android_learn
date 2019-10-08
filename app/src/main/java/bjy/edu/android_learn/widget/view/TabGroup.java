package bjy.edu.android_learn.widget.view;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import bjy.edu.android_learn.R;

/**
 *  tablayout 如何屏蔽tab里的点击事件
 */
public class TabGroup extends LinearLayout {
    private List<Tab> tabs;
    private List<TabSelectListener> tabSelectListeners;

    public TabGroup(Context context) {
        this(context, null);
    }

    public TabGroup(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        init();
    }

    private void init(){
        tabs = new ArrayList<>();
        tabSelectListeners = new ArrayList<>();
    }

    public void addTab(Tab tab){
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(0, LayoutParams.MATCH_PARENT);
        layoutParams.weight = 1;
        addView(tab.getView(), layoutParams);
        tabs.add(tab);
    }

    public void selectTab(int position){
        selectTab(tabs.get(position));
    }

    public void selectTab(Tab tab){
        for (Tab tab1: tabs){
            if (tab == tab1){
                for (TabSelectListener tabSelectListener: tabSelectListeners){
                    tabSelectListener.tabSelect(tab1);
                }
            }else {
                for (TabSelectListener tabSelectListener: tabSelectListeners){
                    tabSelectListener.tabUnSelect(tab1);
                }
            }
        }
    }

    public class Tab {
        private View view;
        private int layoutId;

        public Tab(int layoutId) {
            this.layoutId = layoutId;
            view = LayoutInflater.from(getContext()).inflate(layoutId, TabGroup.this, false);
            view.setClickable(true);
            view.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectTab(Tab.this);
                }
            });
        }

        public View getView(){
            return view;
        }
    }

    public interface TabSelectListener{
        void tabSelect(Tab tab);

        void tabUnSelect(Tab tab);
    }

    public void addTabSelectListener(TabSelectListener tabSelectListener){
        tabSelectListeners.add(tabSelectListener);
    }
}
