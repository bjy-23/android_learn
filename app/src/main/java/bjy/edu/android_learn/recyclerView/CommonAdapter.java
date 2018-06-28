package bjy.edu.android_learn.recyclerView;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by sogubaby on 2018/5/30.
 *
 *  RecyclerView通用的Adapter
 */

public abstract class CommonAdapter extends RecyclerView.Adapter {
    protected Context context;

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    protected OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
