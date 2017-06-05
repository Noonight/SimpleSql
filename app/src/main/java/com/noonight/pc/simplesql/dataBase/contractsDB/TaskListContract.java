package com.noonight.pc.simplesql.dataBase.contractsDB;

import android.provider.BaseColumns;

/**
 * Created by PC on 5/26/2017.
 */

public final class TaskListContract {
    public TaskListContract() {
    }

    public static abstract class TaskList implements BaseColumns {
        public static final String TABLE_NAME = "tasklist";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_DATA = "data";
        public static final String COLUMN_NAME_NULLABLE = "null";
    }
}
