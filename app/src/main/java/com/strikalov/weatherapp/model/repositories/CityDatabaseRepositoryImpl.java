package com.strikalov.weatherapp.model.repositories;

import com.strikalov.weatherapp.model.databases.AppDatabase;
import com.strikalov.weatherapp.model.databases.CityEntity;
import com.strikalov.weatherapp.model.entities.City;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.MaybeSource;
import io.reactivex.functions.Function;

public class CityDatabaseRepositoryImpl implements CityDatabaseRepository {

    /**
     * Константа обозначает, что город не добавлен в избранное
     */
    private static final int NOT_FAVORITE = 0;
    /**
     * Константа обозначает, что город добавлен в избранное
     */
    private static final int FAVORITE = 1;

    /**
     * Ссылка на базу данных
     */
    private AppDatabase appDatabase;

    public CityDatabaseRepositoryImpl(AppDatabase appDatabase){
        this.appDatabase = appDatabase;
    }


    /**
     * Метод преобразует список объектов класса CityEntity
     * в список объектов класса City
     * @param cityEntities
     * @return
     */
    private List<City> createCityList(List<CityEntity> cityEntities){

        List<City> cityList = new ArrayList<>();
        for(CityEntity cityEntity : cityEntities){
            cityList.add(new City(cityEntity.getCityIndex(), cityEntity.getCityName()));
        }
        return cityList;

    }

    /**
     * Метод преобразует список объектов класса City
     * в список объектов класса CityEntity
     * @param cityList
     * @return
     */
    private List<CityEntity> createCityEntityList(List<City> cityList){

        List<CityEntity> cityEntityList = new ArrayList<>();
        for(City city: cityList){
            CityEntity cityEntity = new CityEntity(city.getCityIndex(), city.getCityName(), NOT_FAVORITE);
            cityEntityList.add(cityEntity);
        }
        return cityEntityList;
    }

    /**
     * Метод сохраняет список объектов класса City в базе данных
     * @param cities
     * @return
     */
    @Override
    public Completable insertCityList(List<City> cities) {

        final List<CityEntity> cityEntityList = createCityEntityList(cities);

        return Completable.fromCallable(new Callable<Long[]>() {
            @Override
            public Long[] call() throws Exception {
                return appDatabase.cityDatabaseDao().insertList(cityEntityList);
            }
        });

    }

    /**
     * Метод запрашивает из базы данных все города
     * @return
     */
    @Override
    public Maybe<List<City>> getAllCities() {
        return appDatabase.cityDatabaseDao().getAllCities()
                .map(new Function<List<CityEntity>, List<City>>() {
                    @Override
                    public List<City> apply(List<CityEntity> cityEntities) throws Exception {

                        return createCityList(cityEntities);
                    }
                });
    }

    /**
     * Метод запрашивает из базы данных все избранные города
     * @return
     */
    @Override
    public Maybe<List<City>> getFavoritesCities() {
        return appDatabase.cityDatabaseDao().getCitiesByFavoriteValue(FAVORITE)
                .map(new Function<List<CityEntity>, List<City>>() {
                    @Override
                    public List<City> apply(List<CityEntity> cityEntities) throws Exception {

                        return createCityList(cityEntities);
                    }
                });
    }

    /**
     * Метод меняет в базе данных у города с заданным индексом
     * значение поля избранное/не избранное
     * @param desiredCityIndex
     * @param favoriteValue
     * @return
     */
    @Override
    public Completable updateIsFavoritesByCityIndex(final String desiredCityIndex, final int favoriteValue) {
        return Completable.fromCallable(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                return appDatabase.cityDatabaseDao().updateIsFavoritesByCityIndex(desiredCityIndex, favoriteValue);
            }
        });
    }
}
