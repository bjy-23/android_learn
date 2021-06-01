package com.example.x_test;

import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;

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
    public void test1(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("M/dd");

        String date1 = "20210226150159231";
        SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        try {
            String time = simpleDateFormat.format(simpleDateFormat1.parse(date1));
            System.out.println("time: " + time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}