package bjy.edu.android_learn.util;

import android.content.Context;
import android.graphics.drawable.Drawable;


import bjy.edu.android_learn.App;


public class ResUtil {


    static Context context = App.getInstance();

    /**
     * 获得color
     *
     * @param colorId
     * @return
     */
    public static int getColor(int colorId) {
        return context.getResources().getColor(colorId);
    }

    /**
     * 获取color值对应的string, eg: #ffffff
     *
     * @param colorId
     * @return
     */
    public static String getColorString(int colorId) {
        StringBuilder stringBuilder = new StringBuilder();
        int color = getColor(colorId);
        int red = (color & 0xff0000) >> 16;
        int green = (color & 0x00ff00) >> 8;
        int blue = (color & 0x0000ff);

        stringBuilder.append("#")
                .append(String.format("%02x", red))
                .append(String.format("%02x", green))
                .append(String.format("%02x", blue));

        return stringBuilder.toString();
    }

    /**
     * 获取尺寸
     *
     * @param resId
     * @return
     */
    public static Drawable getDrawable(int resId) {
        return context.getResources().getDrawable(resId);
    }

}
