package com.noonight.pc.simplesql.retroFit.service;

import com.noonight.pc.simplesql.retroFit.model.Tasks;

import java.util.List;

import retrofit.http.GET;
import retrofit2.Call;

/**
 * Created by PC on 5/29/2017.
 */

public interface GETAPIService {
    @GET("api/tasks")
    Call<List<Tasks>> getTasks();
}
