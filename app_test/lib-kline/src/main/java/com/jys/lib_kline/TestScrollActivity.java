package com.jys.lib_kline;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class TestScrollActivity extends AppCompatActivity {
    public static int colorRed = Color.parseColor("#EE3F3C");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_scroll);

        {
            KLineView kLineView = findViewById(R.id.kline);
            {
                KLineData kLineData = new KLineData();
                kLineData.kLineColor = colorRed;
//            kLineData.shadowEnable = true;
            kLineData.kLineValues = new float[]{100, 160, 210, 120, 330, 440, 170, 100, 90, 50, 333, 222, 111, 180};
//                kLineData.kLineValues = new float[]{55, 75, 175};
//            kLineData.xStart = 1;
//                kLineData.yValueMin = 0f;
//                kLineData.yValueMax = 300f;
                kLineView.indexSpaceCount = kLineData.kLineValues.length - 1;
                kLineView.addKLineData(kLineData);
                kLineView.indexLineEnable = true;

                //虚线
                kLineView.xBgLineEnable = true;
                kLineView.xBgLineArray = new float[]{0.25f, 0.5f, 0.75f};
                kLineView.yBgLineEnable = true;
                kLineView.yBgLineArray = new float[]{0f, 0.25f, 0.5f, 0.75f, 1f};
            }
        }
    }
}