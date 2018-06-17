package bjy.edu.android_learn;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sogubaby on 2018/6/14.
 */

public class Test {

    public static void main(String[] args) {
        Gson gson = new Gson();

        List<TestBean> list = new ArrayList<>();
        list.add(new TestBean("bjy", 22));
        System.out.println(gson.toJson(list));

        String json = "[{\"name\":\"bjy\",\"age\":22}]";
        List list1 = gson.fromJson(json, new TypeToken<ArrayList<TestBean>>(){}.getType());
    }
}
