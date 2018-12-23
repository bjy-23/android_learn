package bjy.edu.android_learn.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import bjy.edu.android_learn.R;

public class Fragment_1  extends Fragment{
    public static String TAG = "TAG";
    String tag = "";

    private Timer timer = new Timer();

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

//        tag = getArguments().getString(TAG);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_1, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView tv = view.findViewById(R.id.tv);

        tv.setText("bbbjy" + tag);

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Log.e("TimerTask1", "run  " + System.currentTimeMillis());
            }
        }, 0, 1000);

        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Log.e("TimerTask2", "run  " + System.currentTimeMillis());
            }
        }, 0, 1000);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 15);
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Log.e("TimerTask3", "run  " + System.currentTimeMillis());
            }
        }, calendar.getTime(), 1000);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        timer.cancel();
    }
}
