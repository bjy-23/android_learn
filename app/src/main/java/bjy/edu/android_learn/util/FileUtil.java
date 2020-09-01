package bjy.edu.android_learn.util;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import bjy.edu.android_learn.App;

/**
 *  mkdir: 只能创建一级目录
 *  mkdirs: 可以创建多级目录
 *  调用mkdir、mkdirs时根据返回值判断是否创建成功 ！！！
 *
 *
 */
public class FileUtil {
    private static Context sContext = App.getInstance();

    //把asset目录中的文件copy到指定目录
    public static void copyAssetFile(Context context, String fileName, File target){
        try {
            InputStream inputStream = context.getResources().getAssets().open(fileName);
            FileOutputStream fileOutputStream = new FileOutputStream(target);
            byte[] bytes = new byte[1024];
            int count = -1;
            while ((count=inputStream.read(bytes)) != -1){
                fileOutputStream.write(bytes, 0, count);
            }
            inputStream.close();
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //容易导致空指针
    public static File UriToFile(Uri uri){
        Context context = sContext;
        File file = null;
        if (uri == null)
            return null;
        String path = "";
        if ("content".equals(uri.getScheme())){
            String[] proj = { MediaStore.Images.Media.DATA };
            Cursor cursor = context.getContentResolver().query(uri, proj, null, null, null);
            if (cursor.moveToFirst()) {
                int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                path = cursor.getString(columnIndex);
            }
            cursor.close();
            if (path == null)
                return null;
            return new File(path);
        }else if ("file".equals(uri.getScheme())){
            path = uri.getEncodedPath();
            if (path != null) {
                path = Uri.decode(path);
                ContentResolver cr = context.getContentResolver();
                StringBuffer buff = new StringBuffer();
                buff.append("(").append(MediaStore.Images.ImageColumns.DATA).append("=").append("'" + path + "'").append(")");
                Cursor cur = cr.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, new String[] { MediaStore.Images.ImageColumns._ID, MediaStore.Images.ImageColumns.DATA }, buff.toString(), null, null);
                int index = 0;
                int dataIdx = 0;
                for (cur.moveToFirst(); !cur.isAfterLast(); cur.moveToNext()) {
                    index = cur.getColumnIndex(MediaStore.Images.ImageColumns._ID);
                    index = cur.getInt(index);
                    dataIdx = cur.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                    path = cur.getString(dataIdx);
                }
                cur.close();
            }
            if (path != null) {
                return new File(path);
            }
        }

        return file;
    }

    public static String UriToFilePath(Uri uri){
        Context context = sContext;
        String path = null;
        // 4.4及之后的 是以 content:// 开头的，比如 content://com.android.providers.media.documents/document/image%3A235700
        if (ContentResolver.SCHEME_CONTENT.equals(uri.getScheme()) && Build.VERSION.SDK_INT >= 19) {
            if (DocumentsContract.isDocumentUri(context, uri)) {
                if (isExternalStorageDocument(uri)) {
                    // ExternalStorageProvider
                    final String docId = DocumentsContract.getDocumentId(uri);
                    final String[] split = docId.split(":");
                    final String type = split[0];
                    if ("primary".equalsIgnoreCase(type)) {
                        path = Environment.getExternalStorageDirectory() + "/" + split[1];
                        return path;
                    }
                } else if (isDownloadsDocument(uri)) {
                    // DownloadsProvider
                    final String id = DocumentsContract.getDocumentId(uri);
                    final Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"),
                            Long.valueOf(id));
                    path = getDataColumn(context, contentUri, null, null);
                    return path;
                } else if (isMediaDocument(uri)) {
                    // MediaProvider
                    final String docId = DocumentsContract.getDocumentId(uri);
                    final String[] split = docId.split(":");
                    final String type = split[0];
                    Uri contentUri = null;
                    if ("image".equals(type)) {
                        contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                    } else if ("video".equals(type)) {
                        contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                    } else if ("audio".equals(type)) {
                        contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                    }
                    final String selection = "_id=?";
                    final String[] selectionArgs = new String[]{split[1]};
                    path = getDataColumn(context, contentUri, selection, selectionArgs);
                    return path;
                }
            }
        }else {
            // 以 file:// 开头的
            if (ContentResolver.SCHEME_FILE.equals(uri.getScheme())) {
                path = uri.getPath();
                return path;
            }
            // 以 content:// 开头的，比如 content://media/extenral/images/media/17766
            if (ContentResolver.SCHEME_CONTENT.equals(uri.getScheme()) && Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
                Cursor cursor = context.getContentResolver().query(uri, new String[]{MediaStore.Images.Media.DATA}, null, null, null);
                if (cursor != null) {
                    if (cursor.moveToFirst()) {
                        int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                        if (columnIndex > -1) {
                            path = cursor.getString(columnIndex);
                        }
                    }
                    cursor.close();
                }
                return path;
            }
        }
        return null;
    }

