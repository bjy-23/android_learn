package bjy.edu.android_learn.widget.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import bjy.edu.android_learn.R;

/**
 * 选择器
 */
public class PickView extends RecyclerView {
    private Context context;
    private int width;

    /**
     * 同时展示在页面上的的数目
     */
    private int showNum = 3;

    /**
     * 默认选中的字体大小
     */
    private float selectedTextSize = 40f;

    /**
     * 字体梯度值
     */
    private float offsetTextSize = 18f;

    /**
     * todo 需要保证数据不重复
     * 可以选择的列表
     */
    private List<String> data;

    private boolean showing = false;

    TopSmoothScroller topSmoothScroller;

    LinearLayoutManager linearLayoutManager;

    public PickView(Context context) {
        this(context, null);
    }

    public PickView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;

        init();
    }

    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
        this.data = data;
    }

    private void init(){
        setOverScrollMode(View.OVER_SCROLL_NEVER);

        topSmoothScroller = new TopSmoothScroller(context);
    }

    public void show() {

        if (width == 0){
            showing = true;
            return;
        }

        Adapter adapter = new Adapter(context, data);
        setAdapter(adapter);
        linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        setLayoutManager(linearLayoutManager);

        addOnScrollListener(new OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                //停下的时候 字体最大的TextView居中
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    float textSize = 0;
                    String text = "";
                    for (int i = 0; i < getChildCount(); i++) {
                        View view = getChildAt(i);
                        TextView tv = view.findViewById(R.id.tv);
                        if (tv != null) {
                            if (tv.getTextSize() > textSize) {
                                textSize = tv.getTextSize();
                                text = tv.getText().toString();
                            }
                        }
                    }

                    int pos = 0;
                    for (int i = 0; i < data.size(); i++) {
                        if (data.get(i).equals(text)) {
                            pos = i;
                            break;
                        }
                    }

//                    smoothScrollToPosition(pos - 1);

                    topSmoothScroller.setTargetPosition(pos);
                    linearLayoutManager.startSmoothScroll(topSmoothScroller);
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

                int pos = 0;
                float size = 0;
                for (int i = 0; i < getChildCount(); i++) {
                    View child = recyclerView.getChildAt(i);
                    //改变TextView的size
                    TextView tv = child.findViewById(R.id.tv);
                    if (tv != null) {
                        float a = selectedTextSize - (Math.abs(child.getX() - child.getWidth()) / child.getWidth()) * offsetTextSize;
                        tv.setTextSize(a);

                        //获得最大字体的那个view的下标
                        if (a > size){
                            size = a;
                            pos = i;
                        }
                    }
                }

                //根据下标，显隐指示器
                for (int i=0; i< getChildCount(); i++){
                    View child = recyclerView.getChildAt(i);
                    View view = child.findViewById(R.id.view);
                    if (view != null){
                        if (i == pos){
                            view.setVisibility(View.VISIBLE);
                        }else {
                            view.setVisibility(View.GONE);
                        }
                    }
                }
            }
        });

        smoothScrollToPosition(1);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);

        if (width == 0) {
            width = getMeasuredWidth();

            if (showing)
                show();
        }
    }

    class Adapter extends RecyclerView.Adapter {
        private Context context;
        private List<String> data;

        public Adapter(Context context, List<String> data) {
            this.context = context;
            this.data = data;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.rv_pick, parent, false);
            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(width / showNum, ViewGroup.LayoutParams.MATCH_PARENT);
            view.setLayoutParams(layoutParams);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
            ViewHolder viewHolder = (ViewHolder) holder;

            if ((position == 0) || (position == data.size() + 1)) {
                viewHolder.tv.setText("");
            } else {
                viewHolder.tv.setText(data.get(position - 1));
            }

            viewHolder.itemView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
//                    smoothScrollToPosition(position);
//                    scrollToPosition(position);

                    topSmoothScroller.setTargetPosition(position-1);
                    linearLayoutManager.startSmoothScroll(topSmoothScroller);
                }
            });
        }

        @Override
        public int getItemCount() {
            if (data != null)
                return data.size() + 2;
            return 0;
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tv;
        private View view;

        public ViewHolder(View itemView) {
            super(itemView);

            tv = itemView.findViewById(R.id.tv);
            view = itemView.findViewById(R.id.view);
        }
    }

    class TopSmoothScroller extends LinearSmoothScroller {
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
}
