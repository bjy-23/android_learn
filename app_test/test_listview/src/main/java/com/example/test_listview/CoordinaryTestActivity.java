package com.example.test_listview;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

public class CoordinaryTestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coordinary_test);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(new RecyclerView.Adapter() {
            @NonNull
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                return new TestViewHolder(new FrameLayout(CoordinaryTestActivity.this));
            }

            @Override
            public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                TextView textView = new TextView(CoordinaryTestActivity.this);
                textView.setText("position: " + i);

                TestViewHolder testViewHolder = (TestViewHolder) viewHolder;
                FrameLayout frameLayout = (FrameLayout) testViewHolder.itemView;
                frameLayout.addView(textView);
            }

            @Override
            public int getItemCount() {
                return 30;
            }
        });
    }

    class TestViewHolder extends RecyclerView.ViewHolder{
        public TestViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}