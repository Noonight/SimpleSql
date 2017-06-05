package com.noonight.pc.simplesql.view;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.RequiresApi;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.noonight.pc.simplesql.R;
import com.noonight.pc.simplesql.dataBase.TaskListDbManager;
import com.noonight.pc.simplesql.dataBase.contractsDB.TaskListContract.TaskList;
import com.noonight.pc.simplesql.view.fragments.SettingFragment;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    public static final int CONTEXT_MENU_DELETE_ID = 1;
    public static final int CONTEXT_MENU_UPDATE_ID = 2;

    private Button btnAdd;
    private ListView lvTasks;

    private TaskListDbManager dbManager;
    private SimpleCursorAdapter scAdapter;

    final String LOG_TAG = "MainActivityLog: ";

    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);

        btnAdd = (Button) findViewById(R.id.btnAdd);
        lvTasks = (ListView) findViewById(R.id.lvTasks);

        dbManager = new TaskListDbManager(getBaseContext());

        String[] from = new String[]{
                TaskList.COLUMN_NAME_TITLE,
                TaskList.COLUMN_NAME_DATA
        };
        int[] to = new int[]{
                R.id.tvTitle,
                R.id.tvData
        };
        scAdapter = new SimpleCursorAdapter(this, R.layout.item, null, from, to);
        lvTasks.setAdapter(scAdapter);

        registerForContextMenu(lvTasks);

        getSupportLoaderManager().initLoader(0, null, this);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*ContentValues cv = new ContentValues();
                cv.put(TaskList.COLUMN_NAME_TITLE, "New Title");
                cv.put(TaskList.COLUMN_NAME_DATA, "New Data " + scAdapter.getCount());
                dbManager.insert(cv);
                getSupportLoaderManager().getLoader(0).forceLoad();*/
                openAddActivity();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_activity_menu, menu);
        /*MenuItem menuItem = menu.add(0, 1, 0, "Setting");
        menuItem.setIntent(new Intent(this, SettingFragment.class));*/
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.retroFitGetRequest:
                intent = new Intent(this, TasksRequestAcrivity.class);
                startActivity(intent);
                return true;
            case R.id.preferShow:
                intent = new Intent(this, SettingActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
        //return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        dbManager.close();
        super.onDestroy();
    }

    private void openAddActivity(int rowId) {
        intent = new Intent(MainActivity.this, AddActivity.class);
        //String rowID = String.valueOf(rowId);
        intent.putExtra(TaskList._ID, String.valueOf(rowId));
        //Log.d(LOG_TAG, "" + rowId);
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        getSupportLoaderManager().getLoader(0).forceLoad();
        super.onStart();
    }

    private void openAddActivity() {
        intent = new Intent(MainActivity.this, AddActivity.class);
        startActivity(intent);
    }

    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(0, CONTEXT_MENU_DELETE_ID, 0, R.string.delete_record);
        menu.add(0, CONTEXT_MENU_UPDATE_ID, 0, R.string.update_record);
    }

    public boolean onContextItemSelected(MenuItem item) {
        if (item.getItemId() == CONTEXT_MENU_DELETE_ID) {

            AdapterView.AdapterContextMenuInfo acmi = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

            dbManager.delete((int) acmi.id);

            getSupportLoaderManager().getLoader(0).forceLoad();
            return true;
        } else if (item.getItemId() == CONTEXT_MENU_UPDATE_ID) {

            AdapterView.AdapterContextMenuInfo acmi = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

            openAddActivity((int) acmi.id);

            getSupportLoaderManager().getLoader(0).forceLoad();
            return true;
        }
        return super.onContextItemSelected(item);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new MyCursorLoader(this, dbManager);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        scAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    static class MyCursorLoader extends CursorLoader {

        TaskListDbManager db;

        public MyCursorLoader(Context context, TaskListDbManager db) {
            super(context);
            this.db = db;
        }

        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public Cursor loadInBackground() {
            Cursor c = db.read();
            try {
                Thread.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return c;
        }
    }
}
