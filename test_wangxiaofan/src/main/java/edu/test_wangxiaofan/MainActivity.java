package edu.test_wangxiaofan;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView tv_wyf = findViewById(R.id.tv_wyf);
        tv_wyf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                new ProgressUtil.Builder(MainActivity.this).show();

//                View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.list_dingwb, null);
//                ListView listView = view.findViewById(R.id.listView);
//                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                    @Override
//                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                        Toast.makeText(MainActivity.this, "position: " + position, Toast.LENGTH_SHORT).show();
//                    }
//                });
//                List<Bean> list = new ArrayList<>();
//                for (int i=0; i<10; i++){
//                    Bean bean = new Bean();
//                    bean.setName("dingwb_" + i);
//                    bean.setAge("18_" + i);
//                    list.add(bean);
//                }
//                Adapter adapter = new Adapter(MainActivity.this, list);
//                listView.setAdapter(adapter);
//
//                PopupWindow popupWindow = new PopupWindow(view, 500, 500);
//                popupWindow.setFocusable(true);
//
//                popupWindow.showAsDropDown(tv_wyf);

            if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
                File file = new File(Environment.getExternalStorageDirectory(), "baijy2020.txt");
                try {
                    FileOutputStream fileOutputStream = new FileOutputStream(file);
                    fileOutputStream.write("柏建宇2020020".getBytes());
                    fileOutputStream.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }else {
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 100);
            }
            }
        });
    }

    class Bean{
        String name;
        String age;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAge() {
            return age;
        }

        public void setAge(String age) {
            this.age = age;
        }
    }

    class Adapter extends BaseAdapter{
        private Context context;
        List<Bean> data;

        public Adapter(Context context, List<Bean> data) {
            this.context = context;
            this.data = data;
        }

        @Override
        public int getCount() {
            if (data == null)
                return 0;

            return data.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View itemView;
            ViewHolder viewHolder;
            if (convertView == null){
                viewHolder = new ViewHolder();
                itemView = View.inflate(context, R.layout.item_dingwb, null);
                viewHolder.tv_name = itemView.findViewById(R.id.tv_name);
                viewHolder.tv_age = itemView.findViewById(R.id.tv_age);
                itemView.setTag(viewHolder);
            }else {
                itemView = convertView;
                viewHolder = (ViewHolder) itemView.getTag();
            }

            Bean bean = data.get(position);
            if (bean != null){
                viewHolder.tv_age.setText(bean.getAge());
                viewHolder.tv_name.setText(bean.getName());
            }

            return itemView;
        }
    }

    class ViewHolder{
        TextView tv_name;
        TextView tv_age;
    }
}
