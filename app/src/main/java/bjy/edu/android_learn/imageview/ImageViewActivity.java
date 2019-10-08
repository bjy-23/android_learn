package bjy.edu.android_learn.imageview;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.io.File;

import bjy.edu.android_learn.MainActivity;
import bjy.edu.android_learn.R;
import bjy.edu.android_learn.http.HttpHelper;
import io.reactivex.functions.Consumer;

public class ImageViewActivity extends AppCompatActivity {
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_view);

        imageView = findViewById(R.id.image_view);

        int position = 4;
        switch (position){
            case 4:
                test_4();
                break;
        }
        //scaleType;
//        test_1();

        //setSrc
//        test_2();

        //获得手机相册中的所有图片
//        test_3();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1001){
            if (data == null){
                Log.i("1001", "读取图片失败");
                return;
            }

            Uri uri = data.getData();
            Log.i("uri", uri.toString());
//            imageView.setImageURI(uri);

            Glide.with(ImageViewActivity.this)
                    .load(uri)
                    .into(imageView);
        }
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

    private void test_4(){
        setContentView(R.layout.activity_image_view_4);
        imageView = findViewById(R.id.img);

        Uri uri = Uri.parse("content://media/external/images/media/43");
        Glide.with(this)
                .load(uri)
                .into(imageView);

        final RxPermissions rxPermissions = new RxPermissions(ImageViewActivity.this);
        TextView tv = findViewById(R.id.tv);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rxPermissions.requestEach(Manifest.permission.READ_EXTERNAL_STORAGE)
                        .subscribe(new Consumer<Permission>() {
                            @Override
                            public void accept(Permission permission) throws Exception {

                                if (permission.granted) {//获得权限
                                    readPhoto2();
                                } else if (permission.shouldShowRequestPermissionRationale) {//禁止权限

                                } else {// 禁止权限并不再访问

                                }

                            }
                        });
            }
        });


    }

    private void readPhoto2() {
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 1001);
    }
}
