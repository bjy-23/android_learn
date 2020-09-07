package bjy.edu.android_learn.imageview;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import bjy.edu.android_learn.R;
import bjy.edu.android_learn.util.BitmapUtil;
import bjy.edu.android_learn.util.DisplayUtil;

public class GalleryPreviewActivity extends AppCompatActivity {
    private static final String TAG = "111222";
    private RecyclerView recyclerView;
    private int previewSize;//预览图片的宽高
    private int count = 3; // 一行展示

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery_preview);

        File dir = getExternalFilesDir("Pictures/美图");
        File[] files = dir.listFiles();
        if (files == null || files.length == 0){
            Log.i(TAG, "文件目录下无图片");
            return;
        }
        Log.i(TAG, "文件个数： " + files.length);
        //获取屏幕宽高
        int[] array = DisplayUtil.getScreenWidthHeight(this);
        previewSize = array[0] / count;
        Log.i(TAG, "[预览图片的宽高 previewSize] " + previewSize);

        recyclerView = findViewById(R.id.recyclerView);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(GalleryPreviewActivity.this, count, LinearLayout.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        GalleryPreviewAdapter adapter = new GalleryPreviewAdapter(this, Arrays.asList(files));
        recyclerView.setAdapter(adapter);
    }

    class GalleryPreviewAdapter extends RecyclerView.Adapter{
        private Context context;
        private List<File> data;

        public GalleryPreviewAdapter(Context context, List<File> data) {
            this.context = context;
            this.data = data;
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = View.inflate(context, R.layout.item_gallery_preview, null);
            return new GalleryPreviewViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
            GalleryPreviewViewHolder holder = (GalleryPreviewViewHolder) viewHolder;
            File file = data.get(i);
            if (file == null)
                return;
            String path = file.getAbsolutePath();
            Log.i(TAG, "file path " + path);
            holder.img_preview.setScaleType(ImageView.ScaleType.CENTER_CROP);
            Bitmap bitmap = BitmapUtil.decodeBitmap(path, previewSize, previewSize);
            holder.img_preview.setImageBitmap(bitmap);
//            holder.img_preview.setImageResource(R.drawable.dilireba);

            // TODO: 2020/9/5 height 的wrap_content为什么没生效,
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) holder.img_preview.getLayoutParams();
            layoutParams.width = previewSize;
            layoutParams.height = previewSize;
            holder.img_preview.setLayoutParams(layoutParams);
        }

        @Override
        public int getItemCount() {
            if (data == null)
                return 0;
            return data.size();
        }
    }

    class GalleryPreviewViewHolder extends RecyclerView.ViewHolder{
        ImageView img_preview;

        public GalleryPreviewViewHolder(@NonNull View itemView) {
            super(itemView);

            img_preview = itemView.findViewById(R.id.img_preview);
        }
    }
}