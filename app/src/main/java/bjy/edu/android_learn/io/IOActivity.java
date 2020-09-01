package bjy.edu.android_learn.io;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.RandomAccessFile;
import java.lang.reflect.Array;
import java.nio.charset.Charset;
import java.util.Arrays;

import bjy.edu.android_learn.R;

public class IOActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_io);

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED
            || ActivityCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(IOActivity.this, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE
                    , android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1000);
            return;
        }

        int position = 6;
        switch (position){
            case 6:
                test_6();
                break;
            case 3:
                test_3();
                position = 3;
                break;
        }
    }

    //从 assets目录读取文本(推荐做法): 字符流读取
    private void test_1() {
        try {
            // TODO: 2019-08-30 StreamDecoder源码查看
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(this.getAssets().open("lixi")));
            StringBuilder stringBuilder = new StringBuilder();
            String temp;
            while ((temp = bufferedReader.readLine()) != null) {
                stringBuilder.append(temp);
            }
            bufferedReader.close();

            System.out.println(stringBuilder.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //从 assets目录读取文本(其他实现方法) 字节流读取
    private void test_2() {
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
            while ((count = bis2.read(bytes)) != -1) {
                byteArrayOutputStream.write(bytes, 0, count);
            }
            bis2.close();
            System.out.println(new String(byteArrayOutputStream.toByteArray()));
            byteArrayOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 向文件里写数据 - 字节流
    private void test_3() {
        File file = new File(Environment.getExternalStorageDirectory(), "202000728.txt");

        String text = "柏建宇1229";
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(text.getBytes());

        try {
            FileOutputStream fos = new FileOutputStream(file);
            byte[] bytes = new byte[1024];
            int count = -1;
            while ((count = byteArrayInputStream.read(bytes)) != -1) {
                fos.write(bytes, 0, count);
            }

            byteArrayInputStream.close();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {

        }

    }

    // 向文件里写数据 - 字符流
    private void test_4() {
        File file = new File(Environment.getExternalStorageDirectory(), "20190131_2.txt");

        String text = "柏建宇1229_2";

        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(text.getBytes());
        InputStreamReader inputStreamReader = new InputStreamReader(byteArrayInputStream);
        try {
            FileWriter fileWriter = new FileWriter(file);
            char[] chars = new char[1024];
            int count = -1;
            while ((count = inputStreamReader.read(chars)) != -1){
                fileWriter.write(chars, 0, count);
            }
            fileWriter.close();
            inputStreamReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {

        }

    }

    private void test_5() {
        File file = new File(Environment.getExternalStorageDirectory(), "20190131_3.txt");

        String text = "柏建宇1229_3";

        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(text.getBytes());
        InputStreamReader inputStreamReader = new InputStreamReader(byteArrayInputStream);
        try {
            FileWriter fileWriter = new FileWriter(file);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            char[] chars = new char[1024];
            int count = -1;
            while ((count = inputStreamReader.read(chars)) != -1){
                bufferedWriter.write(chars, 0, count);
            }
            bufferedWriter.close();
            inputStreamReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {

        }

    }

    private void test_6(){
        File dir = getExternalFilesDir("test");
        File file = new File(dir, "test.txt");
        if (!file.exists()){
            System.out.println("file 不存在");
            try {
                file.createNewFile();
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                String text1 = "你好,";
                fileOutputStream.write(text1.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        File dir2 = getExternalFilesDir("test");
        File file2 = new File(dir2, "test.txt");

        if (!file2.exists()){
            System.out.println("file2 不存在");
            try {
                file2.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(file2, true);
            String text2 = "\n 柏建宇";
            fileOutputStream.write(text2.getBytes());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
