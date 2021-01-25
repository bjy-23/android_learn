package bjy.edu.android_learn;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class TestFragment extends Fragment {
    Handler handler = new Handler();
    {
        Log.i("111222", "new Handler");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_test, null);

        handler.post(new Runnable() {
            @Override
            public void run() {
                Log.i("111222", "thread " + Thread.currentThread().getName());
            }
        });

        return view;
    }


}
