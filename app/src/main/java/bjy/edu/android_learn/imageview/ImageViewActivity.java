package bjy.edu.android_learn.imageview;

import android.Manifest;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.io.File;

import bjy.edu.android_learn.App;
import bjy.edu.android_learn.R;
import io.reactivex.functions.Consumer;

public class ImageViewActivity extends AppCompatActivity {
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_view);

        imageView = findViewById(R.id.image_view);

        int position = 6;
        switch (position){
            case 6:
                //
                test_6();
                break;
            case 5:
                //图片选取和裁剪
                test_5();
                break;
            case 4:
                //图片手势
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

    private void test_5(){
        setContentView(R.layout.activity_image_view_5);

        TextView tv_ok = findViewById(R.id.tv_ok);
        final ExpandleImageView2 img = findViewById(R.id.img);
//        final View v_crop = findViewById(R.id.v_crop);

        tv_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: 2019-10-09 图片裁剪
//                Drawable drawable = img.getDrawable();
//                Bitmap bitmap0 = Bitmap.createBitmap(img.getWidth(), img.getHeight(), Bitmap.Config.ARGB_8888);
//                Canvas canvas0 = new Canvas(bitmap0);
//                drawable.draw(canvas0);
//
//                int x = img.getWidth()/2 - (int)dp2px(100);
//                int y = img.getHeight()/2 - (int)dp2px(100);
//                Bitmap bitmap1 = Bitmap.createBitmap(bitmap0, x, y, (int)dp2px(200), (int) dp2px(200));
//
//                Bitmap bitmap2 = Bitmap.createBitmap((int)dp2px(200), (int)dp2px(200), Bitmap.Config.ARGB_8888);
//                Canvas canvas1 = new Canvas(bitmap2);
//                Paint paint = new Paint();
//                paint.setColor(Color.GREEN);
//                paint.setAntiAlias(true);
//                paint.setStyle(Paint.Style.FILL);
//
//                canvas1.drawBitmap(bitmap1, 0, 0, paint);
//
//                paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
//                canvas1.drawCircle(dp2px(200)/2, dp2px(200)/2, dp2px(100), paint);

                Bitmap bitmap = Bitmap.createBitmap(600, 600, Bitmap.Config.ARGB_8888);
                Paint paint = new Paint();
                paint.setColor(Color.GREEN);
                Canvas canvas = new Canvas(bitmap);
                canvas.drawCircle(300, 300, 200, paint);
                paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
                paint.setColor(Color.BLUE);
                canvas.drawRect(0, 0, 300, 300, paint);

                ImageView imageView1 = new ImageView(ImageViewActivity.this);
                imageView1.setImageBitmap(bitmap);

                new AlertDialog.Builder(ImageViewActivity.this)
                        .setView(imageView1)
                        .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .create()
                        .show();
        }
        });
        

        img.setBorderLeft(dp2px(50));
        img.setBorderRight(dp2px(50));
        img.setBorderTop(dp2px(150));
        img.setBorderBottom(dp2px(150));

        Uri uri = Uri.parse("content://media/external/images/media/43");
        Glide.with(this)
                .load(uri)
                .into(img);
    }

    private void test_6(){
        setContentView(R.layout.activity_image_view_6);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(ImageViewActivity.this, 3, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(new RecyclerView.Adapter() {
            private PorterDuff.Mode[] modes = PorterDuff.Mode.values();

            @NonNull
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                return new ViewHolderTemp(View.inflate(ImageViewActivity.this, R.layout.rv_porterduff_view, null));
            }

            @Override
            public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                ViewHolderTemp viewHolderTemp = (ViewHolderTemp) viewHolder;
                viewHolderTemp.porterDuffView.setMode(modes[i]);
                viewHolderTemp.tv_name.setText(modes[i].toString());
            }

            @Override
            public int getItemCount() {
                return modes.length;
            }
        });

    }

    class ViewHolderTemp extends RecyclerView.ViewHolder{
        private PorterDuffView porterDuffView;
        private TextView tv_name;

        public ViewHolderTemp(@NonNull View itemView) {
            super(itemView);

            porterDuffView = itemView.findViewById(R.id.porter_duff_view);
            tv_name = itemView.findViewById(R.id.tv_name);
        }
    }

    private void readPhoto2() {
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 1001);
    }

    private float dp2px(float dp){
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, App.getInstance().getResources().getDisplayMetrics());
    }
}
