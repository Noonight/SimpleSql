package com.noonight.pc.simplesql.dataBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.noonight.pc.simplesql.dataBase.contractsDB.TaskListContract.TaskList;

/**
 * Created by PC on 5/26/2017.
 */

public class TaskListDbHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "TaskList.db";
    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ", ";
    private static final String SQL_CREATE_TASK =
            "CREATE TABLE " + TaskList.TABLE_NAME + " (" +
                    TaskList._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    TaskList.COLUMN_NAME_TITLE + TEXT_TYPE + COMMA_SEP +
                    TaskList.COLUMN_NAME_DATA + TEXT_TYPE +
            " )";
    private static final String SQL_DELETE_TASK =
            "DROP TABLE IF EXISTS " + TaskList.TABLE_NAME;
    
    public TaskListDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TASK);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_TASK);
        onCreate(db);
    }
}
