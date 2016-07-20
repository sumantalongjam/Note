package com.treebo.note.databases;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.treebo.note.Utilities.KeyIDS;

/**
 * Created by sumanta on 21/7/16.
 */
public class TreeboSqliteHelper extends SQLiteOpenHelper implements KeyIDS {

    private static final String DATABASE_NAME = "treebo.db";
    private static final int DATABASE_VERSION = 1;

    private final String CREATE_TABLE_TASK = "CREATE TABLE "
            + TASK_TABLE + "(" + TASK_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + TASK_TITLE + " TEXT," + TASK_CONTENT + " TEXT);";


    private static TreeboSqliteHelper sqliteHelper;

    private TreeboSqliteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static TreeboSqliteHelper getSqliteHelper(Context context) {
        synchronized (TreeboSqliteHelper.class) {
            if (sqliteHelper == null) {
                sqliteHelper = new TreeboSqliteHelper(context);
            }
        }
        return sqliteHelper;
    }

    public static SQLiteDatabase getWritableDatabase(Context context) {
        return getSqliteHelper(context).getWritableDatabase();
    }

    public static SQLiteDatabase getReadableDatabase(Context context) {
        return getSqliteHelper(context).getReadableDatabase();
    }

    public static void closeDatabase(Context context) {
        getSqliteHelper(context).close();
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(CREATE_TABLE_TASK);
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {

    }
}