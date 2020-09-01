package bjy.edu.android_learn.activity;

import android.Manifest;
import android.app.Activity;
import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.OperationApplicationException;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Binder;
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

import com.tbruyelle.rxpermissions2.RxPermissions;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import bjy.edu.android_learn.R;

/*
*  参考文档 ： https://developer.android.google.cn/guide/topics/providers/contacts-provider
* */

public class ContactActivity extends AppCompatActivity {
    private static final String TAG = "111222";

    //防止多次调用
    private int edit_flag = 0;
    private int repair_flag = 0;
    private static final String FAKE_NAME = "Faker";
    private static final String FAKE_PHONE = "20207102111";
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH时mm分ss秒");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        Button btn_1 = findViewById(R.id.btn_1);
        btn_1.setText("一键修改通讯录");
        btn_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(ContactActivity.this, Manifest.permission.WRITE_CONTACTS) == PackageManager.PERMISSION_GRANTED){
                    xiugai();
                }else {
                    ActivityCompat.requestPermissions(ContactActivity.this, new String[]{Manifest.permission.WRITE_CONTACTS}, 1000);
                }
            }
        });

        Button btn_2 = findViewById(R.id.btn_2);
        btn_2.setText("一键还原通讯录");
        btn_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(ContactActivity.this, Manifest.permission.WRITE_CONTACTS) == PackageManager.PERMISSION_GRANTED){
                    huanyuan();
                }else {
                    ActivityCompat.requestPermissions(ContactActivity.this, new String[]{Manifest.permission.WRITE_CONTACTS}, 1001);
                }
            }
        });

        Button btn_3 = findViewById(R.id.btn_3);
        btn_3.setText("新增联系人");
        btn_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(ContactActivity.this, Manifest.permission.WRITE_CONTACTS) == PackageManager.PERMISSION_GRANTED){
                    addContact();
                }else {
                    ActivityCompat.requestPermissions(ContactActivity.this, new String[]{Manifest.permission.WRITE_CONTACTS}, 1002);
                }
            }
        });

        Button btn_4 = findViewById(R.id.btn_4);
        btn_4.setText("清空联系人");
        btn_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(ContactActivity.this, Manifest.permission.WRITE_CONTACTS) == PackageManager.PERMISSION_GRANTED){
                    deleteContact();
                }else {
                    ActivityCompat.requestPermissions(ContactActivity.this, new String[]{Manifest.permission.WRITE_CONTACTS}, 1002);
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (Manifest.permission.WRITE_CONTACTS.equals(permissions[0]) && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            if (requestCode == 1000)
                xiugai();
            else if (requestCode == 1001)
                huanyuan();
            else if (requestCode == 1002)
                addContact();
        }
    }

    private void xiugai(){
//        Cursor cursor_raw = getContentResolver().query(ContactsContract.RawContacts.CONTENT_URI, null, null, null, null);
//        cursor_raw.moveToLast();
//        printCursorLines(cursor_raw);

//        Cursor cursor_data = getContentResolver().query(ContactsContract.Data.CONTENT_URI, null, null, null, null);
//        cursor_data.moveToLast();
//        printCursorLines(cursor_data, "cursor_data");

        Cursor cursor = getContentResolver().query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);

