package com.simona.easytravel1;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface TraveLDAO {

    @Insert(onConflict = REPLACE)
    void insertNewTravel(Travel travel);

    @Update
    void updateTravel(Travel travel);

    @Delete
    void deleteAtravel(Travel travel);

    @Query("DELETE FROM generalTravelList")
    void deleteAllTravels();


    @Query("SELECT * FROM generalTravelList")
    LiveData<List<Travel>> getAllRegisteredTravels();

    @Query("SELECT * FROM generalTravelList WHERE columnFavorite==1")
    LiveData<List<Travel>> getAllFavoriteTravels();

    @Query("SELECT * FROM generalTravelList WHERE id = :idToBeShared LIMIT 1")
   Travel getOneTravelByID(int idToBeShared);

}
