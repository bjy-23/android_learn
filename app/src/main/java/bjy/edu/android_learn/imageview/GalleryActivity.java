package bjy.edu.android_learn.imageview;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import bjy.edu.android_learn.R;
import bjy.edu.android_learn.thread.ThreadPoolUtil;
import bjy.edu.android_learn.util.BitmapUtil;
import bjy.edu.android_learn.util.FileUtil;

public class GalleryActivity extends AppCompatActivity {
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        viewPager = findViewById(R.id.viewPager);

        //asset目录下需要将文件copy出来
        ThreadPoolUtil.run(new Runnable() {
            @Override
            public void run() {
                File dir = GalleryActivity.this.getExternalFilesDir("asset");
                File file = new File(dir, "哈哈.zip");
                FileUtil.copyAssetFile(GalleryActivity.this, "哈哈.zip", file);

                //解压
                long time_start = System.currentTimeMillis();
                File dirTarget = new File(dir, "哈哈");
                if (!dirTarget.exists())
                    dirTarget.mkdirs();
                ZipFile zipFile = null;
                try {
                    zipFile = new ZipFile(file);
                    Enumeration<?> entries = zipFile.entries();
                    while (entries.hasMoreElements()){
                        ZipEntry zipEntry = (ZipEntry) entries.nextElement();
                        Log.i("111222", "解压：" + zipEntry.getName());
                        if (zipEntry.isDirectory()){

                        }else {
                            File targetFile = new File(dirTarget, zipEntry.getName());
                            //保证父文件夹一定存在
                            if (!targetFile.getParentFile().exists()){
                                targetFile.getParentFile().mkdirs();
                            }
                            targetFile.createNewFile();
                            int count = -1;
                            byte[] bytes = new byte[1024];
                            InputStream inputStream = zipFile.getInputStream(zipEntry);
                            FileOutputStream fileOutputStream = new FileOutputStream(targetFile);
                            while ((count = inputStream.read(bytes)) != -1){
                                fileOutputStream.write(bytes, 0, count);
                            }
                            fileOutputStream.close();
                            inputStream.close();
                        }
                    }

                    Log.i("111222", "解压耗时：" + (System.currentTimeMillis() - time_start) + " ms");

                    final File[] files = dirTarget.listFiles();
                    GalleryActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            viewPager.setAdapter(new GalleryAdapter(GalleryActivity.this, Arrays.asList(files)));
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }finally {
                    if (zipFile != null){
                        try {
                            zipFile.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });


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