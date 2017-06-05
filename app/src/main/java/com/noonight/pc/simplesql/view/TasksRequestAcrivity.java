package com.noonight.pc.simplesql.view;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.noonight.pc.simplesql.R;
import com.noonight.pc.simplesql.dataBase.TaskListDbManager;
import com.noonight.pc.simplesql.retroFit.model.Tasks;
import com.noonight.pc.simplesql.retroFit.service.APIService;

import org.w3c.dom.Text;

import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class TasksRequestAcrivity extends AppCompatActivity {

    public static final String BASE_URL = /*"http://ayura96.000webhostapp.com/"*/"http://10.0.2.2/";

    //private SharedPreferences sp;

    private Button btnGetData;
    private TextView tvDetails;

    private Retrofit retrofit;
    private ProgressDialog pDialog;
    private APIService apiService;
    private TaskListDbManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks_request_acrivity);
        init();
    }

    private void init() {
        btnGetData = (Button) findViewById(R.id.btnGetData);
        tvDetails = (TextView) findViewById(R.id.tvDetails);

        //sp = PreferenceManager.getDefaultSharedPreferences(this);
        //String tmp = sp.getString("address", "");
        //BASE_URL = tmp;
        pDialog = new ProgressDialog(this);
        //dbManager = new TaskListDbManager(this);
        new Toast(this).makeText(this, "BASE_URL: " + BASE_URL, Toast.LENGTH_LONG).show();
        btnGetData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getTasks();
            }
        });
    }

    private void getTasks() {
        showpDialog();
        try {
            apiService = getAPIService();

            Call<List<Tasks>> call = apiService.getTasks();

            call.enqueue(new Callback<List<Tasks>>() {
                @Override
                public void onResponse(Response<List<Tasks>> response, Retrofit retrofit) {
                    List<Tasks> peopleData = response.body();
                    String details = "";
                    for (int i = 0; i < peopleData.size(); i++) {
                        String id = peopleData.get(i).get_id();
                        String title = peopleData.get(i).getTitle();
                        String data = peopleData.get(i).getData();
                        details +=
                                "ID: " + id + "\n" +
                                "Title: " + title + "\n" +
                                "Data: " + data + "\n\n";
                        Log.d("LOG", "ID: " + id + "\n" +
                                "Title: " + title + "\n" +
                                "Data: " + data + "\n\n");
                    }

                    //Toast.makeText(MainActivity.this, details, Toast.LENGTH_SHORT).show();
                    tvDetails.setText(details);
                    hidepDialog();
                }

                @Override
                public void onFailure(Throwable t) {
                    Log.d("onFailure", t.toString());
                    hidepDialog();
                }
            });
        } catch (Exception e) {
            Log.d("onResponse", "There is an error");
            e.printStackTrace();
            hidepDialog();
        }
    }

    /*private String getTasks() {
        String details = "";
        showpDialog();
        try {
            apiService = getAPIService();

            Call<List<Tasks>> call = apiService.getTasks();

            call.enqueue(new Callback<List<Tasks>>() {
                @Override
                public void onResponse(Response<List<Tasks>> response, Retrofit retrofit) {
                    List<Tasks> peopleData = response.body();
                    //String details = "";
                    for (int i = 0; i < peopleData.size(); i++) {
                        String id = peopleData.get(i).get_id();
                        String name = peopleData.get(i).getTitle();

                        details += "ID: " + id + "\n" +
                                "Name: " + name + "\n\n";


                    }
                    //Toast.makeText(MainActivity.this, details, Toast.LENGTH_SHORT).show();

                    //textDetails.setText(details);
                    hidepDialog();
                }

                @Override
                public void onFailure(Throwable t) {
                    Log.d("onFailure", t.toString());
                    hidepDialog();
                }
            });
        } catch (Exception e) {
            Log.d("onResponse", "There is an error");
            e.printStackTrace();
            hidepDialog();
        }
        return details;
    }*/

    private APIService getAPIService() {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        final APIService mAPIService = retrofit.create(APIService.class);
        return mAPIService;
    }


    private void showpDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hidepDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }
}
