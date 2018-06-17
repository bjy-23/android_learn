package bjy.edu.android_learn.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import bjy.edu.android_learn.App;


/**
 * Created by sogubaby on 2018/6/11.
 */

public class SpUtil {
    public static Map<String, Class> map = new HashMap<>();//保存key对应的class

    public static <T> Boolean put(String key, T value){
        SharedPreferences sp = App.getInstance().getSharedPreferences("11", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        Gson gson = new Gson();
        String s = gson.toJson(value);
        map.put(key, value.getClass());
        editor.putString(key, s);

        return editor.commit();
    }

    public static <T> T get(String key){
        SharedPreferences sp = App.getInstance().getSharedPreferences("11", Context.MODE_PRIVATE);
        String s = sp.getString(key, null);
        Gson gson = new Gson();

        // TODO: 2018/6/14 判空 
        return (T)gson.fromJson(s , map.get(key));
    }
}
