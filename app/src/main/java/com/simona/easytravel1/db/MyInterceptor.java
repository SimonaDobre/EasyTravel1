package com.simona.easytravel1.db;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class MyInterceptor implements Interceptor {

    @NotNull
    @Override
    public Response intercept(@NotNull Chain chain) throws IOException {
        Request generalRequest = chain.request();
        HttpUrl.Builder urlBuilder = generalRequest.url().newBuilder();

        Request requestPers = generalRequest
                .newBuilder()
                .url(urlBuilder.build())
                .build();

        return chain.proceed(requestPers);
    }



}





