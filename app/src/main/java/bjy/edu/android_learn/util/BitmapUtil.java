package bjy.edu.android_learn.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.util.Base64;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;

public class BitmapUtil {

    /** 把图片(jpg、png ...)压缩到指定像素
     * @param filePath
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    public static Bitmap decodeBitmap(String filePath, int reqWidth, int reqHeight){
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);

        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(filePath, options);
    }

    public static Bitmap decodeBitmap(InputStream inputStream, int reqWidth, int reqHeight){
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;

        Rect rect = new Rect(0, 0, reqWidth, reqHeight);
        Bitmap temp = BitmapFactory.decodeStream(inputStream);

        options.outWidth = temp.getWidth();
        options.outHeight = temp.getHeight();
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeStream(inputStream, null, options);
    }

    public Bitmap decodeSampledBitmapFromFileDescriptor(FileDescriptor fileDescriptor, int reqWidth, int reqHeight){
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFileDescriptor(fileDescriptor,null,options);

        options.inSampleSize = calculateInSampleSize(options,reqWidth,reqHeight);

        options.inJustDecodeBounds = false;

        return BitmapFactory.decodeFileDescriptor(fileDescriptor,null,options);
    }

    /**
     * 计算 bitmap 的采样率
     * @param options
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight){
        final int width  = options.outWidth;
        final int height = options.outHeight;
        int inSampleSize = 1;

        if(width > reqWidth || height > reqHeight){
            final int halWidth = width / 2;
            final int halHeight = height / 2;
            while ((halWidth / inSampleSize) >=reqWidth &&(halHeight / inSampleSize) >= reqHeight ){
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }

    /**
     * @param bitmap
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    public static int calculateInSampleSize(Bitmap bitmap, int reqWidth, int reqHeight){
        final int width  = bitmap.getWidth();
        final int height = bitmap.getHeight();
        int inSampleSize = 1;

        if(width > reqWidth || height > reqHeight){
            final int halWidth = width / 2;
            final int halHeight = height / 2;
            while ((halWidth / inSampleSize) >=reqWidth &&(halHeight / inSampleSize) >= reqHeight ){
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }

    public static File bitmapToFile(Context context, Bitmap bitmap){
        File dir = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){
            dir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
            if (!dir.exists()){
                if (!dir.mkdir()){
                    return null;
                }
            }
        }else {
            dir = Environment.getExternalStorageDirectory();
        }

        File file = new File(dir, System.currentTimeMillis() + ".jpg");
        try {
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(file));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bufferedOutputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return file;
    }
}
