package com.example.rxjavaretrofittest;

import android.app.Application;


import com.facebook.flipper.android.AndroidFlipperClient;
import com.facebook.flipper.android.utils.FlipperUtils;
import com.facebook.flipper.core.FlipperClient;
import com.facebook.flipper.plugins.inspector.DescriptorMapping;
import com.facebook.flipper.plugins.inspector.InspectorFlipperPlugin;
import com.facebook.flipper.plugins.network.NetworkFlipperPlugin;
import com.facebook.soloader.SoLoader;

import javax.inject.Inject;

import dagger.hilt.android.HiltAndroidApp;

@HiltAndroidApp
public class MarvelApplication extends Application {
    @Inject
    NetworkFlipperPlugin networkFlipperPlugin;
    @Override
    public void onCreate() {
        super.onCreate();
        SoLoader.init(this, false);
        if(BuildConfig.DEBUG && FlipperUtils.shouldEnableFlipper(this)) {
            final FlipperClient client = AndroidFlipperClient.getInstance(this);
            client.addPlugin(new InspectorFlipperPlugin(this, DescriptorMapping.withDefaults()));

            client.addPlugin(networkFlipperPlugin);
            client.start();
        }
    }
}
