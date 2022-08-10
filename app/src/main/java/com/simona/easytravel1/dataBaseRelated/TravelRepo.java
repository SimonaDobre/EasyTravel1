package com.simona.easytravel1.dataBaseRelated;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class TravelRepo {

    private TravelDAO traveLDAO;
    private LiveData<List<Travel>> arrayAllTravels;
    private LiveData<List<Travel>> arrayFavoriteTravels;
    private Travel oneTravelToBeShared;



    public TravelRepo(Application application) {
        TravelRoomDataBase rbd = TravelRoomDataBase.generateDBinstance(application);
        traveLDAO = rbd.travelDAOmethodFromRoomDB();
        arrayAllTravels = traveLDAO.getAllRegisteredTravels();
        arrayFavoriteTravels = traveLDAO.getAllFavoriteTravels();

    }

    public LiveData<List<Travel>> getAllTravelsRepo() {
        return arrayAllTravels;
    }

    public LiveData<List<Travel>> getFavoriteTravels(){
        return arrayFavoriteTravels;
    }



    public void insertRepo(Travel t) {
        insertNewTravel(t);
    }

    public void updateRepo(Travel t) {
        updateTravel(t);
    }

    public void deleteRepo(Travel t) {
        deleteTravel(t);
    }

    public void deleteAllRepo() {
        deleteAllTravels();
    }



    private void insertNewTravel(Travel newTravel) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                traveLDAO.insertNewTravel(newTravel);
            }
        });
        thread.start();
    }

    private void updateTravel(Travel newTravel) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                traveLDAO.updateTravel(newTravel);
            }
        });
        thread.start();
    }

    private void deleteTravel(Travel newTravel) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                traveLDAO.deleteAtravel(newTravel);
            }
        });
        thread.start();
    }

    private void deleteAllTravels() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                traveLDAO.deleteAllTravels();
            }
        });
        thread.start();
    }



}
