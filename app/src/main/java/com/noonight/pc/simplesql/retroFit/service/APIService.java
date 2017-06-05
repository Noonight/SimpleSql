package com.noonight.pc.simplesql.retroFit.service;

import com.noonight.pc.simplesql.dataBase.contractsDB.TaskListContract.TaskList;
import com.noonight.pc.simplesql.retroFit.model.Tasks;

import java.util.List;

import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit2.Call;
import retrofit2.http.Field;

/**
 * Created by PC on 5/29/2017.
 */

public interface APIService {
    @GET("api/tasks")
    retrofit.Call<List<Tasks>> getTasks();

    @FormUrlEncoded
    @POST("api/tasks/insert.php")
    retrofit.Call<Tasks> insertTasks(
            @Field(TaskList._ID) String _id,
            @Field(TaskList.COLUMN_NAME_TITLE) String title,
            @Field(TaskList.COLUMN_NAME_DATA) String data
    );

    @FormUrlEncoded
    @POST("api/tasks/delete.php")
    Call<Tasks> deleteTasks(
            @Field(TaskList._ID) String _id
    );

    @FormUrlEncoded
    @POST("api/tasks/update.php")
    Call<Tasks> updateTasks(
            @Field(TaskList._ID) String _id,
            @Field(TaskList.COLUMN_NAME_TITLE) String title,
            @Field(TaskList.COLUMN_NAME_DATA) String data
    );
}
