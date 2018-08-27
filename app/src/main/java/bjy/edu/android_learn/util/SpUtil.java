package bjy.edu.android_learn.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import java.security.Key;
import java.util.HashMap;
import java.util.Map;

import bjy.edu.android_learn.App;


/**
 * Created by sogubaby on 2018/6/11.
 */

public class SpUtil {
    // TODO: 2018/7/26 map需要保存
    public static Map<String, Class> map = new HashMap<>();//保存key对应的class
    public static final String NAME = "sharedPreference";
    public static final String KEY = "key";

    //初始化，取出之前保存的所有类型信息
    public static void init(Context context){
        SharedPreferences sp = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        String keyValues = sp.getString(KEY, "");
        for (String keyValue : keyValues.split("&&")){
            String[] key = keyValue.split("#");
            if (key.length == 2){
                Class clazz = null;
                try {
                    clazz = Class.forName(key[1]);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                    continue;
                }
                map.put(key[0], clazz);
            }
        }
    }

    public static <T> Boolean put(String key, T value){
        SharedPreferences sp = App.getInstance().getSharedPreferences(NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        Gson gson = new Gson();
        String s = gson.toJson(value);
        map.put(key, value.getClass());
        editor.putString(key, s);

        //保存类信息
        String keyValue = sp.getString(KEY, "");
        keyValue += "&&"+key+"#"+value.getClass().getName();
        editor.putString(KEY, keyValue);
        return editor.commit();
    }

    public static <T> T get(String key){
        if (map.get(key) == null)
            return null;

        SharedPreferences sp = App.getInstance().getSharedPreferences(NAME, Context.MODE_PRIVATE);
        String s = sp.getString(key, null);
        Gson gson = new Gson();

        // TODO: 2018/6/14 判空
        if (gson.fromJson(s , map.get(key)) != null)
            return (T)gson.fromJson(s , map.get(key));
        else
            return null;
    }
}
