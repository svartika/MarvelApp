package com.perpy.marvelapp;

import android.content.Context;

import com.perpy.networkcontroller.MarvelRetrofitEndpointApi;
import com.perpy.networkcontroller.QueryInterceptor;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ApplicationComponent;
import dagger.hilt.android.qualifiers.ApplicationContext;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.perpy.networkcontroller.SharedVars.BASE_URL;


@Module
@InstallIn(ApplicationComponent.class)
public class InjectionModule2 {


    @Provides
    public FlipperSetter createSetter() {
        return new FlipperSetter() {
            @Override
            public void setup() {

            }
        };
    }

    @Provides
    @Singleton
    public Interceptor createNetworkFlipperIntercepter () {
        return new Interceptor() {
            @NotNull
            @Override
            public Response intercept(@NotNull Chain chain) throws IOException {
                return chain.proceed(chain.request());
            }
        };
    }

    @Provides
    public Retrofit bindRetrofit(Interceptor networkFlipperInterceptor, @ApplicationContext Context context) {
        Gson gson = new GsonBuilder().setLenient().create();
        //RxJava2CallAdapterFactory rxAdapter = RxJava2CallAdapterFactory.create();
        RxJava2CallAdapterFactory rxAdapter = RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io());
        //OkHttpClient okHttpClient = new OkHttpClient();
        //okHttpClient.interceptors().add(new QueryInterceptor());
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.level(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder()
                .addInterceptor(new QueryInterceptor())
                .addInterceptor(loggingInterceptor);
        if (networkFlipperInterceptor != null) {
            //.addNetworkInterceptor(new FlipperOkhttpInterceptor(AndroidFlipperClient.getInstance(context).getPlugin(NetworkFlipperPlugin.ID)))
            okHttpClientBuilder.addNetworkInterceptor(networkFlipperInterceptor);
        }

        OkHttpClient okHttpClient = okHttpClientBuilder.build();
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(rxAdapter)
                .build();
    }



    @Provides
    public MarvelRetrofitEndpointApi getEndPoint( Retrofit retrofit) {
        return retrofit.create(MarvelRetrofitEndpointApi.class);
    }


}
