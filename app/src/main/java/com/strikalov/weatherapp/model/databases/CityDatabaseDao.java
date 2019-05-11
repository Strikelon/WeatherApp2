package com.strikalov.weatherapp.model.databases;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
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
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(CityEntity city);

    /**
     * Добавление в базу данных списка городов
     * @param cities
     * @return
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
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

    /**
     * Получение из базы данных всех городов, с нужной отметкой избранное/не избранное
     * @param favoriteValue
     * @return
     */
    @Query("SELECT * FROM cityentity where isFavorites = :favoriteValue")
    Maybe<List<CityEntity>> getCitiesByFavoriteValue(int favoriteValue);

    /**
     * Обновление у города с определенным индексом отметки, избранное/не избранное
     * @param desiredCityIndex
     * @param favoriteValue
     * @return
     */
    @Query("UPDATE cityentity SET isFavorites = :favoriteValue WHERE cityIndex = :desiredCityIndex")
    int updateIsFavoritesByCityIndex(String desiredCityIndex, int favoriteValue);

}
