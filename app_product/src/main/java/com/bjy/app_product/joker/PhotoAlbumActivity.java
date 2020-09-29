package com.bjy.app_product.joker;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bjy.app_product.R;
import com.bjy.app_product.util.BitmapUtil;
import com.bjy.app_product.util.DisplayUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class PhotoAlbumActivity extends AppCompatActivity {
    private static final String TAG = "111222";
    private RecyclerView recyclerView;

    private int previewSize;//预览图片的宽高
    private int count = 3; // 一行展示
    private int margin = DisplayUtil.dp2px(30);//相册之前的间距

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_album);

        //相册路径
        File dir = getExternalFilesDir("joker");
        if (!dir.exists()){
            Log.i("111222", "相册不存在");
            return;
        }
        final ArrayList<File> files = new ArrayList<>();
        File[] albums = dir.listFiles();
        for (File file : albums){
            if (file.isDirectory()){
                files.add(file);
            }
        }
        //获取屏幕宽高
        int[] array = DisplayUtil.getScreenWidthHeight(this);
        previewSize = (array[0] - (count * margin)) / count;

        recyclerView = findViewById(R.id.recyclerView);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(PhotoAlbumActivity.this, count, LinearLayout.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        PhotoAlbumAdapter photoAlbumAdapter = new PhotoAlbumAdapter(PhotoAlbumActivity.this, files);
        recyclerView.setAdapter(photoAlbumAdapter);
        photoAlbumAdapter.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(int position) {
                Intent intent = new Intent(PhotoAlbumActivity.this, GalleryPreviewActivity.class);
                intent.putExtra(GalleryPreviewActivity.INTENT_IMAGE_PATH, files.get(position).getAbsolutePath());
                startActivity(intent);
            }
        });
    }

    class PhotoAlbumAdapter extends RecyclerView.Adapter{
        private Context context;
        private List<File> data;

        OnClickListener onClickListener;

        public void setOnClickListener(OnClickListener onClickListener) {
            this.onClickListener = onClickListener;
        }

        public PhotoAlbumAdapter(Context context, List<File> data) {
            this.context = context;
            this.data = data;
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = View.inflate(context, R.layout.item_photo_album, null);
            return new PhotoAlbumViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {
            PhotoAlbumViewHolder holder = (PhotoAlbumViewHolder) viewHolder;
            File file = data.get(i);
            if (file == null || !file.isDirectory())
                return;

            File[] files = file.listFiles();
            String path = files[0].getAbsolutePath();
            Log.i(TAG, "file path " + path);
            holder.img_preview.setScaleType(ImageView.ScaleType.CENTER_CROP);
            Bitmap bitmap = BitmapUtil.decodeBitmap(path, previewSize, previewSize);
            holder.img_preview.setImageBitmap(bitmap);
//            holder.img_preview.setImageResource(R.drawable.dilireba);

            holder.tv_name.setText(file.getName());
            holder.tv_num.setText(files.length + "");
            // TODO: 2020/9/5 height 的wrap_content为什么没生效,
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) holder.img_preview.getLayoutParams();
            layoutParams.width = previewSize;
            layoutParams.height = previewSize;
            holder.img_preview.setLayoutParams(layoutParams);

            holder.layout_album.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onClickListener != null){
                        onClickListener.onClick(i);
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            if (data == null)
                return 0;
            return data.size();
        }
    }

    class PhotoAlbumViewHolder extends RecyclerView.ViewHolder{
        View layout_album;
        ImageView img_preview;
        TextView tv_name;
        TextView tv_num;

        public PhotoAlbumViewHolder(@NonNull View itemView) {
            super(itemView);

            layout_album = itemView.findViewById(R.id.layout_album);
            img_preview = itemView.findViewById(R.id.img_preview);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_num = itemView.findViewById(R.id.tv_num);
        }
    }

    interface OnClickListener{
        void onClick(int position);
    }
}