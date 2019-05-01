package com.strikalov.weatherapp.model.databases;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;


/**
 * База данных с двумя сущностями CityEntity и WeatherForecastEntity
 */
@Database(entities = {CityEntity.class, WeatherForecastEntity.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public abstract CityDatabaseDao cityDatabaseDao();

    public abstract WeatherForecastDatabaseDao weatherForecastDatabaseDao();

}