//        cursor.moveToPosition(0);
//        printCursorLines(cursor, "cursor_contacts");
//        if (true)
//            return;

        Log.i("111222", "数据修改");
        //
        if (edit_flag == 1){
            Toast.makeText(ContactActivity.this, "正在修改...", Toast.LENGTH_SHORT).show();
            return;
        }

        edit_flag = 1;

        if (cursor == null){
            edit_flag = 0;
            return;
        }

        if (MyDB.getInstance().hasContactsData()){
            Toast.makeText(ContactActivity.this, "请勿重复操作", Toast.LENGTH_SHORT).show();
            edit_flag = 0;
            return;
        }

        int count = 0;
        //移动到初始位置
        cursor.moveToPosition(-1);
        //保存到数据库
        List<ContentValues> phoneList = new ArrayList<>();
        //修改到联系人
        ArrayList<ContentProviderOperation> contentList = new ArrayList();
        while (cursor.moveToNext()){

            int _id = cursor.getInt(cursor.getColumnIndex(ContactsContract.Contacts._ID));
            // TODO: 2020/7/11 5.0才能取到
            int raw_contact_id = cursor.getInt(cursor.getColumnIndex(ContactsContract.Contacts.NAME_RAW_CONTACT_ID));
            //只处理前n条数据
//            count ++;
//            if (count > 5)
//                break;

            //只处理固定的数据
//            if (_id != 4)
//                continue;

            Log.i("111222", "_id " + _id);
            Log.i("111222", "raw_contact_id " + raw_contact_id);
            String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
            Log.i("111222", "name " + name);
            Cursor phoneCursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID+"="+_id, null, null);

//            if (true)
//                continue;

            //比较cursor值的不同
//            Cursor phoneCursor1 = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
//                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID+"=11", null, null);
//            phoneCursor1.moveToNext();
//            Cursor phoneCursor2 = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
//                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID+"=15", null, null);
//            phoneCursor2.moveToNext();
//            printDifference(phoneCursor1, phoneCursor2);
//            if (true)
//                break;
//            printCursorLines(phoneCursor);
//            phoneCursor.moveToPosition(-1);


            String phoneNumber = "";
            String phoneType = "";
            while (phoneCursor.moveToNext()){
//                printCursorLines(phoneCursor, "phoneCursor");
                phoneNumber = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                Log.i("111222", "phoneNumber " + phoneNumber);
                phoneType = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.TYPE));
                Log.i("111222", "phoneType " + phoneType);
                int phone_id = phoneCursor.getInt(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone._ID));
                Log.i("111222", "phone_id " + phone_id);

                //保存当前数据 todo 子线程操作
                ContentValues contentValues = new ContentValues();
                contentValues.put("raw_contact_id", raw_contact_id);
                contentValues.put("contact_name", name);
                contentValues.put("phone_number", phoneNumber);
                contentValues.put("phone_id", phone_id);
                phoneList.add(contentValues);

                String nameFaker = FAKE_NAME + raw_contact_id;
                String phoneFaker = FAKE_PHONE;

                //改名字，这步可以不需要
//                contentList.add(ContentProviderOperation.newUpdate(ContactsContract.RawContacts.CONTENT_URI)
//                        .withSelection(ContactsContract.RawContacts._ID+ "=?"
//                                , new String[]{String.valueOf(raw_contact_id)})
//                        .withValue(ContactsContract.RawContacts.DISPLAY_NAME_PRIMARY, nameFaker)
//                        .build());
                contentList.add(ContentProviderOperation.newUpdate(ContactsContract.Data.CONTENT_URI)
                        .withSelection(ContactsContract.Data.RAW_CONTACT_ID+ "=? and " + ContactsContract.Data.MIMETYPE + " =? "
                                , new String[]{String.valueOf(raw_contact_id), ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE})
                        .withValue(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME, nameFaker)
                        .withValue(ContactsContract.CommonDataKinds.StructuredName.FAMILY_NAME, "")
                        .withValue(ContactsContract.CommonDataKinds.StructuredName.MIDDLE_NAME, "")
                        .build());


                contentList.add(ContentProviderOperation.newUpdate(ContactsContract.Data.CONTENT_URI)
                        .withSelection(ContactsContract.Data.RAW_CONTACT_ID + "=? and " + ContactsContract.Data.MIMETYPE + " =? and " + ContactsContract.Data._ID + "=?"
                                , new String[]{String.valueOf(raw_contact_id), ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE, String.valueOf(phone_id)})
                        .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, phoneFaker)
                        .build());
            }
            phoneCursor.close();

            //修改手机
