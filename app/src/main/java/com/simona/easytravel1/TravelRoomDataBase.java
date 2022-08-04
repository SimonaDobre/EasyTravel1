package com.simona.easytravel1;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Travel.class}, version = 6, exportSchema = false)
public abstract class TravelRoomDataBase extends RoomDatabase {

    public abstract TraveLDAO travelDAOmethodFromRoomDB();

    private static TravelRoomDataBase travelDataBase;

    public synchronized static TravelRoomDataBase generateDBinstance(Context context) {
        if (travelDataBase == null) {
            travelDataBase = Room.databaseBuilder(context.getApplicationContext()
                    , TravelRoomDataBase.class
                    , "bazaGenrrala")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return travelDataBase;
    }




    // populare baza cu inregistrari random
//    private static RoomDatabase.Callback roomCallbackkk = new Callback() {
//        @Override
//        public void onCreate(@NonNull SupportSQLiteDatabase db) {
//            super.onCreate(db);
//            new PopulateDbAsyncTaskkk(bazaa).execute();
//        }
//    };
//
//    private static class PopulateDbAsyncTaskkk extends AsyncTask<Void, Void, Void> {
//
//        private TraveLDAO traveLDAOOO;
//
//        public PopulateDbAsyncTaskkk(TravelRoomDataBase db) {
//            traveLDAOOO = db.traveLDAOmetodaDinRoomBazaDeDate();
//        }
//
//        @Override
//        protected Void doInBackground(Void... voids) {
//            traveLDAOOO.inserareCalatorieNoua(new Travel("nume1",
//                    "dest1", "munte", 10,
//                    "azi", "maine", 5, null, false));
//
//            traveLDAOOO.inserareCalatorieNoua(new Travel("nume2",
//                    "dest2", "mare", 20,
//                    "azi", "maine", 5, null, false));
//
//            traveLDAOOO.inserareCalatorieNoua(new Travel("nume3",
//                    "dest3", "city", 30,
//                    "azi", "maine", 5, null, false));
//
//
//            return null;
//        }
//    }

}
