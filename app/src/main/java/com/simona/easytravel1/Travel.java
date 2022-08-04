package com.simona.easytravel1;

import androidx.lifecycle.LiveData;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "generalTravelList")
public class Travel { //extends LiveData<Travel> {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String travelName;
    private String destination;
    private String typeOfTravel;
    private int price;
    private String startDate;
    private String endDate;
    private int rating;
    private String uriPicture;

    @ColumnInfo(name = "columnFavorite")
    private boolean favorite;

    public Travel(String travelName, String destination, String typeOfTravel, int price,
                  String startDate, String endDate, int rating,
                  String uriPicture, boolean favorite) {
        this.travelName = travelName;
        this.destination = destination;
        this.typeOfTravel = typeOfTravel;
        this.price = price;
        this.startDate = startDate;
        this.endDate = endDate;
        this.rating = rating;
        this.uriPicture = uriPicture;
        this.favorite = favorite;
    }

    public void changeFavorit(){
        favorite = !favorite;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTravelName() {
        return travelName;
    }

    public String getDestination() {
        return destination;
    }

    public String getTypeOfTravel() {
        return typeOfTravel;
    }

    public int getPrice() {
        return price;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public int getRating() {
        return rating;
    }

    public String getUriPicture() {
        return uriPicture;
    }




}
