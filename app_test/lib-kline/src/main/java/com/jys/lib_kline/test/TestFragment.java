package com.jys.lib_kline.test;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;

import com.jys.lib_kline.IndexView;
import com.jys.lib_kline.KLineData;
import com.jys.lib_kline.KLineView;
import com.jys.lib_kline.R;
import com.jys.lib_kline.VpTestActivity;

import java.util.Arrays;

public class TestFragment extends Fragment {
    int startPosition;
    int endPosition;
    int numCOunt = 5;

    int startPDown;
    int lastStartP;
    int Offset = 50;

    int fragmentP = 0;

    ViewPager viewPager;
    ScrollView scrollView;

    public static TestFragment getInstance(int position){
        Bundle bundle = new Bundle();
        bundle.putInt("position", position);

        TestFragment testFragment = new TestFragment();
        testFragment.setArguments(bundle);

        return testFragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        Bundle bundle = getArguments();
        if (bundle != null)
            fragmentP = bundle.getInt("position");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_test, null);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getActivity() instanceof VpTestActivity){
            VpTestActivity vpTestActivity = (VpTestActivity) getActivity();
            viewPager = vpTestActivity.viewPager;
        }
        scrollView = view.findViewById(R.id.scrollView);

        View v_bottom = view.findViewById(R.id.v_bottom);
        v_bottom.setAlpha(0.2f);
        if (fragmentP == 0){
            v_bottom.setBackgroundColor(Color.RED);
        }else if (fragmentP == 1){
            v_bottom.setBackgroundColor(Color.GREEN);
        }else {
            v_bottom.setBackgroundColor(Color.BLUE);
        }

        final float[] valuesAll = new float[]{20, 30, 20, 30, 20, 10, 20, 10, 20, 10};

        endPosition = valuesAll.length - 1;
        startPosition = endPosition - numCOunt +1;

        float[] values = Arrays.copyOfRange(valuesAll, startPosition, endPosition+1);
        KLineView kLineView = view.findViewById(R.id.kLine);
        KLineData kLineData = new KLineData();
        kLineData.yValueMax = 50;
        kLineData.yValueMin = 0;
        kLineData.xSpaceCount = values.length - 1;
        kLineData.kLineValues = values;

        kLineView.addKLineData(kLineData);

        kLineView.refreshAllData();
        kLineView.invalidate();

        IndexView indexView = view.findViewById(R.id.indexView);

        indexView.scaleListener = new IndexView.ScaleListener() {
            @Override
            public void scaleStart() {
//                Log.i("111222", "scaleStart");
                viewPager.requestDisallowInterceptTouchEvent(true);
                scrollView.requestDisallowInterceptTouchEvent(true);
            }

            @Override
            public void onScale(float scale) {
//                Log.i("111222", "onScale  " + scale);
            }

            @Override
            public void scaleEnd() {
//                Log.i("111222", "scaleEnd");
            }
        };
        //滑动
        indexView.scrollEnable = true;
        indexView.scrollListener = new IndexView.ScrollListener() {
            @Override
            public void scrollOffset(float deltaX, float deltaY) {
                if (startPosition == 0){
                    if (deltaX > 0 ){
                        viewPager.requestDisallowInterceptTouchEvent(false);
                    }else {
                        viewPager.requestDisallowInterceptTouchEvent(true);
                    }
                }
                if (startPosition == valuesAll.length-numCOunt){
                    if (deltaX < 0){
                        viewPager.requestDisallowInterceptTouchEvent(false);
                    }else {
                        viewPager.requestDisallowInterceptTouchEvent(true);
                    }
                }

                if (Math.abs(deltaY) > Math.abs(deltaX)){
                    scrollView.requestDisallowInterceptTouchEvent(false);
                }else {
                    scrollView.requestDisallowInterceptTouchEvent(true);
                }
                int count = (int) (deltaX/Offset);
                startPosition = Math.min(Math.max(0, startPDown-count), valuesAll.length-numCOunt);
                endPosition = startPosition + numCOunt;

                if (startPosition == lastStartP)
                    return;

                float[] arrayTemp = Arrays.copyOfRange(valuesAll, startPosition, endPosition);
                kLineData.kLineValues = arrayTemp;
                kLineView.refreshAllData();
                kLineView.invalidate();

                lastStartP = startPosition;
            }

            @Override
            public void scrollStart() {
                Log.i("111222", "scrollStart");
                startPDown = startPosition;
                lastStartP = startPosition;

                if (startPosition == 0){

                }else {
                    viewPager.requestDisallowInterceptTouchEvent(true);
                }

                scrollView.requestDisallowInterceptTouchEvent(true);
            }

            @Override
            public void scrollEnd() {

            }
        };

        indexView.indexLineEnable = true;
        indexView.indexSpaceCount = 240;
        indexView.xEndPosition = 240;
        indexView.yHeight = 120;
        float[] yValues = new float[120];
        for (int i=0; i< 120; i++){
            yValues[i] = i;
        }
        indexView.yValues = yValues;

        indexView.yMax = 120;
        indexView.yMin = 0;
        indexView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                    Log.i("111222", "indexView.setOnClickListener");
            }
        });

        indexView.leftValueListenr = new IndexView.TextValueListener() {
            @Override
            public String getTextValue(int xPosition) {
//                return String.valueOf(100 * (1-yRate));

                if (xPosition < yValues.length)
                    return String.valueOf(yValues[xPosition]);
                return "--";
            }
        };

        indexView.rightValueListenr = new IndexView.TextValueListener() {
            @Override
            public String getTextValue(int xPosition) {
                return String.valueOf((xPosition));
            }
        };

        indexView.XValueListenr = new IndexView.TextValueListener() {
            @Override
            public String getTextValue(int xPosition) {
                return String.valueOf(xPosition);
            }
        };
    }
}
