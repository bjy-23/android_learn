package bjy.edu.android_learn.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import bjy.edu.android_learn.R;

//验证fragment的
public class FragmentLifeCycle extends Fragment {
    private static final String TAG = FragmentLifeCycle.class.getSimpleName();
    private String name = "null";

    public FragmentLifeCycle(String name) {
        this.name = name;
    }

    @Override
    public void onAttach(Context context) {
        Log.i(TAG, "onAttach - context - " + name);
        super.onAttach(context);
    }

    @Override
    public void onAttach(Activity activity) {
        Log.i(TAG, "onAttach - activity - " + name);
        super.onAttach(activity);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.i(TAG, "onCreate - " + name);
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.i(TAG, "onCreateView - " + name);
        View view = inflater.inflate(R.layout.fragment_life_cycle, container, false);
        TextView textView = view.findViewById(R.id.tv);
        textView.setText("name: " + name);
        return view;
    }

    @Override
    public void onStart() {
        Log.i(TAG, "onStart - " + name);
        super.onStart();
    }

    @Override
    public void onResume() {
        Log.i(TAG, "onResume - " + name);
        super.onResume();
    }

    @Override
    public void onPause() {
        Log.i(TAG, "onPause - " + name);
        super.onPause();
    }

    @Override
    public void onStop() {
        Log.i(TAG, "onStop - " + name);
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        Log.i(TAG, "onDestroyView - " + name);
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        Log.i(TAG, "onDestroy - " + name);
        super.onDestroy();
    }
}
