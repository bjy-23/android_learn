package bjy.edu.android_learn;

import java.lang.reflect.ParameterizedType;

/**
 * Created by sogubaby on 2018/7/12.
 */

public class T1<T> {
    private Class classt;

    public T1() {
        ParameterizedType type = (ParameterizedType) this.getClass().getGenericSuperclass();
        this.classt = (Class) type.getActualTypeArguments()[0];
        System.out.println(this.classt);
    }
}
