package com.strikalov.weatherapp.model.repositories;

import com.strikalov.weatherapp.model.databases.AppDatabase;
import com.strikalov.weatherapp.model.databases.CityEntity;
import com.strikalov.weatherapp.model.entities.City;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.Completable;

public class CityDatabaseRepositoryImpl implements CityDatabaseRepository {

    /**
     * Константа обозначает, что город не добавлен в избранное
     */
    private static final int NOT_SELECTED = 0;
    /**
     * Константа обозначает, что город добавлен в избранное
     */
    private static final int SELECTED = 1;

    /**
     * Ссылка на базу данных
     */
    private AppDatabase appDatabase;

    public CityDatabaseRepositoryImpl(AppDatabase appDatabase){
        this.appDatabase = appDatabase;
    }

    /**
     * Метод сохраняет список объектов класса City в базе данных
     * @param cities
     * @return
     */
    @Override
    public Completable insertCityList(List<City> cities) {

        /**
         * Список объектов cityEntityList, в таком виде они хранятся в базе данных
         */
        final List<CityEntity> cityEntityList = new ArrayList<>();

        /**
         * Проходимся по списку cities и наполняем список cityEntityList
         */
        for(City city: cities){

            CityEntity cityEntity = new CityEntity(city.getCityIndex(), city.getCityName(), NOT_SELECTED);

            cityEntityList.add(cityEntity);
        }

        /**
         * Сохранения списка в базе данных и возврат объкта типа Completable, чтобы
         * можно было отследить, что сохранения прошло успешно
         */
        return Completable.fromCallable(new Callable<Long[]>() {
            @Override
            public Long[] call() throws Exception {
                return appDatabase.cityDatabaseDao().insertList(cityEntityList);
            }
        });

    }
}
