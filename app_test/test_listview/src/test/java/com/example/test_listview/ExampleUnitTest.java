package com.example.test_listview;

import org.junit.Test;

import static org.junit.Assert.*;

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
    public void test_1(){
        String s = "17083783508258726638";
        try{
            System.out.println("float: " +Long.parseLong(s));
        }catch (RuntimeException e){
            try {
                System.out.println(e.getCause().getMessage() + "");
            }catch (Exception e1){
                System.out.println("e1");
            }
        }
    }
}