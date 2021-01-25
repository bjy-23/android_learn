package edu.bjy.plugin.notepad;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import edu.bjy.plugin.notepad.database.DBHelper;
import edu.bjy.plugin.notepad.database.DBManager;

public class NoteListActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private List<NotepadBean> data = new ArrayList<>();
    private NotepadAdapter notepadAdapter;


    private TextView tv_add;

    private static final int REQUEST_ADD = 1000;
    private static final int REQUEST_UPDATE = 1001;

    private ExecutorService executorService = new ThreadPoolExecutor(5, 5, 0, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(), new ThreadFactory() {
        private AtomicInteger atomicInteger = new AtomicInteger(0);
        @Override
        public Thread newThread(@NonNull Runnable r) {
            return new Thread(r, "notepad#" + atomicInteger.getAndIncrement());
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_list);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
        initView();

        initData();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null){
            NotepadBean notepadBean = (NotepadBean) data.getParcelableExtra(IntentConst.NOTE);
            switch (requestCode){
                case REQUEST_ADD:
                    refreshAdd(notepadBean);
                    break;
                case REQUEST_UPDATE:
                    int position = data.getIntExtra(IntentConst.POSITION, -1);
                    refreshUpdate(notepadBean, position);
                    break;
            }
        }
    }

    private void initView(){
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(NoteListActivity.this, LinearLayoutManager.VERTICAL, false));
        notepadAdapter = new NotepadAdapter(NoteListActivity.this, data);
        recyclerView.setAdapter(notepadAdapter);
        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                outRect.set(0, 0, 0, Utils.dp2px(NoteListActivity.this, 10));
            }
        });
        notepadAdapter.setOnItemClick(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(NoteListActivity.this, NoteEditActivity.class);
                intent.putExtra(IntentConst.NOTE, data.get(position));
                intent.putExtra(IntentConst.POSITION, position);
                startActivityForResult(intent, REQUEST_UPDATE);
            }
        });

        tv_add = findViewById(R.id.tv_add);
        tv_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(NoteListActivity.this, NoteEditActivity.class), REQUEST_ADD);
            }
        });
        
    }

    private void initData(){
        //数据库查询
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                DBManager dbManager = new DBManager(NoteListActivity.this);
                List<NotepadBean> list = dbManager.queryTableNote();
                data.addAll(list);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        notepadAdapter.notifyDataSetChanged();
                    }
                });
            }
        };

        executorService.submit(runnable);
    }

    private void refreshAdd(NotepadBean notepadBean){
        if (data != null && notepadBean != null){
            data.add(notepadBean);
            notepadAdapter.notifyItemInserted(data.size() - 1);
        }
    }

    private void refreshUpdate(NotepadBean notepadBean, int position){
        if (data != null && notepadBean != null && position > -1){
            data.set(position, notepadBean);
            notepadAdapter.notifyItemChanged(position);
        }
    }

    class NotepadAdapter extends RecyclerView.Adapter{
        private Context context;
        private List<NotepadBean> data;
        private OnItemClickListener onItemClickListener;

        public NotepadAdapter(Context context, List<NotepadBean> data) {
            this.context = context;
            this.data = data;
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            return new NoteViewHolder(LayoutInflater.from(context).inflate(R.layout.note_list_item, viewGroup, false));
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
            NotepadBean bean = data.get(i);
            if (bean != null){
                final NoteViewHolder holder = (NoteViewHolder) viewHolder;
                holder.tv_note.setText(bean.getNote());
                holder.tv_timestamp.setText(Utils.convertTime(bean.getTimestamp()));
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (onItemClickListener != null){
                            onItemClickListener.onItemClick(holder.getAdapterPosition());
                        }
                    }
                });
            }
        }

        @Override
        public int getItemCount() {
            if (data == null)
                return 0;
            return data.size();
        }

        public void setOnItemClick(OnItemClickListener onItemClickListener){
            this.onItemClickListener = onItemClickListener;
        }
    }

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    class NoteViewHolder extends RecyclerView.ViewHolder{
        private TextView tv_note;
        private TextView tv_timestamp;

        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_note = itemView.findViewById(R.id.tv_note);
            tv_timestamp = itemView.findViewById(R.id.tv_timestamp);
        }
    }
}