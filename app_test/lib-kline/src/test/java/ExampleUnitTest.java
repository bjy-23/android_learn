

import android.util.Log;

import org.junit.Test;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.StringTokenizer;

import static org.junit.Assert.assertEquals;


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void test_(){
        float value = 3.13f;
        String valueString = "3.0";
        StringTokenizer stoken = new StringTokenizer(valueString, ".");
        while (stoken.hasMoreTokens()){
            System.out.println(stoken.nextToken());
        }
//        System.out.println(": " + Util.transPercentData(valueString, 4));
    }

    @Test
    public void testComparator(){
        List<Person> list = new ArrayList<>();
        list.add(new Person(19, "184.9"));
        list.add(new Person(14, "185"));
        list.add(new Person(13, "175"));

//        Collections.sort(list, new AgeComparator());
//        Collections.sort(list, new HeightComparator());

//        Collections.sort(list, new FieldComparator<Person>(0, "age"));
//        Collections.sort(list, new FieldComparator<Person>(1, "age"));
//        Collections.sort(list, new FieldComparator<Person>(0, "height"));
        Collections.sort(list, new FieldComparator<Person>(1, "height"));

        for (int i=0; i<list.size(); i++){
            System.out.println("list " + i + " : " + list.get(i).toString());
        }
    }

    public  class Person{
        int age;
        String height;

        public Person() {
        }

        public Person(int age) {
            this.age = age;
        }

        public Person(int age, String height) {
            this.age = age;
            this.height = height;
        }

        @Override
        public String toString() {
            return "Person{" +
                    "age=" + age +
                    ", height='" + height + '\'' +
                    '}';
        }
    }

    static  class AgeComparator implements Comparator<Person>{
        @Override
        public int compare(Person o1, Person o2) {
            return o1.age - o2.age;
        }
    }

    static  class HeightComparator implements Comparator<Person>{
        @Override
        public int compare(Person o1, Person o2) {
            return Float.compare(Float.parseFloat(o1.height) - Float.parseFloat(o2.height), 0f) ;
        }
    }

    static class FieldComparator<T> implements Comparator<T>{
        String fieldName;
        int state;

        public FieldComparator(int state, String fieldName) {
            this.state = state;
            this.fieldName = fieldName;
        }

        @Override
        public int compare(T o1, T o2) {
            Class c = o1.getClass();
            try {
                Field field = c.getDeclaredField(fieldName);
                field.setAccessible(true);

                Float f1 = Float.parseFloat( String.valueOf(field.get(o1)));
                Float f2 = Float.parseFloat( String.valueOf(field.get(o2)));

                if (state == 0){
                    return Float.compare(f1-f2, 0f);
                }else {
                    return Float.compare(f2-f1, 0f);
                }
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }

            return 0;
        }
    }


    @Test
    public void testFanxing(){
        List list = new ArrayList();
        list.add(111);
        list.add("222");

        for (int i=0; i<list.size(); i++){
            System.out.println(list.get(i));
        }
    }

    @Test
    public void test_1(){
        float[] f1 = new float[]{5,2,3};
        Arrays.sort(f1);
        System.out.println("end");
    }

    @Test
    public void test_2(){
        //科学计数法
//        String v1 = "9.87E8";
//        BigDecimal bigDecimal = new BigDecimal(v1);
//        System.out.println("v1 -> " + bigDecimal.toPlainString());
//
//        String v2 = "123456789987654321123456789";
//        System.out.println("v2 -> " + new BigDecimal(v2).multiply(new BigDecimal("10000")));

        //保留3位小数
//        String v1 = "3.1414926";
//        BigDecimal bigDecimal = new BigDecimal(v1);
//        System.out.println(bigDecimal.setScale(3, RoundingMode.HALF_UP).toPlainString());

        String s = "hello 你好 nihao";
        String[] a = s.split(" ");
        System.out.println(a.length);
    }
}