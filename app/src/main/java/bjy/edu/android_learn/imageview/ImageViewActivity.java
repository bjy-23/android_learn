package bjy.edu.android_learn.imageview;

import android.Manifest;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import java.io.File;

import bjy.edu.android_learn.MainActivity;
import bjy.edu.android_learn.R;
import bjy.edu.android_learn.http.HttpHelper;

public class ImageViewActivity extends AppCompatActivity {
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_view);

        imageView = findViewById(R.id.image_view);

        //scaleType;
//        test_1();

        //setSrc
//        test_2();

        //获得手机相册中的所有图片
        test_3();

    }

    public void test_1(){
        imageView.setImageDrawable(getDrawable(R.drawable.tu));
        imageView.setScaleType(ImageView.ScaleType.MATRIX);
        Matrix matrix = new Matrix();
        matrix.setTranslate(100, 100);
        matrix.preRotate(30);
        imageView.setImageMatrix(matrix);
    }

    public void test_2(){
        //color
//        imageView.setImageResource(R.color.colorPrimary);

        //color -> colorDrawable
//        imageView.setImageDrawable(new ColorDrawable(getResources().getColor(R.color.statusBarColor)));
        imageView.setImageResource(0);
    }

    private void test_3(){

        if (ContextCompat.checkSelfPermission(ImageViewActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(ImageViewActivity.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED){
            Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
            ContentResolver contentResolver = ImageViewActivity.this.getContentResolver();
            final Cursor cursor = contentResolver.query(uri, null, null, null, null);
            if (cursor == null || cursor.getCount() <= 0) // 无相片
                return;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (cursor.moveToNext()){
                        int index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                        String path = cursor.getString(index);
                        File file = new File(path);
                        if (file.exists()){
                            Bitmap bitmap = BitmapFactory.decodeFile(path);
                            if (bitmap !=null)
                                Log.i("bitmap", bitmap.getByteCount()/1024/1024 +"M");
                        }
                    }
                }
            }).start();

        }else {
            ActivityCompat.requestPermissions(ImageViewActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, 1);
        }
    }
}
