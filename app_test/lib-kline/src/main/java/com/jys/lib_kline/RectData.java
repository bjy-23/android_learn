package com.jys.lib_kline;

public class RectData {
    Item item;

    public int drawColor;
    public float lineWidth;
    public int style = PillarData.Item.STYLE_DEFAULT;

    //只包含坐标信息
    public static class Item{
        float left;
        float top;
        float right;
        float bottom;
    }
}
