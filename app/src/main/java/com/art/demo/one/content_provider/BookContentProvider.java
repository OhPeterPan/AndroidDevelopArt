package com.art.demo.one.content_provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.art.demo.one.open_helper.BookHelper;

public class BookContentProvider extends ContentProvider {
    public static final String AUTHORITY = "com.art.demo.one.provider";
    public static final Uri BOOK_CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/book");
    public static final Uri USER_CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/user");

    public static final int BOOK_CODE = 0;
    public static final int USER_CODE = 1;
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        sUriMatcher.addURI(AUTHORITY, "book", BOOK_CODE);
        sUriMatcher.addURI(AUTHORITY, "user", USER_CODE);
    }

    private Context mContext;
    private SQLiteDatabase mDb;

    @Override
    public boolean onCreate() {//除了这个方法由系统回调运行在主线程中，其它几个方法均运行在Binder的线程池中
        mContext = getContext();
        initDBHelper();//在ContentProvider创建时创建数据库，注：为了演示方便在主线程中进行数据库操作，实际开发中不推荐在主线程中进行耗时的操作
        return true;
    }

    private void initDBHelper() {
        mDb = new BookHelper(mContext).getWritableDatabase();
        mDb.execSQL("delete from " + BookHelper.BOOK_TABLE_NAME);
        mDb.execSQL("delete from " + BookHelper.USER_TABLE_NAME);
        mDb.execSQL(" insert into book values(3,'android');");
        mDb.execSQL(" insert into book values(4,'ios');");
        mDb.execSQL(" insert into book values(5,'html5');");
        mDb.execSQL(" insert into user values(1,'jack',1);");
        mDb.execSQL(" insert into user values(2,'jasmine',0);");
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] strings, @Nullable String s, @Nullable String[] strings1, @Nullable String s1) {
        String tableName = getTableName(uri);
        Cursor cursor = mDb.query(tableName, strings, s, strings1, null, null, s1, null);
        return cursor;
    }

    /**
     * 用来返回一个Uri请求所对应的MIME类型（媒体类型），比如图片、视频
     * 不关心的话可以返回null或者“*\/*”
     *
     * @param uri
     * @return
     */
    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        String table = getTableName(uri);
        mDb.insert(table, null, contentValues);
        mContext.getContentResolver().notifyChange(uri, null);
        return uri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        String table = getTableName(uri);
        int count = mDb.delete(table, s, strings);
        if (count > 0)
            mContext.getContentResolver().notifyChange(uri, null);

        return count;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        String table = getTableName(uri);

        int count = mDb.update(table, contentValues, s, strings);
        if (count > 0)
            mContext.getContentResolver().notifyChange(uri, null);
        return count;
    }

    private String getTableName(Uri uri) {
        String tableName = null;
        switch (sUriMatcher.match(uri)) {
            case BOOK_CODE:
                tableName = BookHelper.BOOK_TABLE_NAME;
                break;
            case USER_CODE:
                tableName = BookHelper.USER_TABLE_NAME;
                break;
            default:
                break;
        }
        Log.i("wak", "heheh" + tableName);
        return tableName;
    }
}
