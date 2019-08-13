package com.example.puneet.gotennatest;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.puneet.gotennatest.models.Data;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

public interface DataDao {
    @Query("select * from Data")
    LiveData<List<Data>> getAllDataItems();

    @Query("select * from Data where id = :id")
    Data getItembyId(String id);

    @Insert(onConflict = REPLACE)
    void addData(Data data);

    @Delete
    void deleteData(Data data);
}
