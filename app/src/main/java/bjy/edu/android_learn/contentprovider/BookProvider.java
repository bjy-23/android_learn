package bjy.edu.android_learn.contentprovider;

import android.app.AlertDialog;
import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import bjy.edu.android_learn.R;

//配置manifest读写权限时不应该明文配置
public class BookProvider extends ContentProvider {
    BookHelper bookHelper;

    UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    @Override
    public boolean onCreate() {
        Log.i("BookProvider", "onCreate");
        bookHelper = BookHelper.getInstance(getContext());

        uriMatcher.addURI(getContext().getResources().getString(R.string.authority_book), "", 0);
        return false;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        Log.i("BookProvider", "query");
        Cursor cursor = bookHelper.getReadableDatabase().query(BookHelper.table_name, projection, selection, selectionArgs, null, null, sortOrder);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        Log.i("BookProvider", "insert");
        bookHelper.getWritableDatabase().insert(BookHelper.table_name, null, values);
        getContext().getContentResolver().notifyChange(uri, null);
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        Log.i("BookProvider", "delete");
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}
