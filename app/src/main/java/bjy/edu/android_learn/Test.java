package bjy.edu.android_learn;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.orhanobut.hawk.Hawk;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by sogubaby on 2018/6/14.
 */

public class Test {

    public static void main(String[] args) {
        Gson gson = new Gson();

        List<TestBean> list = new ArrayList<>();
        list.add(new TestBean("bjy", 22));
        System.out.println(gson.toJson(list));

        Map<String, TestBean> map = new HashMap<>();
        map.put("test", new TestBean("dilireba", 22));
        System.out.println(gson.toJson(map));

        Set<TestBean> set = new HashSet<>();
        set.add(new TestBean("suolong", 21));
        System.out.println(gson.toJson(set));

        String json = "[{\"name\":\"bjy\",\"age\":22}]";
        List list1 = gson.fromJson(json, new TypeToken<ArrayList<TestBean>>(){}.getType());

    }
}
