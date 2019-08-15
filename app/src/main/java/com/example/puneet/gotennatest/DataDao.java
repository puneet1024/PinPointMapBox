package com.example.puneet.gotennatest;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.puneet.gotennatest.models.Data;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface DataDao {

    @Insert(onConflict = REPLACE)
    void addData(Data data);

}
