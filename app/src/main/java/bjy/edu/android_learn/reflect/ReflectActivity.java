package bjy.edu.android_learn.reflect;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import bjy.edu.android_learn.R;

public class ReflectActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reflect);

        //反射Constructor
        Class clazz = Person.class;
        Class clazz2 = Woman.class;

        //普通内部类的构造方法中会包含一个外部类参数
        //获取所有非私有方法
        Constructor[] constructors = clazz.getConstructors();
        Constructor constructor = constructors[0];

        //获得所有方法
        Constructor[] constructors2 = clazz.getDeclaredConstructors();
        Constructor constructor2 = constructors2[0];
        try {

            // Class 的 newInstance方法只适合 无参的且可以访问的构造函数
//            Object o = clazz.newInstance();
            Object oo = clazz2.newInstance();

            //可访问的构造函数
            Object object = constructor.newInstance(this, "bbbjy");
            //不可可访问的构造函数
            constructor2.setAccessible(true);
            Object object2 = constructor2.newInstance(this);

        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        //反射 Method
        try {
            //可访问的方法
            Method getName = clazz2.getMethod("getName");
            getName.invoke(new Woman());

            //不可访问的方法
            Method getAge = clazz2.getDeclaredMethod("getAge");
            getAge.setAccessible(true);
            getAge.invoke(new Woman());
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        // 反射 Field
        try {
            Field name = clazz2.getField("name");
            Object nameString = name.get(new Woman("迪丽热巴"));

            Field age = clazz2.getDeclaredField("age");
            age.setAccessible(true);
            Object ageInt = age.getInt(new Woman(26));

            Log.i("", "");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        System.out.println("see you");
    }

    class Person {
        private String name;
        private int age;

        private Person() {

        }

        protected Person(String name) {
            this.name = name;
        }

        public Person(String name, int age) {
            this.name = name;
            this.age = age;
        }
    }

    static class Woman {
        private int age;
        public String name;

        public Woman() {
        }

        public Woman(int age) {
            this.age = age;
        }

        public Woman(String name) {
            this.name = name;
        }

        public void getName() {
            System.out.println("dilireba");
        }

        private void getAge(){
            System.out.println("26");
        }
    }
}
