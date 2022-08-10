package com.simona.easytravel1.weather;

import com.simona.easytravel1.BuildConfig;
import com.simona.easytravel1.dataBaseRelated.QueryInterceptor;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HelperShowWeather {

    private static Retrofit retrofitt;

    public static final String BASE = "https://api.openweathermap.org/data/2.5/";

    public static Retrofit generateRetrofit() {
        if (retrofitt == null) {
            OkHttpClient.Builder httpClient = getClientVreme();
            httpClient.addInterceptor(new QueryInterceptor());

            retrofitt = new Retrofit.Builder()
                    .baseUrl(BASE)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.build())
                    .build();
        }

        return retrofitt;
    }


    private static OkHttpClient.Builder getClientVreme() {
        OkHttpClient.Builder client = new OkHttpClient.Builder();
        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            client.addInterceptor(interceptor);

        }
        return client;
    }

}
