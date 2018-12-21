package bjy.edu.android_learn.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import java.security.Key;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bjy.edu.android_learn.App;


/**
 * Created by sogubaby on 2018/6/11.
 */

public class SpUtil {
    // TODO: 2018/7/26 map需要保存到本地
    private static SharedPreferences sp;
    public static Map<String, Class> map = new HashMap<>();//保存key对应的class
    public static final String NAME = "sharedPreference";
    public static final String KEY = "key";

    //初始化，取出之前保存的所有类型信息
    public static void init(Context context) {
        if (context == null)
            throw new NullPointerException("context should not be null");

        sp = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        String keyValues = sp.getString(KEY, "");

        //获取类型信息
        for (String keyValue : keyValues.split("&&")) {
            String[] key = keyValue.split("#");
            if (key.length == 2) {
                Class clazz = null;
                try {
                    clazz = Class.forName(key[1]);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                    continue;
                }
                if (clazz != null)
                    map.put(key[0], clazz);
            }
        }
    }

    public static <T> Boolean put(String key, T value) {
        if (sp == null)
            throw new NullPointerException("SpUtil should not be null");
        Gson gson = new Gson();
        if (value instanceof List){
            List list = (List) value;

            Object o = list.get(0);
            System.out.println(o.toString());


        }
        SharedPreferences.Editor editor = sp.edit();

        String s = gson.toJson(value);
        // todo 相同的key, 类型信息会被覆盖
        map.put(key, value.getClass());
        editor.putString(key, s);

        //保存类信息到本地
        String keyValue = sp.getString(KEY, "");
        keyValue += "&&" + key + "#" + value.getClass().getName();
        editor.putString(KEY, keyValue);
        return editor.commit();
    }

    public static <T> T get(String key) {
        if (sp == null)
            throw new NullPointerException("SpUtil should not be null");

        if (map.get(key) == null)
            return null;

        String s = sp.getString(key, null);
        Gson gson = new Gson();

        // TODO: 2018/6/14 判空
        if (gson.fromJson(s, map.get(key)) != null)
            return (T) gson.fromJson(s, map.get(key));
        else
            return null;
    }
}
