package com.simona.easytravel1.dataBaseRelated;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class TravelViewModel extends AndroidViewModel {

    private TravelRepo travelRepo;
    private LiveData<List<Travel>> arrayAllTravels;
    private LiveData<List<Travel>> arrayFavoriteTravels;


    public TravelViewModel(@NonNull Application application) {
        super(application);
        travelRepo = new TravelRepo(application);
        arrayAllTravels = travelRepo.getAllTravelsRepo();
        arrayFavoriteTravels = travelRepo.getFavoriteTravels();

    }

    public void insertViewModel(Travel t){
        travelRepo.insertRepo(t);
    }

    public void updateViewModel(Travel t){
        travelRepo.updateRepo(t);
    }

    public void deleteViewModel(Travel t){
        travelRepo.deleteRepo(t);
    }

    public void deleteAllViewModel(){
        travelRepo.deleteAllRepo();
    }



    public LiveData<List<Travel>> getAllRegisteredTravelsViewModel(){
        return arrayAllTravels;
    }

    public LiveData<List<Travel>> getFavoriteTravels(){
        return arrayFavoriteTravels;
    }


}
