package com.jys.lib_kline;

import java.util.List;

public class PillarData {
    public List<Item> list;
    public int xSpaceCount;//x轴分割块数
    public int xSpaceOffset;//x轴绘制的偏移位置
    public float pillarWidthRate;
    public float pillarWidthMax;
    public float yValueMin;//y轴方向的数据计算起始位置，对应y轴起始位置
    public float yValueMax;//y轴方向的数据计算终点位置，对应y轴可绘制区域的最高位置

    public static class Item {
        public float top;
        public float bottom;

        public int color;
    }
}
