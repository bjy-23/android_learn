package com.example.test_k_line;

import java.util.List;

//蜡烛图
public class CandleData {
    public List<Item> itemList;
    public int xSpaceCount;//x轴分割块数
    public float lineWidth;//线宽
    public float maxCandleWidth; //蜡烛图最大宽度
    public float candleWidthRate; //蜡烛图最大宽度比，和x轴的间距做比较

    public float minLimit;
    public float maxLimit;

    //单个蜡烛图的数据
    public static class Item{
        public float start;//开盘价
        public float end;//收盘价
        public float max; //最高价
        public float min;//最低价
        public int drawColor;//绘制颜色
        public float candleMaxWidth;// 蜡烛图矩形最大宽度
        public int candleWidthRate;//

        Point[] points; // 蜡烛图8个的位置信息
        public Item(float start, float end, float max, float min) {
            this.start = start;
            this.end = end;
            this.max = max;
            this.min = min;
        }
    }
}
