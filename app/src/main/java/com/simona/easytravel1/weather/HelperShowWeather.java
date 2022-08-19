package com.simona.easytravel1.weather;

import com.simona.easytravel1.BuildConfig;
import com.simona.easytravel1.db.MyInterceptor;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HelperShowWeather {

    private static Retrofit retrofitt;

    public static final String BASE = "https://api.openweathermap.org/data/2.5/";

    private static OkHttpClient.Builder getClientWeather() {
        OkHttpClient.Builder client = new OkHttpClient.Builder();
        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            client.addInterceptor(interceptor);
        }
        return client;
    }

    public static Retrofit generateRetrofit() {
        if (retrofitt == null) {
            OkHttpClient.Builder httpClient = getClientWeather();
            httpClient.addInterceptor(new MyInterceptor());

            retrofitt = new Retrofit.Builder()
                    .baseUrl(BASE)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.build())
                    .build();
        }
        return retrofitt;
    }





}
