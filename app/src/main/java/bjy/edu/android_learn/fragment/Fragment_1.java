package bjy.edu.android_learn.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
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
import bjy.edu.android_learn.socket.SocketActivity;

public class Fragment_1  extends Fragment{
    public static final String TAG = Fragment_1.class.getSimpleName();
    String tag = "";

    private Timer timer = new Timer();

    private TextView tv_1;

    public Fragment_1() {
    }

    public Fragment_1(String tag) {
        this.tag = tag;
    }

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

        //校验userVisibleHint属性
        tv_1 = view.findViewById(R.id.tv_1);
        tv_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "userVisibleHint: " + getUserVisibleHint());
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Log.i(TAG, "userVisibleHint: " + getUserVisibleHint());
                    }
                }, 3000);

                startActivity(new Intent(getActivity(), SocketActivity.class));

            }
        });
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
