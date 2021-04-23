package com.example.test_k_line;

import android.util.TypedValue;

//K线数据
public class KLineData {
    public float[] kLineValues; //k线数据,只传y轴数据
    public Point[] points; //k线数据

    public int kLineColor; // k线颜色
    public int kLineWidth; //k线宽度
    public int xSpaceCount;//x轴分割块数
    public boolean shadowEnable; // 是否允许绘制阴影
    public float shadowAlpha; //阴影透明度
    public int shadowColorStart;
    public int shadowColorEnd; //默认透明

    public static class Point{
        public float x;
        public float y;
    }
}