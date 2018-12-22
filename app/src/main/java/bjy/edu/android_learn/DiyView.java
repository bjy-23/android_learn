package bjy.edu.android_learn;

import android.content.Context;
import android.view.View;

import java.lang.reflect.Method;

public class DiyView extends View {
    public DiyView(Context context) {
        super(context);

//        invalidate();

    }

    public static void main(String[] args) {
        try {
            Class c = Class.forName("bjy.edu.android_learn.DiyView");
            Method[] methods =  c.getDeclaredMethods();
            Class cl = Class.forName("android.view.View");
            Method[] methods2 =  cl.getDeclaredMethods();
            System.out.println(methods2.length);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
