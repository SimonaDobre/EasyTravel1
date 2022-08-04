package com.simona.easytravel1.weather;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherInterface {

    // https://api.openweathermap.org/data/2.5/weather?q={city name}&appid={API key}
    // de aici, mai jos de jumatatea paginii: https://openweathermap.org/current

    @GET("weather")
    Call<Example> getWeatherForSelectedCity(
            @Query("q") String selectedCity,
            @Query("appid") String myApiKey);


}
