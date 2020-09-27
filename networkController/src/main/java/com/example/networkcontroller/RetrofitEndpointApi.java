package com.example.networkcontroller;

import com.example.entitiy.models.Change;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RetrofitEndpointApi {
    @GET("changes/")
    Observable<List<Change>> loadChanges(@Query("q") String status);
}
