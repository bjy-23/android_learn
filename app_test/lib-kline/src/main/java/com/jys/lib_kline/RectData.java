package com.jys.lib_kline;

import java.util.List;

public class RectData {
    Item item;

    public int drawColor;
    public int lineWidth;

    //只包含坐标信息
    public static class Item{
        float left;
        float top;
        float right;
        float bottom;
    }
}
