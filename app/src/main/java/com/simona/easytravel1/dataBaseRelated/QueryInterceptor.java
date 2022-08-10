package com.simona.easytravel1.dataBaseRelated;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class QueryInterceptor implements Interceptor {

//    private final String name;
//    private final String value;
//
//    public QueryInterceptor(String name, String value){
//        this.name = name;
//        this.value = value;
//    }

    @NotNull
    @Override
    public Response intercept(@NotNull Chain chain) throws IOException {
        Request requestComun = chain.request();
        HttpUrl.Builder urlBuilder = requestComun.url().newBuilder();
     //   urlBuilder.addQueryParameter(name, value);

        Request requestPers = requestComun
                .newBuilder()
                .url(urlBuilder.build())
                .build();

        return chain.proceed(requestPers);
    }



}





