package com.bjy.app_product.contacts;

import android.Manifest;
import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.ContentValues;
import android.content.OperationApplicationException;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.RemoteException;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.bjy.app_product.R;
import com.bjy.app_product.database.MyDB;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ContactActivity extends AppCompatActivity {

    private static final String TAG = "111222";

    //防止多次调用
    private int edit_flag = 0;
    private int repair_flag = 0;
    private static final String FAKE_NAME = "Faker";
    private static final String FAKE_PHONE = "20207102111";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        Button btn_1 = findViewById(R.id.btn_1);
        btn_1.setText("一键修改通讯录");
        btn_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(ContactActivity.this, Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(ContactActivity.this, Manifest.permission.WRITE_CONTACTS) == PackageManager.PERMISSION_GRANTED){
                    xiugai();
                }else {
                    ActivityCompat.requestPermissions(ContactActivity.this, new String[]{Manifest.permission.READ_CONTACTS, Manifest.permission.WRITE_CONTACTS}, 1000);
                }
            }
        });

        Button btn_2 = findViewById(R.id.btn_2);
        btn_2.setText("一键还原通讯录");
        btn_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(ContactActivity.this, Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED
                        && ActivityCompat.checkSelfPermission(ContactActivity.this, Manifest.permission.WRITE_CONTACTS) == PackageManager.PERMISSION_GRANTED){
                    huanyuan();
                }else {
                    ActivityCompat.requestPermissions(ContactActivity.this, new String[]{Manifest.permission.READ_CONTACTS, Manifest.permission.WRITE_CONTACTS}, 1001);
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (Manifest.permission.READ_CONTACTS.equals(permissions[0]) && grantResults[0] == PackageManager.PERMISSION_GRANTED
            && Manifest.permission.WRITE_CONTACTS.equals(permissions[1]) && grantResults[1] == PackageManager.PERMISSION_GRANTED){
            if (requestCode == 1000)
                xiugai();
            else if (requestCode == 1001)
                huanyuan();
            else if (requestCode == 1002)
                addContact();
        }
    }

    private void xiugai(){
        if (edit_flag == 1){
            Toast.makeText(ContactActivity.this, "正在修改...", Toast.LENGTH_SHORT).show();
            return;
        }
        edit_flag = 1;

        if (MyDB.getInstance().hasContactsData()){
            Toast.makeText(ContactActivity.this, "请勿重复操作", Toast.LENGTH_SHORT).show();
            edit_flag = 0;
            return;
        }

        Cursor cursor = getContentResolver().query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
        if (cursor == null){
            Toast.makeText(ContactActivity.this, "获取联系人数据失败", Toast.LENGTH_SHORT).show();
            edit_flag = 0;
            return;
        }

        //保存到数据库
        List<ContentValues> phoneList = new ArrayList<>();
        //修改到联系人
        ArrayList<ContentProviderOperation> contentList = new ArrayList();
        while (cursor.moveToNext()){
            int raw_contact_id = cursor.getInt(cursor.getColumnIndex(ContactsContract.Contacts.NAME_RAW_CONTACT_ID));
            String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
            int _id = cursor.getInt(cursor.getColumnIndex(ContactsContract.Contacts._ID));
            Cursor phoneCursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID+"="+_id, null, null);
            if (phoneCursor == null)
                continue;
            String phoneNumber = "";
            while (phoneCursor.moveToNext()){
                phoneNumber = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                int phone_id = phoneCursor.getInt(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone._ID));
                //修改名字
                contentList.add(ContentProviderOperation.newUpdate(ContactsContract.Data.CONTENT_URI)
                        .withSelection(ContactsContract.Data.RAW_CONTACT_ID+ "=? and " + ContactsContract.Data.MIMETYPE + " =? "
                                , new String[]{String.valueOf(raw_contact_id), ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE})
                        .withValue(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME, FAKE_NAME + raw_contact_id)
                        .withValue(ContactsContract.CommonDataKinds.StructuredName.FAMILY_NAME, "")
                        .withValue(ContactsContract.CommonDataKinds.StructuredName.MIDDLE_NAME, "")
                        .build());
                //修改手机号
                contentList.add(ContentProviderOperation.newUpdate(ContactsContract.Data.CONTENT_URI)
                        .withSelection(ContactsContract.Data.RAW_CONTACT_ID + "=? and " + ContactsContract.Data.MIMETYPE + " =? and " + ContactsContract.Data._ID + "=?"
                                , new String[]{String.valueOf(raw_contact_id), ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE, String.valueOf(phone_id)})
                        .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, FAKE_PHONE)
                        .build());

                //数据线暂存下来
                ContentValues contentValues = new ContentValues();
                contentValues.put("raw_contact_id", raw_contact_id);
                contentValues.put("contact_name", name);
                contentValues.put("phone_number", phoneNumber);
                contentValues.put("phone_id", phone_id);
                phoneList.add(contentValues);
            }
            phoneCursor.close();
        }
        cursor.close();
        int operationResult = 0;
        try {
            ContentProviderResult[] results = getContentResolver().applyBatch(ContactsContract.AUTHORITY, contentList);
            for (ContentProviderResult contentProviderResult : results){
                Log.i(TAG, "[result] " + contentProviderResult.toString());
            }
            operationResult = 1;
        } catch (OperationApplicationException | RemoteException e) {
            e.printStackTrace();
        }
        if (operationResult == 0){
            edit_flag = 0;
            return;
        }

        //修改成功后，把数据插入数据库
        MyDB.getInstance().insertContacts(phoneList);
        edit_flag = 0;
        Toast.makeText(ContactActivity.this, "修改联系人成功", Toast.LENGTH_SHORT).show();
    }

    private void huanyuan(){
        if (repair_flag == 1){
            Toast.makeText(ContactActivity.this, "正在修复数据...", Toast.LENGTH_SHORT).show();
            return;
        }
        repair_flag = 1;
        List<Map<String, String>> arraylist = MyDB.getInstance().queryContacts();
        if (arraylist.isEmpty()){
            Toast.makeText(ContactActivity.this, "无联系人数据", Toast.LENGTH_SHORT).show();
            repair_flag = 0;
            return;
        }
        Cursor cursor = getContentResolver().query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
        if (cursor == null){
            repair_flag = 0;
            Toast.makeText(ContactActivity.this, "获取联系人数据失败", Toast.LENGTH_SHORT).show();
            return;
        }

        //修改到联系人
        ArrayList<ContentProviderOperation> contentList = new ArrayList();
        while (cursor.moveToNext()){
            int raw_contact_id = cursor.getInt(cursor.getColumnIndex(ContactsContract.Contacts.NAME_RAW_CONTACT_ID));
            for (Map<String, String> map : arraylist){
                if (String.valueOf(raw_contact_id).equals(map.get("raw_contact_id"))){
                    String name = map.get("contact_name");
                    String phoneNumber = map.get("phone_number");
                    String phone_id = map.get("phone_id");

                    contentList.add(ContentProviderOperation.newUpdate(ContactsContract.Data.CONTENT_URI)
                            .withSelection(ContactsContract.Data.RAW_CONTACT_ID+ "=? and " + ContactsContract.Data.MIMETYPE + " =? "
                                    , new String[]{String.valueOf(raw_contact_id), ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE})
                            .withValue(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME, name)
                            .build());
                    contentList.add(ContentProviderOperation.newUpdate(ContactsContract.Data.CONTENT_URI)
                            .withSelection(ContactsContract.Data.RAW_CONTACT_ID + "=? and " + ContactsContract.Data.MIMETYPE + " =? and " + ContactsContract.Data._ID + "=?"
                                    , new String[]{String.valueOf(raw_contact_id), ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE, String.valueOf(phone_id)})
                            .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, phoneNumber)
                            .build());
                }
            }

        }
        cursor.close();

        int operationResult = 0;
        try {
            ContentProviderResult[] results = getContentResolver().applyBatch(ContactsContract.AUTHORITY, contentList);
            for (ContentProviderResult contentProviderResult : results){
                Log.i(TAG, "[result] " + contentProviderResult.toString());
            }
            operationResult = 1;
        } catch (OperationApplicationException | RemoteException e) {
            e.printStackTrace();
        }
        if (operationResult == 0){
            repair_flag = 0;
            return;
        }
        //操作成功后
        MyDB.getInstance().deleteContacts();
        repair_flag = 0;

        Toast.makeText(ContactActivity.this, "联系人数据已恢复", Toast.LENGTH_SHORT).show();
    }

    private void addContact(){
        ArrayList<ContentProviderOperation> list = new ArrayList<>();
        list.add(ContentProviderOperation.newInsert(ContactsContract.RawContacts.CONTENT_URI)
                .withValue(ContactsContract.RawContacts.ACCOUNT_NAME, null)
                .build());
        list.add(ContentProviderOperation.newInsert(ContactsContract.RawContacts.CONTENT_URI)
                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
                .withValue(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME, "做主")
                .build());
        list.add(ContentProviderOperation.newInsert(ContactsContract.RawContacts.CONTENT_URI)
                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
                .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, "111000")
                .build());
        try {
            getContentResolver().applyBatch(ContactsContract.AUTHORITY, list);
        } catch (OperationApplicationException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    //打印 cursor 集合中的列名称
    private void printCursorLines(Cursor cursor, String cursorName){
        if (cursor == null)
            return;

        Log.i(TAG, cursorName + " [cursor] 列如下, 列长度 " + cursor.getColumnCount());
        // TODO: 2020/7/4
//        cursor.moveToFirst();
        //获取列名数组
        String[] names = cursor.getColumnNames();
        for (String name : names){
            //列顺序
            int index = cursor.getColumnIndex(name);
            //列属性
            int type = cursor.getType(index);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("[").append(name).append("] ");
            switch (type) {
                case Cursor.FIELD_TYPE_INTEGER:
                    stringBuilder.append("[int] ").append(cursor.getInt(index));
                    break;
                case Cursor.FIELD_TYPE_FLOAT:
                    stringBuilder.append("[float] ").append(cursor.getFloat(index));
                    break;
                case Cursor.FIELD_TYPE_STRING:
                    stringBuilder.append("[string] ").append(cursor.getString(index));
                    break;
                case Cursor.FIELD_TYPE_BLOB:
                    stringBuilder.append("[blob]");
                    break;
                case Cursor.FIELD_TYPE_NULL:
                    stringBuilder.append("[null]");
                    break;
            }
            Log.i(TAG, stringBuilder.toString());
        }

        System.out.println("\n\n");
    }

    //对比两个cursor的不同（结构是一样的，数据不一样）
    private void printDifference(Cursor cursor1, Cursor cursor2){
        //获取列名数组
        String[] names = cursor1.getColumnNames();
        for (String name : names){
            //列顺序
            int index = cursor1.getColumnIndex(name);
            //列属性
            int type = cursor1.getType(index);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("[").append(name).append("] ");
            boolean logEnable = false;
            switch (type) {
                case Cursor.FIELD_TYPE_INTEGER:
                    if (cursor1.getInt(index) != cursor2.getInt(index)){
                        stringBuilder.append("[int] [").append(cursor1.getInt(index)).append("] [").append(cursor2.getInt(index)).append("]");
                        logEnable = true;
                    }

                    break;
                case Cursor.FIELD_TYPE_FLOAT:
                    if (cursor1.getFloat(index) != cursor2.getFloat(index)){
                        stringBuilder.append("[float] [").append(cursor1.getFloat(index)).append("] [").append(cursor2.getFloat(index)).append("]");
                        logEnable = true;
                    }
                    break;
                case Cursor.FIELD_TYPE_STRING:
                    String s1 = cursor1.getString(index);
                    String s2 = cursor2.getString(index);
                    if ((s1 != null && !s1.equals(s2)) || (s2 != null && !s2.equals(s1))){
                        stringBuilder.append("[string] [").append(s1).append("] [").append(s2).append("]");
                        logEnable = true;
                    }
                    break;
                case Cursor.FIELD_TYPE_BLOB:
//                    stringBuilder.append("[blob]");
                    break;
                case Cursor.FIELD_TYPE_NULL:
//                    stringBuilder.append("[null]");
                    break;
            }
            if (logEnable)
                Log.i(TAG, stringBuilder.toString());
        }
    }
}