package bjy.edu.android_learn;

import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

/**
 * Thread.setDefaultUncaughtExceptionHandler();在Application中设置该CrashHandler为程序的默认处理
 *
 * 设置了之后，logcat中的部分异常可能无法查看，所以开发模式下无需设置
 *
 */
public class CrashHandler implements Thread.UncaughtExceptionHandler {
    private static final String TAG = CrashHandler.class.getSimpleName();

    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd_HH:mm:ss");

    @Override
    public void uncaughtException(Thread t, Throwable e){
        Log.i(TAG, e.getMessage());
        //android手机根目录下新建目录1229
        File dir = new File(Environment.getExternalStorageDirectory(), "/1229_crash");
        if (!dir.exists())
            dir.mkdir();
        File file = new File(dir, simpleDateFormat.format(new Date()) + "_crash.txt");

        Writer writer = new StringWriter();
        PrintWriter pw = new PrintWriter(writer);
        e.printStackTrace(pw);
        Throwable cause = e.getCause();
        // 循环着把所有的异常信息写入writer中
        while (cause != null) {
            cause.printStackTrace(pw);
            cause = cause.getCause();
        }
        pw.close();
        String result = writer.toString();

        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(result.getBytes());
            fileOutputStream.close();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(1);
    }
}
