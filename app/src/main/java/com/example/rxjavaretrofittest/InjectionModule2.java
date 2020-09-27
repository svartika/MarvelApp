package com.example.rxjavaretrofittest;

import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;

import com.example.networkcontroller.MarvelRetrofitEndpointApi;
import com.example.networkcontroller.QueryInterceptor;
import com.example.networkcontroller.RetrofitEndpointApi;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ApplicationComponent;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.networkcontroller.SharedVars.BASE_URL;


@Module
@InstallIn(ApplicationComponent.class)
public class InjectionModule2 {
    @Marvel
    @Provides
    public Retrofit bindRetrofit() {
        Gson gson = new GsonBuilder().setLenient().create();
        //RxJava2CallAdapterFactory rxAdapter = RxJava2CallAdapterFactory.create();
        RxJava2CallAdapterFactory rxAdapter = RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io());
        //OkHttpClient okHttpClient = new OkHttpClient();
        //okHttpClient.interceptors().add(new QueryInterceptor());
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.level(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor(new QueryInterceptor()).addInterceptor(loggingInterceptor).build();
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(rxAdapter)
                .build();
    }


    @GitCodeBase
    @Provides
    public Retrofit bindGitRetrofit() {
        Gson gson = new GsonBuilder().setLenient().create();
        //RxJava2CallAdapterFactory rxAdapter = RxJava2CallAdapterFactory.create();
        RxJava2CallAdapterFactory rxAdapter = RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io());
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(rxAdapter)
                .build();
    }

    @Provides
    public MarvelRetrofitEndpointApi getEndPoint(@Marvel Retrofit retrofit) {
        return retrofit.create(MarvelRetrofitEndpointApi.class);
    }

    @Provides
    public RetrofitEndpointApi getGitEndPoint(@GitCodeBase Retrofit retrofit) {
        return retrofit.create(RetrofitEndpointApi.class);
    }
}
