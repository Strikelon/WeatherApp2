package com.strikalov.weatherapp.model.databases;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import io.reactivex.Maybe;

@Dao
public interface CityDatabaseDao {

    @Insert
    Long insert(CityEntity city);

    @Insert
    Long[] insertList(List<CityEntity> cities);

    @Update
    int update(CityEntity city);

    @Delete
    int delete(CityEntity city);

    @Query("SELECT * FROM cityentity")
    Maybe<List<CityEntity>> getAllCities();

}
