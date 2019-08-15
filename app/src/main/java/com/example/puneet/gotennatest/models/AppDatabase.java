package com.example.puneet.gotennatest.models;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.puneet.gotennatest.DataDao;

@Database(entities = {Data.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase instance;

    // Singleton pattern
    public static AppDatabase getDatabase(Context context) {
        if (instance == null) {
            instance =
                    Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "data_db")
                            .build();
        }
        return instance;
    }

    public abstract DataDao dataDao();

}
