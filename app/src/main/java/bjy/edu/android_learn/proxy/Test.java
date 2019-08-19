package bjy.edu.android_learn.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class Test {

    public static void main(String[] args) {
        final BJY bjy = new BJY();
        BJYProxy bjyProxy = new BJYProxy(bjy);

        Man man = (Man) Proxy.newProxyInstance(bjy.getClass().getClassLoader(), bjy.getClass().getInterfaces(), bjyProxy);
        man.work();
//        man.love();

        Man man2 = (Man) Proxy.newProxyInstance(bjy.getClass().getClassLoader(), bjy.getClass().getInterfaces(), new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                method.invoke(bjy, args);
                System.out.println("bjy 失恋了");
                return null;
            }
        });
        man2.love();
    }
}
