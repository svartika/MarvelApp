package com.example.rxjavaretrofittest;

import android.content.Context;

import com.example.networkcontroller.MarvelRetrofitEndpointApi;
import com.example.networkcontroller.QueryInterceptor;
import com.facebook.flipper.android.AndroidFlipperClient;
import com.facebook.flipper.plugins.network.FlipperOkhttpInterceptor;
import com.facebook.flipper.plugins.network.NetworkFlipperPlugin;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ApplicationComponent;
import dagger.hilt.android.qualifiers.ApplicationContext;
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

    @Provides
    @Singleton
    public NetworkFlipperPlugin createNetworkFlipperPlugin () {
        return new NetworkFlipperPlugin();
    }

    @Marvel
    @Provides
    public Retrofit bindRetrofit(NetworkFlipperPlugin networkFlipperPlugin, @ApplicationContext Context context) {
        Gson gson = new GsonBuilder().setLenient().create();
        //RxJava2CallAdapterFactory rxAdapter = RxJava2CallAdapterFactory.create();
        RxJava2CallAdapterFactory rxAdapter = RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io());
        //OkHttpClient okHttpClient = new OkHttpClient();
        //okHttpClient.interceptors().add(new QueryInterceptor());
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.level(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new QueryInterceptor())
                .addInterceptor(loggingInterceptor)
                //.addNetworkInterceptor(new FlipperOkhttpInterceptor(AndroidFlipperClient.getInstance(context).getPlugin(NetworkFlipperPlugin.ID)))
                .addNetworkInterceptor(new FlipperOkhttpInterceptor(networkFlipperPlugin))
                .build();
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(rxAdapter)
                .build();
    }


    @Provides
    public MarvelRetrofitEndpointApi getEndPoint(@Marvel Retrofit retrofit) {
        return retrofit.create(MarvelRetrofitEndpointApi.class);
    }

}
