package com.simona.easytravel1.showPossibleLocations;



import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ShowAllPossibleLocationsInterface {

    @GET("json")
    Call<Example> showAllPossibleLocations (
            @Query("input") String textintrodus,
            @Query("key") String apiAllPossibleLocations);


}
