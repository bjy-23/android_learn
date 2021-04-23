package com.example.test_k_line;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    public static int colorRed = Color.parseColor("#EE3F3C");
    public static int  colorGreen = Color.parseColor("#00A846");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        {
            KLineView kLineView = findViewById(R.id.kline);
            KLineData kLineData = new KLineData();
            kLineData.kLineColor = colorRed;
            kLineData.shadowEnable = true;
            kLineData.kLineValues = new float[]{100, 160, 210, 120, 330, 440, 170, 100, 90, 50, 333, 222, 111, 180};
//            kLineData.kLineValues = new float[]{50, 100, 125, 200};
//            kLineView.addKLineData(kLineData);

            //蜡烛图
//            CandleData candleData = new CandleData();
//            getTestCandleData(kLineData.kLineValues);
//            List<CandleData.Item> list = new ArrayList<>();
//            CandleData.Item item1 = new CandleData.Item(53, 46, 58, 43);
//            list.add(item1);
//            CandleData.Item item2 = new CandleData.Item(73, 56, 88, 53);
//            list.add(item2);
//            CandleData.Item item3 = new CandleData.Item(180, 189, 205, 180);
//            list.add(item3);
//            candleData.itemList = list;

            CandleData candleData = getTestCandleData(kLineData.kLineValues);
            candleData.xSpaceCount = candleData.itemList.size()-1;
            candleData.lineWidth = dp2px(2f);
            candleData.maxCandleWidth = dp2px(5);
            candleData.candleWidthRate = 0.6f;
            kLineView.addCandleData(candleData);


            kLineView.requestLayout();
        }


//        {
//            KLineView kLineView = findViewById(R.id.kline2);
//            KLineData kLineData = new KLineData();
//            kLineData.kLineValues = getTestData(1,241, 300);
//            kLineData.xSpaceCount = 240;
//            kLineData.kLineColor = colorRed;
//            kLineData.kLineWidth = dp2px(1);
//            kLineView.addKLineData(kLineData);
//
//            KLineData kLineData2 = new KLineData();
//            kLineData2.kLineValues = new float[]{50, 250, 150, 300, 200, 250};
//            kLineData2.kLineColor = colorGreen;
//            kLineData2.kLineWidth = dp2px(2);
//            kLineView.addKLineData(kLineData2);
//        }


//        {
//            DoubleKLineView klineDetail = findViewById(R.id.klineDetail);
//            KLineData kLineDataLeft = new KLineData();
//            kLineDataLeft.kLineValues = getTestData(1, 241, 200);
//            logArray(kLineDataLeft.kLineValues);
//            kLineDataLeft.shadowEnable = true;
//            kLineDataLeft.xSpaceCount = 20;
//            kLineDataLeft.kLineColor = colorRed;
//            kLineDataLeft.kLineWidth = dp2px(1);
//            klineDetail.setkLineDataLeft(kLineDataLeft);
//
//            KLineData kLineDataRight = new KLineData();
////            kLineDataRight.kLineValues = getTestData(2, 241, 150);
//            kLineDataRight.kLineValues = new float[]{-10, 10, 0, 5, 0};
//            logArray(kLineDataRight.kLineValues);
//            kLineDataRight.xSpaceCount = 20;
//            kLineDataRight.xSpaceCount = kLineDataRight.kLineValues.length-1;
//            kLineDataRight.kLineColor = Color.BLACK;
//            kLineDataRight.kLineWidth = dp2px(1);
//            klineDetail.setkLineDataRight(kLineDataRight);
//
//            klineDetail.setTagName1("主力净流入");
//            klineDetail.setTagName2("板块指数");
//            klineDetail.setNameLeft("板块指数");
//            klineDetail.setNameRight("今日资金净流入(亿元)");
//
//            klineDetail.refresh();
//        }

    }

    private int dp2px(float dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
    }

    private float[] getTestData(int seed, int count, int maxValue){
        Random random = new Random(seed);

        float[] array = new float[count];
        for (int i=0; i<count; i++){
            array[i] = random.nextFloat() * maxValue;
        }

        return array;
    }

    private CandleData getTestCandleData(float[] array){
        CandleData candleData = new CandleData();
        List<CandleData.Item> list = new ArrayList<>();
        Random random = new Random();
        for (int i=0; i<array.length; i++){
            float value = array[i];
            if (random.nextFloat() > 0.5f){
                list.add(new CandleData.Item(value-20, value+30, value + 70, value-40));
            }else {
                list.add(new CandleData.Item(value+30, value-20, value + 70, value-40));
            }
        }

        candleData.itemList = list;

        return candleData;
    }

    private void logArray(float[] array){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[ ");
        for (int i =0; i<array.length; i++){
            stringBuilder.append(array[i]).append(", ");
        }
        stringBuilder.append(" ]");
        Log.i("111222", stringBuilder.toString());
    }
}