package bjy.edu.android_learn.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import java.security.Key;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bjy.edu.android_learn.App;


/**
 *  SharedPreferences 工具
 *
 * Created by sogubaby on 2018/6/11.
 */

public class SpUtil {
    // TODO: 2018/7/26 map需要保存到本地
    private static SharedPreferences sp;
    public static Map<String, Class> map = new HashMap<>();//保存key对应的class
    public static final String NAME = "sharedPreference";
    public static final String KEY = "key";

    public static final String TAG_LIST_ITEM = "list_item";
    public static final String TAG_OBJECT = "&O";

    //初始化，取出之前保存的所有类型信息
    public static void init(Context context) {
        if (context == null)
            throw new NullPointerException("context should not be null");

        sp = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        String keyValues = sp.getString(KEY, "");

        //获取类型信息
        for (String keyValue : keyValues.split("&&")) {
            if (keyValue.equals(""))
                continue;
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


    /** key相同时，sharedPreference会覆盖之前的值
     * @param key
     * @param value
     * @param <T>
     * @return
     */
    public static <T> Boolean put(String key, T value) {
        if (sp == null)
            throw new NullPointerException("SpUtil should not be null");
        Gson gson = new Gson();
        SharedPreferences.Editor editor = sp.edit();
        String keyValue = sp.getString(KEY, "");
        if (value instanceof List){
            List list = (List) value;
            StringBuilder stringBuilder = new StringBuilder();
//            stringBuilder.append(TAG_LIST_START);
            for (int i=0; i<list.size(); i++){
                stringBuilder.append(TAG_OBJECT).append(gson.toJson(list.get(i)));
            }
            editor.putString(key, stringBuilder.toString());
            // TODO: 2018/12/23 list为空怎么获取class
            map.put(key+TAG_LIST_ITEM, list.get(0).getClass());

            //已经包含了相同的key,移除之前的类型信息
            if (keyValue.contains("&&"+key+TAG_LIST_ITEM)){
                int start = keyValue.indexOf("&&"+key+TAG_LIST_ITEM);
                int end = keyValue.indexOf("&&", start + 2);
                if (end == -1)
                    end = keyValue.length();
                String replaced = keyValue.substring(start, end);
                keyValue = keyValue.replace(replaced, "");
            }
            //保存类信息到本地
            keyValue += "&&" + key+TAG_LIST_ITEM + "#" + list.get(0).getClass().getName();
            editor.putString(KEY, keyValue);
            editor.commit();
        }else {
            String s = gson.toJson(value);
            editor.putString(key, s);
        }

        map.put(key, value.getClass());

        //已经包含了相同的key,移除之前的类型信息
        if (keyValue.contains("&&"+key)){
            int start = keyValue.indexOf("&&"+key);
            int end = keyValue.indexOf("&&", start + 2);
            if (end == -1)
                end = keyValue.length();
            String replaced = keyValue.substring(start, end);
            keyValue = keyValue.replace(replaced, "");
        }
        //保存类信息到本地
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


        if (map.get(key) == ArrayList.class){
            List list = new ArrayList();
            Class c = map.get(key+TAG_LIST_ITEM);
            String[] a = s.split(TAG_OBJECT);
            for (String item: s.split(TAG_OBJECT)){
                if (item.equals(""))
                    continue;
                list.add(gson.fromJson(item, c));
            }

            return (T) list;
        }

        // TODO: 2018/6/14 判空
        if (gson.fromJson(s, map.get(key)) != null)
            return (T) gson.fromJson(s, map.get(key));
        else
            return null;
    }
}
