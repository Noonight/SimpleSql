package com.noonight.pc.simplesql.retroFit;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

import com.noonight.pc.simplesql.dataBase.TaskListDbManager;
import com.noonight.pc.simplesql.retroFit.model.Tasks;
import com.noonight.pc.simplesql.retroFit.service.APIService;

import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;

/**
 * Created by PC on 5/29/2017.
 */

public class RetroFitManager {

    public static final String BASE_URL = "http://10.0.2.2";
    private retrofit.Retrofit retrofit;
    private ProgressDialog pDialog;
    private APIService apiService;
    private TaskListDbManager dbManager;

    public RetroFitManager(Context context) {
        pDialog = new ProgressDialog(context);
        dbManager = new TaskListDbManager(context);
    }

    public void insertTasks(int _id, String title, String data) {
        showpDialog();

        apiService = getAPIService();

        Tasks people = new Tasks();
        people.set_id(""+_id);
        people.setTitle(title);
        people.setData(data);

        Call<Tasks> peopleCall = apiService.insertTasks(people.get_id(), people.getTitle(), people.getData());

        peopleCall.enqueue(new Callback<Tasks>() {
            @Override
            public void onResponse(Response<Tasks> response, retrofit.Retrofit retrofit) {


                hidepDialog();
                Log.d("onResponse", "" + response.code() +
                        "  response body " + response.body() +
                        " responseError " + response.errorBody() + " responseMessage " +
                        response.message());
            }

            @Override
            public void onFailure(Throwable t) {
                hidepDialog();
                Log.d("onFailure", t.toString());
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
                public void onResponse(Response<List<Tasks>> response, retrofit.Retrofit retrofit) {
                    List<Tasks> peopleData = response.body();
                    String details = "";
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
        retrofit = new retrofit.Retrofit.Builder()
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
