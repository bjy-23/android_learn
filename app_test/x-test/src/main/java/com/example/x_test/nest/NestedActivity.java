package com.example.x_test.nest;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.x_test.R;

public class NestedActivity extends AppCompatActivity {
    private static final String TAG = "NestedActivity";
    MyRecyclerView recyclerView;
    NestedScrollView scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nested);

        scrollView = findViewById(R.id.scrollView);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(NestedActivity.this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(new RecyclerView.Adapter() {
            @NonNull
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                return new ViewHolder(LayoutInflater.from(NestedActivity.this).inflate(R.layout.item_rv_nest, null));
            }

            @Override
            public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
                ViewHolder viewHolder = (ViewHolder) holder;
                viewHolder.tv.setText(position+ "");
            }

            @Override
            public int getItemCount() {
                return 60;
            }
        });

        recyclerView.post(new Runnable() {
            @Override
            public void run() {
                Log.i(TAG, "height: " + recyclerView.getHeight());

                ViewGroup.LayoutParams layoutParams = recyclerView.getLayoutParams();
//                layoutParams.height = scrollView.getHeight() -
            }
        });
    }

    public static 
    class ViewHolder extends RecyclerView.ViewHolder{
        TextView tv;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tv = itemView.findViewById(R.id.tv);
        }
    }
}