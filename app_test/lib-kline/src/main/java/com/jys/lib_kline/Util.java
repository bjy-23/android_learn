package com.jys.lib_kline;

import android.widget.TextView;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Arrays;

public class Util {
    public static void setText(TextView tv, String text){
        if (tv != null){
            tv.setText(text);
        }
    }

    // 计算数组的最大值,最小值，不改变数组顺序
    public static float[] minMaxValue(float[] valueArray){
        if (valueArray == null || valueArray.length == 0)
            return null;

        float[] temp = Arrays.copyOf(valueArray, valueArray.length);
        Arrays.sort(temp);
        float[] min_max = new float[2];
        min_max[0] = temp[0];
        min_max[1] = temp[temp.length-1];
        return min_max;
    }


    public static String transFloat2(float value, int n){
        if (n < 0)
            return String.valueOf(value);
        StringBuilder stringBuilder = new StringBuilder("0.");
        for (int i=0; i<n; i++){
            stringBuilder.append("0");
        }
        DecimalFormat decimalFormat = new DecimalFormat(stringBuilder.toString());
        decimalFormat.setRoundingMode(RoundingMode.HALF_UP);
        return decimalFormat.format(value);
    }

    public static String transFloat(float value, int n){
        BigDecimal bigDecimal = new BigDecimal(String.valueOf(value));
        StringBuilder stringBuilder = new StringBuilder("0.");
        for (int i=0; i<n; i++){
            stringBuilder.append("0");
        }
        DecimalFormat decimalFormat = new DecimalFormat(stringBuilder.toString());
        decimalFormat.setRoundingMode(RoundingMode.HALF_UP);
        return decimalFormat.format(bigDecimal);
    }

    //求均值
    public static float getAvg(float[] array){
        float sum = getSum(array);

        return sum / array.length;
    }

    //求和
    public static float getSum(float[] array){
        float value= 0;
        for (int i=0; i<array.length; i++){
            value += array[i];
        }

        return value;
    }
}
