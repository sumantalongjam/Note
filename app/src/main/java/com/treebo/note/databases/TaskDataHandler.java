package com.treebo.note.databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.treebo.note.Utilities.KeyIDS;
import com.treebo.note.entities.TaskEntity;
import java.util.ArrayList;

/**
 * Created by sumanta on 21/7/16.
 */
public class TaskDataHandler implements KeyIDS {

    public static long addTask(Context context, TaskEntity entity) {
        SQLiteDatabase db = TreeboSqliteHelper.getWritableDatabase(context);
        try {
            ContentValues values = new ContentValues();
            values.put(TASK_TITLE, entity.getTitle());
            values.put(TASK_CONTENT, entity.getContent());
            return db.insertWithOnConflict(TASK_TABLE, null, values, SQLiteDatabase.CONFLICT_REPLACE);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static ArrayList<TaskEntity> getAllTask(Context context) {
        ArrayList<TaskEntity> taskList = new ArrayList<>();

        String selectQuery = "SELECT " + TASK_ID + "," + TASK_TITLE + "," + TASK_CONTENT + " FROM " + TASK_TABLE;
        SQLiteDatabase db = TreeboSqliteHelper.getWritableDatabase(context);
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                TaskEntity entity = new TaskEntity(cursor.getLong(0), cursor.getString(1), cursor.getString(2));
                taskList.add(entity);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return taskList;
    }

    public static void updateTask(Context context, TaskEntity entity) {
        SQLiteDatabase db = TreeboSqliteHelper.getWritableDatabase(context);
        try {
            ContentValues taskValue = new ContentValues();
            taskValue.put(TASK_TITLE, entity.getTitle());
            taskValue.put(TASK_CONTENT, entity.getContent());
            db.update(TASK_TABLE, taskValue, TASK_ID + "=" + entity.getId(), null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void deleteTask(Context context, long id) {
        SQLiteDatabase db = TreeboSqliteHelper.getWritableDatabase(context);
        try {
            db.delete(TASK_TABLE, TASK_ID + "=" + id, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
