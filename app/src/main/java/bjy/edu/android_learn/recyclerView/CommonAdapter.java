package bjy.edu.android_learn.recyclerView;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by sogubaby on 2018/5/30.
 *
 *  RecyclerView通用的Adapter
 */

public abstract class CommonAdapter<VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter {
    protected Context context;
    private VH viewHolder;

    public CommonAdapter(VH viewHolder) {
        this.viewHolder = viewHolder;
    }

    ArrayList<FixedViewInfo> footerViewInfos = new ArrayList<>();

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    protected OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return viewHolder;
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        public ViewHolder(View itemView) {
            super(itemView);
        }
    }

    public void addFootView(View view){
        FixedViewInfo info = new FixedViewInfo();
        info.view = view;
        info.data = null;
        footerViewInfos.add(info);
    }

    class FixedViewInfo{
        View view;
        Object data;
    }
}
