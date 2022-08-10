package com.simona.easytravel1.showPossibleLocations;

import com.simona.easytravel1.BuildConfig;
import com.simona.easytravel1.dataBaseRelated.QueryInterceptor;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HelperAutocompleteLocations {

    private static Retrofit retrofitLocations;
    private static final String BASE = "https://maps.googleapis.com/maps/api/place/autocomplete/";


    public static Retrofit generateRetrofitt(){
        if (retrofitLocations == null){
            OkHttpClient.Builder httpClientt = getHttpClient();
            httpClientt.addInterceptor(new QueryInterceptor());

            retrofitLocations = new Retrofit.Builder()
                    .baseUrl(BASE)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClientt.build())
                    .build();
        }
        return retrofitLocations;
    }

    private static OkHttpClient.Builder getHttpClient(){
        OkHttpClient.Builder client = new OkHttpClient.Builder();
        if (BuildConfig.DEBUG){
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            client.addInterceptor(interceptor);
        }
        return client;
    }

}
