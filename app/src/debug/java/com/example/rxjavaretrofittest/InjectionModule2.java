package com.example.rxjavaretrofittest;

import android.content.Context;
import com.facebook.soloader.SoLoader;

import com.example.networkcontroller.MarvelRetrofitEndpointApi;
import com.example.networkcontroller.QueryInterceptor;
import com.facebook.flipper.android.AndroidFlipperClient;
import com.facebook.flipper.android.utils.FlipperUtils;
import com.facebook.flipper.core.FlipperClient;
import com.facebook.flipper.plugins.inspector.DescriptorMapping;
import com.facebook.flipper.plugins.inspector.InspectorFlipperPlugin;
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
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.networkcontroller.SharedVars.BASE_URL;

@Module
@InstallIn(ApplicationComponent.class)
public class InjectionModule2 {

    @Singleton
    @Provides
    public NetworkFlipperPlugin createNetworkFlipperPlugin() {
        return new NetworkFlipperPlugin();
    }


    @Provides
    @Singleton
    public Interceptor createNetworkFlipperIntercepter (NetworkFlipperPlugin networkFlipperPlugin) {
        return new FlipperOkhttpInterceptor(networkFlipperPlugin);
    }


    @Provides
    public FlipperSetter createSetter(@ApplicationContext Context context, NetworkFlipperPlugin networkFlipperPlugin) {
        return new FlipperSetter() {
            @Override
            public void setup() {
                SoLoader.init(context, false);
                if(BuildConfig.DEBUG && FlipperUtils.shouldEnableFlipper(context)) {
                    final FlipperClient client = AndroidFlipperClient.getInstance(context);
                    client.addPlugin(new InspectorFlipperPlugin(context, DescriptorMapping.withDefaults()));

                    client.addPlugin(networkFlipperPlugin);
                    client.start();
                }
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
