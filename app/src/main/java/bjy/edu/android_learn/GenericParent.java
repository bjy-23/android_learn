package bjy.edu.android_learn;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;

/**
 * Created by sogubaby on 2018/7/12.
 */

public class GenericParent<T, V> {
    public Class<T> entityClass;

    public GenericParent(){
        TypeVariable[] tValue = GenericParent.class.getTypeParameters();
        System.out.println(tValue[0].getName());
        System.out.println(getClass().getName());
        Type t = getClass().getGenericSuperclass();
        System.out.println(t);
        System.out.println(t.getClass().getName());
        Type[] types = ((ParameterizedType)t).getActualTypeArguments();
//        entityClass = (Class<T>)types[0];
        System.out.println(types[0]);
    }
}
