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

    /**
     * Добавление в базу данных нового города
     * @param city
     * @return
     */
    @Insert
    Long insert(CityEntity city);

    /**
     * Добавление в базу данных списка городов
     * @param cities
     * @return
     */
    @Insert
    Long[] insertList(List<CityEntity> cities);

    /**
     * Обновление информации о городе в базе данных
     * @param city
     * @return
     */
    @Update
    int update(CityEntity city);

    /**
     * Удаление города из базы данных
     * @param city
     * @return
     */
    @Delete
    int delete(CityEntity city);

    /**
     * Получение всех городов из базы данных
     * @return
     */
    @Query("SELECT * FROM cityentity")
    Maybe<List<CityEntity>> getAllCities();

}
