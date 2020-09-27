package com.example.networkcontroller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MarvelRetrofitBuilder {
    public static final String BASE_URL = "https://gateway.marvel.com:443/";
    public static Retrofit retrofit = null;
    public static MarvelRetrofitEndpointApi retrofitEndpointApi = null;

    static void setupRetrofit() {
        if (retrofit == null) {
            Gson gson = new GsonBuilder().setLenient().create();
            //RxJava2CallAdapterFactory rxAdapter = RxJava2CallAdapterFactory.create();
            RxJava2CallAdapterFactory rxAdapter = RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io());
            //OkHttpClient okHttpClient = new OkHttpClient();
            //okHttpClient.interceptors().add(new QueryInterceptor());
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.level(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor(new QueryInterceptor()).addInterceptor(loggingInterceptor).build();
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(rxAdapter)
                    .build();
        }
        createRetrofitEndpointApi();
    }

    static void createRetrofitEndpointApi() {
        retrofitEndpointApi = retrofit.create(MarvelRetrofitEndpointApi.class);
    }

}
