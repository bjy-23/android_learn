package com.example.x_test;

import android.content.Context;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileUtil {

    public static void writeSth(File file, String sth){
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(file.getAbsolutePath(), true);

            fileWriter.write("------------------------------------");
            fileWriter.write("\r\n");
            fileWriter.write(sth);
            fileWriter.write("\r\n");
            fileWriter.write("\r\n");

            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
