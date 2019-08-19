package bjy.edu.android_learn.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class BJYProxy implements InvocationHandler {
    private Object real;

    public BJYProxy(Object real){
        this.real = real;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("代理method:   " + method.getName());
        method.invoke(real, args);
        return null;
    }
}
