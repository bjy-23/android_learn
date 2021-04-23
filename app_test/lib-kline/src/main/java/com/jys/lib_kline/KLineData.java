package com.jys.lib_kline;

import android.graphics.Color;

//K线数据，数据参考方向以现实中的常用坐标系方向计算，不对照安卓本身的坐标系
public class KLineData {
    public float[] kLineValues; //k线数据
    public int kLineColor; // k线颜色
    public int kLineWidth; //k线宽度
    public int xSpaceCount;//x轴分割块数
    public int xStartPosition;//x轴起始绘制位置, 从0计数
    public float yValueMin;//y轴方向的数据计算起始位置，对应y轴起始位置
    public float yValueMax;//y轴方向的数据计算终点位置，对应y轴可绘制区域的最高位置
    public boolean shadowEnable; // 是否允许绘制阴影
    public float shadowAlpha; //阴影透明度
    public int shadowColorStart;
    public int shadowColorEnd; //默认透明
    public Point[] points;

    public static class Point{
        public float x;
        public float y;
    }
}
