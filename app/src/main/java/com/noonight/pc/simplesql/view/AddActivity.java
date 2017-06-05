package com.noonight.pc.simplesql.view;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.noonight.pc.simplesql.R;
import com.noonight.pc.simplesql.dataBase.TaskListDbManager;
import com.noonight.pc.simplesql.dataBase.contractsDB.TaskListContract.TaskList;
import com.noonight.pc.simplesql.retroFit.RetroFitManager;

public class AddActivity extends AppCompatActivity {

    public static final int ROW_ID = 00;
    private EditText etAddTitle;
    private EditText etAddData;
    private Button btnSave;

    private TaskListDbManager dbManager;
    private ContentValues cv;
    private Cursor c;

    private int rowId = ROW_ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        init();
    }

    private void init() {
        etAddData = (EditText) findViewById(R.id.etAddData);
        etAddTitle = (EditText) findViewById(R.id.etAddTitle);
        btnSave = (Button) findViewById(R.id.btnSave);

        dbManager = new TaskListDbManager(this);
        //new Toast(this).makeText(this, "" + getIntent().getStringExtra(TaskList._ID), Toast.LENGTH_SHORT).show();
        Log.d("TAGLOGA", "" + getIntent().getStringExtra(TaskList._ID));
        //getIntent().getStringExtra(TaskList._ID);
        if (getIntent().getStringExtra(TaskList._ID) != null && !getIntent().getStringExtra(TaskList._ID).isEmpty()) {
            rowId = Integer.parseInt(getIntent().getStringExtra(TaskList._ID));
            c = dbManager.getRow(rowId);
            int title = c.getColumnIndex(TaskList.COLUMN_NAME_TITLE);
            int data = c.getColumnIndex(TaskList.COLUMN_NAME_DATA);
            etAddTitle.setText(c.getString(title));
            etAddData.setText(c.getString(data));
        } else {

        }

        //final RetroFitManager retroFitManager = new RetroFitManager(this);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cv = new ContentValues();
                cv.put(TaskList.COLUMN_NAME_TITLE, etAddTitle.getText().toString());
                cv.put(TaskList.COLUMN_NAME_DATA, etAddData.getText().toString());
                if (rowId != ROW_ID) {
                    dbManager.update(rowId, cv);
                    //getIntent().getStringExtra()
                } else {
                    long rowId = dbManager.insert(cv, 1);
                    //retroFitManager.insertTasks((int) rowId, etAddTitle.getText().toString(), etAddData.getText().toString());
                }
                finish();
            }
        });
    }
}
