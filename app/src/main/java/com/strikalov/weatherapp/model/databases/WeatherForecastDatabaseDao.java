package com.strikalov.weatherapp.model.databases;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Update;

@Dao
public interface WeatherForecastDatabaseDao {

    /**
     * Добавление нового прогноза погоды в базу данных
     * @param weatherForecastEntity
     * @return
     */
    @Insert
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

}