//            Cursor cursorRaw = getContentResolver().query(ContactsContract.RawContacts.CONTENT_URI, null, null, null, null);
//            printCursorLines(cursorRaw);
//
//            ContentValues cv = new ContentValues();
//            cv.put("data1", FAKE_PHONE);
//            int result = getContentResolver().update(ContactsContract.Data.CONTENT_URI, cv, "mimetype = ? and raw_contact_id = ?"
//                    , new String[]{ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE, "15"});
//            Log.i("111222", "修改手机 " + result);

        }
        cursor.close();

        //实际不操作，只打印数据
//        if (true){
//            edit_flag = 0;
//            return;
//        }

        int operationResult = 0;
        try {
            ContentProviderResult[] results = getContentResolver().applyBatch(ContactsContract.AUTHORITY, contentList);
            for (ContentProviderResult contentProviderResult : results){
                Log.i("111222", "[result] " + contentProviderResult.toString());
            }
            operationResult = 1;
        } catch (OperationApplicationException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        if (operationResult == 0){
            edit_flag = 0;
            return;
        }

        //修改成功后，把数据插入数据库
        Toast.makeText(ContactActivity.this, "修改联系人成功", Toast.LENGTH_SHORT).show();
        MyDB.getInstance().insertContacts(phoneList);
        edit_flag = 0;
    }

    private void huanyuan(){
        Log.i("111222", "修复数据");
        if (repair_flag == 1){
            Toast.makeText(ContactActivity.this, "正在修复数据...", Toast.LENGTH_SHORT).show();
            return;
        }
        List<Map<String, String>> arraylist = MyDB.getInstance().queryContacts();
        if (arraylist.isEmpty()){
            Toast.makeText(ContactActivity.this, "无联系人数据", Toast.LENGTH_SHORT).show();
            repair_flag = 0;
            return;
        }
        Cursor cursor = getContentResolver().query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
        if (cursor == null)
            return;
        //修改到联系人
        ArrayList<ContentProviderOperation> contentList = new ArrayList();
        while (cursor.moveToNext()){
            Log.i("111222", "cursor count " + cursor.getCount());
            int _id = cursor.getInt(cursor.getColumnIndex(ContactsContract.Contacts._ID));
            // TODO: 2020/7/11 5.0才能取到
            int raw_contact_id = cursor.getInt(cursor.getColumnIndex(ContactsContract.Contacts.NAME_RAW_CONTACT_ID));
            Log.i("111222", "_id " + _id);
            for (Map<String, String> map : arraylist){
                if (String.valueOf(raw_contact_id).equals(map.get("raw_contact_id"))){
                    String name = map.get("contact_name");
                    Log.i("111222", "name " + name);
                    String phoneNumber = map.get("phone_number");
                    Log.i("111222", "phoneNumber " + phoneNumber);
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
                Log.i("111222", "[result] " + contentProviderResult.toString());
            }
            operationResult = 1;
        } catch (OperationApplicationException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        if (operationResult == 0){
            repair_flag = 0;
            return;
        }

        Toast.makeText(ContactActivity.this, "联系人数据已恢复", Toast.LENGTH_SHORT).show();

        MyDB.getInstance().deleteContacts();
        repair_flag = 0;
    }

    private void addContact(){
//        addContact1();

//        addContact2();

        addContact3();
    }

    int count = 100;
    //applyBatch
    private void addContact1(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.i(TAG, "[time_start_patch] " + simpleDateFormat.format(new Date()));
                ArrayList<ContentProviderOperation> list = new ArrayList<>();
                String name = "佐助";
                String tel = "110110";
                for (int i=0; i<count; i++){
                    int rawContactInsertIndex = list.size();

                    list.add(ContentProviderOperation.newInsert(ContactsContract.RawContacts.CONTENT_URI)
                            .withValue(ContactsContract.RawContacts.ACCOUNT_TYPE, null)
                            .withValue(ContactsContract.RawContacts.ACCOUNT_NAME, null)
                            .withYieldAllowed(true)
                            .build());
                    list.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                            .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, rawContactInsertIndex)
                            .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
                            .withValue(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME, name + rawContactInsertIndex)
                            .withYieldAllowed(true)
                            .build());
                    list.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                            .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, rawContactInsertIndex)
                            .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
                            .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, tel + rawContactInsertIndex)
                            .withValue(ContactsContract.CommonDataKinds.Phone.TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE)
                            .withYieldAllowed(true)
                            .build());
                }
                try {
                    ContentProviderResult[] results = getContentResolver().applyBatch(ContactsContract.AUTHORITY, list);
                    for (ContentProviderResult contentProviderResult : results) {
//                        Log.i("111222", "[result] " + contentProviderResult.toString());
                    }
                } catch (OperationApplicationException e) {
                    e.printStackTrace();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                Log.i(TAG, "[time_end_patch] " + simpleDateFormat.format(new Date()));
            }
        }).start();
    }

    //insert
    public void addContact2() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.i(TAG, "[time_start_insert] " + simpleDateFormat.format(new Date()));
                // 创建一个空的ContentValues
                ContentValues values = new ContentValues();
                String name = "鸣人";
                String tel = "120120";
                for (int i=0; i<count; i++){
                    // 向RawContacts.CONTENT_URI空值插入，
                    // 先获取Android系统返回的rawContactId
                    // 后面要基于此id插入值
                    Uri rawContactUri = getContentResolver().insert(ContactsContract.RawContacts.CONTENT_URI, values);
                    long rawContactId = ContentUris.parseId(rawContactUri);
                    values.clear();

                    //添加姓名
                    values.put(ContactsContract.Data.RAW_CONTACT_ID, rawContactId);
                    // 内容类型
                    values.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE);
                    // 联系人名字
                    values.put(ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME, name + "-" + rawContactId);
                    // 向联系人URI添加联系人名字
                    getContentResolver().insert(ContactsContract.Data.CONTENT_URI, values);
                    values.clear();

                    //添加手机号
                    values.put(ContactsContract.Data.RAW_CONTACT_ID, rawContactId);
                    values.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE);
                    // 联系人的电话号码
                    values.put(ContactsContract.CommonDataKinds.Phone.NUMBER, tel + "-" + rawContactId);
                    // 电话类型
                    values.put(ContactsContract.CommonDataKinds.Phone.TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE);
                    // 向联系人电话号码URI添加电话号码
                    getContentResolver().insert(ContactsContract.Data.CONTENT_URI, values);
                    values.clear();
                }
                Log.i(TAG, "[time_end_insert] " + simpleDateFormat.format(new Date()));
            }
        }).start();
    }

    //bulkinsert
    public void addContact3(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.i(TAG, "[time_start_bulkInsert] " + simpleDateFormat.format(new Date()));

                ContentValues[] values = new ContentValues[count];
                for (int i=0; i<count; i++){
                    ContentValues contentValues = new ContentValues();
                    values[i] = contentValues;
                }
                getContentResolver().bulkInsert(ContactsContract.RawContacts.CONTENT_URI, values);
                Log.i(TAG, "[time_end_bulkInsert] " + simpleDateFormat.format(new Date()));
            }
        }).start();
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

    private void deleteContact(){
        Cursor contactsCur = getContentResolver().query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
        if (contactsCur == null)
            return;
        while(contactsCur.moveToNext()){
            //获取ID
            String rawId = contactsCur.getString(contactsCur.getColumnIndex(ContactsContract.Contacts._ID));
            //删除
            String where = ContactsContract.Data._ID  + " =?";
            String[] whereparams = new String[]{rawId};
            getContentResolver().delete(ContactsContract.RawContacts.CONTENT_URI, where, whereparams);
        }
        Log.i("111222", "delete finish");
        Toast.makeText(this, "删除完成", Toast.LENGTH_SHORT).show();
    }
}