package bjy.edu.android_learn;

import org.junit.Test;

import java.lang.reflect.Field;

public class AlitaTest {
    public static void main(String[] args) {
        String valueS = "99p";
        try{
            int valueI = Integer.parseInt(valueS);
            System.out.println("valueI " + valueI);
        }catch (NumberFormatException e){
            System.out.println("exception " + e.getMessage());
        }
    }
}