    private static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {
        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {column};
        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    private static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    private static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    private static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    public static String getByUri(Uri uri){
        Context context = sContext;
        String realPath = null;
        //如果大于4.4
        if (isKitKat()) {
            //如果是document类型uri, 则通过id获取
            if (DocumentsContract.isDocumentUri(context, uri)) {
                String docId = DocumentsContract.getDocumentId(uri);
                if (isDownloadsDocuments(uri)) {
                    Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(docId));
                    String[] proj = {"_data"};
                    Cursor cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
                    if (cursor != null && cursor.moveToFirst()) {
                        realPath = cursor.getString(cursor.getColumnIndexOrThrow("_data"));
                        cursor.close();
                    }

                } else if (isMediaDocuments(uri)) {
                    String id = docId.split(":")[1];
                    Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://media/external/file"), Long.valueOf(id));
                    realPath = getRealPath(context, contentUri, null);
                    /*
                     String type = docId.split(":")[0];
                     String id = docId.split(":")[1];
                     Uri contentUri = null;
                     String selection = "_id=" + id;
                     if (type.equals("image")) {
                     contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                     } else if (type.equals("audio")) {
                     contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                     } else if (type.equals("video")) {
                     contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                     }
                     if (contentUri != null && selection != null) {
                     //realPath = getRealPath(context, contentUri, selection);
                     realPath=getRealPath(context,contentUri,null);
                     }
                     */
                } else if (isExternalStorageDocuments(uri)) {
                    return getRootPath() + "/" + docId.split(":")[1];
                }

            } else if (isSchemeContent(uri)) {
                if (isRE(uri) || isEstrongs(uri)) {
                    realPath = uri.getPath();
                } else if (isQQBrowserFileProvider(uri)) {
                    realPath = getRootPath() + uri.getPath();
                } else if (isFileExplorerMyProvider(uri)) {
                    realPath = uri.getPath().replaceFirst("/external_files", getRootPath());
                } else {
                    realPath = getRealPath(context, uri, null);
                }
            } else if (isSchemeFile(uri)) {
                realPath = uri.getPath();
            }
        } else {
            //小于4.4
            realPath = getRealPath(context, uri, null);
        }
        return realPath;
    }



    private static String getRealPath(Context context, Uri uri, String selection) {

        String path = null;
        // 通过Uri和selection来获取真实的图片路径
        Cursor cursor = context.getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            path = cursor.getString(cursor.getColumnIndex("_data"));
            cursor.close();
        }
        return path;
    }


    private static boolean isRE(Uri uri) {
        ///storage/emulated/0/log.txt
        // content
        return uri.getAuthority().equals("com.speedsoftware.rootexplorer.content");
    }
    private static boolean isEstrongs(Uri uri) {
        ///storage/emulated/0/log.txt
        // content
        return uri.getAuthority().equals("com.estrongs.files");
    }

    private static boolean isMIUIGallery(Uri uri) {
        // /raw//storage/emulated/0/DCIM/Camera/IMG_20200318_080535.jpg
        // content
        return uri.getAuthority().equals("com.miui.gallery.open");
    }
    private static boolean isMedia(Uri uri) {
        // /external/audio/media/69767
        // /external/images/media/86837
        // /external/file/130685
        // content
        return uri.getAuthority().equals("media");
    }
    private static boolean isQQBrowserFileProvider(Uri uri) {
        // /QQBrowser/log.txt
        // content
        return uri.getAuthority().equals("com.tencent.mtt.fileprovider");
    }
    private static boolean isFileExplorerMyProvider(Uri uri) {
        // /external_files/netease/cloudmusic/Music/许嵩 - 幻听.mp3
        // content
        return uri.getAuthority().equals("com.android.fileexplorer.myprovider");
    }
    private static boolean isDownloadsDocuments(Uri uri) {
        // /document/503
        // documentUri
        return uri.getAuthority().equals("com.android.providers.downloads.documents");
    }
    private static boolean isMediaDocuments(Uri uri) {
        // /document/audio:69767
        // /document/video:126419
        // /document/image:130682
        // documentUri
        return uri.getAuthority().equals("com.android.providers.media.documents");
    }
    private static boolean isExternalStorageDocuments(Uri uri) {
        // /document/primary:{文件相对路径}
        // documentUri
        return uri.getAuthority().equals("com.android.externalstorage.documents");
    }

    private static boolean isKitKat() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
    }
    private static boolean isSchemeContent(Uri uri) {
        return uri.getScheme().equals(ContentResolver.SCHEME_CONTENT);
    }

    private static boolean isSchemeFile(Uri uri) {
        return uri.getScheme().equals(ContentResolver.SCHEME_FILE);
    }

    private static String getRootPath() {
        return Environment.getExternalStorageDirectory().getPath();
    }
}
