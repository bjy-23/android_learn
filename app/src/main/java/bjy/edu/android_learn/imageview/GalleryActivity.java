package bjy.edu.android_learn.imageview;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import bjy.edu.android_learn.R;

public class GalleryActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private String path;

    private static final String TAG = "111222";

    //intent 参数
    public static final String INTENT_IMG_PATH = "intent_img_path";
    public static final String INTENT_IMG_POSITION = "intent_img_position";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        viewPager = findViewById(R.id.viewPager);

        String path = getIntent().getStringExtra(INTENT_IMG_PATH);
        File dir = new File(path);
        File[] files = null;
        if (dir.exists()){
            files = dir.listFiles();
        }
        if (files == null){
            Log.i(TAG, "files == null");
            return;
        }

        viewPager.setAdapter(new GalleryAdapter(GalleryActivity.this, Arrays.asList(files)));
        //设置选中的图片
        int position = getIntent().getIntExtra(INTENT_IMG_POSITION, 0);
        viewPager.setCurrentItem(position);

        //asset目录下需要将文件copy出来
//        ThreadPoolUtil.run(new Runnable() {
//            @Override
//            public void run() {
//                File dir = GalleryActivity.this.getExternalFilesDir("asset");
//                File file = new File(dir, "哈哈.zip");
//                FileUtil.copyAssetFile(GalleryActivity.this, "哈哈.zip", file);
//
//                //解压
//                long time_start = System.currentTimeMillis();
//                File dirTarget = new File(dir, "哈哈");
//                if (!dirTarget.exists())
//                    dirTarget.mkdirs();
//                ZipFile zipFile = null;
//                try {
//                    zipFile = new ZipFile(file);
//                    Enumeration<?> entries = zipFile.entries();
//                    while (entries.hasMoreElements()){
//                        ZipEntry zipEntry = (ZipEntry) entries.nextElement();
//                        Log.i("111222", "解压：" + zipEntry.getName());
//                        if (zipEntry.isDirectory()){
//
//                        }else {
//                            File targetFile = new File(dirTarget, zipEntry.getName());
//                            //保证父文件夹一定存在
//                            if (!targetFile.getParentFile().exists()){
//                                targetFile.getParentFile().mkdirs();
//                            }
//                            targetFile.createNewFile();
//                            int count = -1;
//                            byte[] bytes = new byte[1024];
//                            InputStream inputStream = zipFile.getInputStream(zipEntry);
//                            FileOutputStream fileOutputStream = new FileOutputStream(targetFile);
//                            while ((count = inputStream.read(bytes)) != -1){
//                                fileOutputStream.write(bytes, 0, count);
//                            }
//                            fileOutputStream.close();
//                            inputStream.close();
//                        }
//                    }
//
//                    Log.i("111222", "解压耗时：" + (System.currentTimeMillis() - time_start) + " ms");
//
//                    final File[] files = dirTarget.listFiles();
//                    GalleryActivity.this.runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//
//                        }
//                    });
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }finally {
//                    if (zipFile != null){
//                        try {
//                            zipFile.close();
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }
//            }
//        });
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        Log.i(TAG, "action " + event.getAction() + " dispatchTouchEvent activity");
        return super.dispatchTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.i(TAG, "action " + event.getAction() + " onTouchEvent activity");
        return super.onTouchEvent(event);
    }

    class GalleryAdapter extends PagerAdapter{
        private Context context;
        private List<File> data;

        public GalleryAdapter(Context context, List<File> data) {
            this.context = context;
            this.data = data;
        }

        @Override
        public int getCount() {
            if (data != null)
                return data.size();
            return 0;
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
            return view == o;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            View view = View.inflate(context, R.layout.gallery_show, null);
            ImageView img_item = view.findViewById(R.id.img_item);
            try {
                img_item.setImageBitmap(BitmapFactory.decodeFile(data.get(position).getAbsolutePath()));
            } catch (Exception e) {
                e.printStackTrace();
            }
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);
        }
    }
}