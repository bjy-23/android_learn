package bjy.edu.android_learn;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONObject;
import org.junit.Test;

import java.lang.reflect.Type;

public class GsonTest {

    @Test
    public void test(){
        String s1 = "{\"age\" : 22 , \"name\" : \"bjy\"}";
        Gson gson = new Gson();
        Bean bean = null;

//        Bean bean = gson.fromJson(s1, Bean.class);
//        System.out.println("age: "+bean.getAge() + ", name: "+bean.getName());

        Type type = Bean.class;
        bean = gson.fromJson(s1, type);
        System.out.println("age: "+bean.getAge() + ", name: "+bean.getName());

//        String s2 = gson.fromJson(s1, String.class);
//        System.out.println(s2);
//
//        JsonObject jsonObject = new JsonParser().parse(s1).getAsJsonObject();
//        Bean bean2 = gson.fromJson(jsonObject, Bean.class);
//        System.out.println("age: "+bean.getAge() + ", name: "+bean2.getName());

        System.out.println(Integer.toHexString(261));
    }

    public class Bean{
        int age;
        String name;

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
