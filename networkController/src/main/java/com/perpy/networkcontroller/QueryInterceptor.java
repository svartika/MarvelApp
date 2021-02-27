package com.perpy.networkcontroller;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class QueryInterceptor implements Interceptor {
    Hasher hasher = new MD5();
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        long timestamp = System.currentTimeMillis();
        String hashedTimeStamp = hashedTimeStamp(timestamp);
        HttpUrl url = request.url().newBuilder()
                .addQueryParameter(SharedVars.API_KEY, SharedVars.PUBLIC_KEY)
                .addQueryParameter(SharedVars.TS, String.valueOf(timestamp))
                .addQueryParameter(SharedVars.HASH, hashedTimeStamp)
                .build();
        request = request.newBuilder().url(url).build();
        return chain.proceed(request);
    }

    private String hashedTimeStamp(long timestamp) {
        String toHash = String.valueOf(timestamp) + SharedVars.PRIVATE_KEY + SharedVars.PUBLIC_KEY;
        return hasher.hashcode(toHash);
    }
}
