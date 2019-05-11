package com.strikalov.weatherapp.model.databases;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import io.reactivex.Maybe;

@Dao
public interface WeatherForecastDatabaseDao {

    /**
     * Добавление нового прогноза погоды в базу данных
     * @param weatherForecastEntity
     * @return
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(WeatherForecastEntity weatherForecastEntity);

    /**
     * Обновление существующего прогноза погоды в базе данных
     * @param weatherForecastEntity
     * @return
     */
    @Update
    int update(WeatherForecastEntity weatherForecastEntity);

    /**
     * Удаление прогноза погоды из базы данных
     * @param weatherForecastEntity
     * @return
     */
    @Delete
    int delete(WeatherForecastEntity weatherForecastEntity);

    /**
     * Метод возвращает из базы данных прогноз погоды для
     * города, имеющего искомый cityIndex
     * @param desireCityIndex
     * @return
     */
    @Query("SELECT * FROM WeatherForecastEntity WHERE cityIndex = :desireCityIndex")
    Maybe<WeatherForecastEntity> getWeatherForecastByCityIndex(String desireCityIndex);

}
