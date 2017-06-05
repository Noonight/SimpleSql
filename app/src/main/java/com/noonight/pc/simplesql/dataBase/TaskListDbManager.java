package com.noonight.pc.simplesql.dataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.noonight.pc.simplesql.dataBase.contractsDB.TaskListContract.TaskList;

/**
 * Created by PC on 5/26/2017.
 */

public class TaskListDbManager {

    final String LOG_TAG = "DataBase|Manager|Log: ";

    private TaskListDbHelper mDbHelper;

    public SQLiteDatabase getDb() {
        return db;
    }

    private SQLiteDatabase db;

    public TaskListDbManager(Context context) {
        mDbHelper = new TaskListDbHelper(context);
    }

    public void insert(ContentValues inValues) {
        writeDb();
        Log.d(LOG_TAG, "Insert: " + inValues.get(TaskList.COLUMN_NAME_TITLE) + ", " + inValues.get(TaskList.COLUMN_NAME_DATA));
        db.insert(
                TaskList.TABLE_NAME,
                TaskList.COLUMN_NAME_NULLABLE,
                inValues
        );
    }

    public long insert(ContentValues inValues, int a) {
        writeDb();
        Log.d(LOG_TAG, "Insert: " + inValues.get(TaskList.COLUMN_NAME_TITLE) + ", " + inValues.get(TaskList.COLUMN_NAME_DATA));
        long rowId = db.insert(
                TaskList.TABLE_NAME,
                TaskList.COLUMN_NAME_NULLABLE,
                inValues
        );
        return rowId;
    }

    public Cursor read() {
        readDb();
        Log.d(LOG_TAG, "Read: ");
        String[] projection = {
                TaskList._ID,
                TaskList.COLUMN_NAME_TITLE,
                TaskList.COLUMN_NAME_DATA
        };
        Cursor c = db.query(
                TaskList.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null
        );
        //
        if (c.moveToFirst()) {
            int id = c.getColumnIndex(TaskList._ID);
            //int task_id = c.getColumnIndex(TaskList.COLUMN_NAME_TASK_ID);
            int title = c.getColumnIndex(TaskList.COLUMN_NAME_TITLE);
            int data = c.getColumnIndex(TaskList.COLUMN_NAME_DATA);
            do {
                Log.d(
                        LOG_TAG,
                        "_id = " + c.getInt(id) +
                        //", task id = " + c.getString(task_id) +
                        ", title = " + c.getString(title) +
                        ", data = " + c.getString(data)
                );
            } while (c.moveToNext());
        } else {
            Log.d(LOG_TAG, "0 rows");
        }
        //
        return c;
    }



    public Cursor getRow(int rowId) {
        readDb();
        String[] projection = {
                TaskList._ID,
                TaskList.COLUMN_NAME_TITLE,
                TaskList.COLUMN_NAME_DATA
        };
        String selection = TaskList._ID + " LIKE ?";
        String[] selectionArgs = { String.valueOf(rowId) };
        Cursor c = db.query(
                TaskList.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );
        //
        c.moveToFirst();
        int id = c.getColumnIndex(TaskList._ID);
        int title = c.getColumnIndex(TaskList.COLUMN_NAME_TITLE);
        int data = c.getColumnIndex(TaskList.COLUMN_NAME_DATA);
        Log.d(
                LOG_TAG,
                "_id = " + c.getInt(id) +
                ", title = " + c.getString(title) +
                ", data = " + c.getString(data)
        );
        //
        return c;
    }

    public void update(int rowId, ContentValues inValues) {
        readDb();
        Log.d(LOG_TAG, "Update: " + rowId);
        String selection = TaskList._ID + " LIKE ?";
        String[] selectionArgs = {
                String.valueOf(rowId)
        };
        db.update(
                TaskList.TABLE_NAME,
                inValues,
                selection,
                selectionArgs
        );
    }

    public void delete(int rowId) {
        writeDb();
        Log.d(LOG_TAG, "Delete: " + rowId);
        String selection = TaskList._ID + " LIKE ?";
        String[] selectionArgs = {
                String.valueOf(rowId)
        };
        db.delete(
                TaskList.TABLE_NAME,
                selection,
                selectionArgs
        );
    }

    public void upgradeDataBase() {
        mDbHelper.onUpgrade(db, 0, 0);
    }

    public void close() {
        Log.d(LOG_TAG, "Close DBHelper");
        if (mDbHelper != null)
            mDbHelper.close();
    }

    private void readDb() {
        Log.d(LOG_TAG, "getReadableDatabase");
        db = mDbHelper.getReadableDatabase();
    }

    private void writeDb() {
        Log.d(LOG_TAG, "getWritableDatabase");
        db = mDbHelper.getWritableDatabase();
    }
}
