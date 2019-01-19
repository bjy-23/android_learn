package bjy.edu.android_learn.io;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import bjy.edu.android_learn.R;

public class IOActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_io);

        test_1();

        test_2();
    }

    //从 assets目录读取文本(推荐做法)
    private void test_1(){
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(this.getAssets().open("lixi")));
            StringBuilder stringBuilder = new StringBuilder();
            String temp;
            while ((temp = bufferedReader.readLine()) != null){
                stringBuilder.append(temp);
            }
            bufferedReader.close();

            System.out.println(stringBuilder.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //从 assets目录读取文本(其他实现方法)
    private void test_2(){
        //数据存到 byte[]
        try {
            BufferedInputStream bis = new BufferedInputStream(this.getAssets().open("lixi"));
            byte[] bytes = new byte[bis.available()];
            bis.read(bytes);
            bis.close();

            System.out.println(new String(bytes));
        } catch (IOException e) {
            e.printStackTrace();
        }

        //保存到 ByteArrayOutputStream
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            BufferedInputStream bis2 = new BufferedInputStream(this.getAssets().open("lixi"));
            byte[] bytes = new byte[1024];
            int count;
            while ((count = bis2.read(bytes)) != -1){
                byteArrayOutputStream.write(bytes, 0, count);
            }
            bis2.close();
            System.out.println(new String(byteArrayOutputStream.toByteArray()));
            byteArrayOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
