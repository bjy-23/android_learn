package com.example.test_listview;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class FragmentTest extends android.support.v4.app.Fragment {
    int position;

    public static FragmentTest getInstance(int position){
        Bundle bundle = new Bundle();
        bundle.putInt("position", position);

        FragmentTest fragmentTest = new FragmentTest();
        fragmentTest.setArguments(bundle);

        return fragmentTest;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_test, null);
        TextView textView = view.findViewById(R.id.tv_content);
        position = getArguments().getInt("position", -1);
        textView.setText("position: " + position);
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        Log.i("112233", "onDestroy: " + position);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        Log.i("112233", "onDestroyView: " + position);
    }
}
