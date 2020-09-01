package bjy.edu.android_learn.util;

import java.lang.reflect.Field;

public class ReflectUtil {

    public static Object getFiled(Object o, String filedName){
        Object fieldObject = null;
        Class clazz = o.getClass();
        try {
            Field field = clazz.getDeclaredField(filedName);
            field.setAccessible(true);
            fieldObject = field.get(o);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }finally {
            return fieldObject;
        }
    }
}
