package bjy.edu.android_learn.util;

import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by sogubaby on 2018/5/8.
 *
 * @see          DateFormatSymbols
 *  yyyy -> 2018    年
 *  MM -> 08    月
 *  dd -> 18    日
 *  HH -> 23    时（24进制：0~23）
 *  hh -> 11    时（12进制：1~12）
 *  mm -> 59    分
 *  ss -> 59    秒
 *  SSS -> 299  毫秒
 *  z -> GMT+08:00  时区
 *  E -> 周六     星期
 *  EEEE -> 星期六     星期
 */

public class DateUtil {

    public static String date2Str(Date date, String format){
        SimpleDateFormat sdf = new SimpleDateFormat(format);

        return sdf.format(date);
    }

    public static String str2Str(String input, String formatIn, String formatOut){
        SimpleDateFormat format1 = new SimpleDateFormat(formatIn);
        Date date = null;
        try {
            date = format1.parse(input);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat format2 = new SimpleDateFormat(formatOut);
        return format2.format(date);
    }

}
