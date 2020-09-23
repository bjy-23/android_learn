package bjy.edu.android_learn;

import android.util.Log;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;

/**
 * Created by sogubaby on 2018/7/12.
 */

public class T1<T> {

    public T1() {
    }

    public static void getParameterType(Object o){
        Class clazz = o.getClass();
        Type type = clazz.getGenericSuperclass();
        System.out.println("type: " + type.toString());
        TypeVariable[] typeVariables = clazz.getTypeParameters();
        for (TypeVariable typeVariable : typeVariables){
            System.out.println("typeVariable: " + typeVariable.toString());
        }
        if (ParameterizedType.class.isAssignableFrom(clazz)){
            ParameterizedType parameterizedType = (ParameterizedType) type;
            Type[] typeArray = parameterizedType.getActualTypeArguments();
            for (Type t : typeArray){
                System.out.println("getActualTypeArguments: " + t.toString());
            }
        }else {
            System.out.println("ParameterizedType isAssignableFrom false");
        }

    }
}
