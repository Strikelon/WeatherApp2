package com.strikalov.weatherapp.dagger;

import com.strikalov.weatherapp.model.databases.AppDatabase;
import com.strikalov.weatherapp.model.interactors.CityInteractor;
import com.strikalov.weatherapp.model.interactors.CityInteractorImpl;
import com.strikalov.weatherapp.model.interactors.WeatherForecastInteractor;
import com.strikalov.weatherapp.model.interactors.WeatherForecastInteractorImpl;
import com.strikalov.weatherapp.model.network.WebApi;
import com.strikalov.weatherapp.model.repositories.AssetsInputStreamRepository;
import com.strikalov.weatherapp.model.repositories.AssetsInputStreamRepositoryImpl;
import com.strikalov.weatherapp.model.repositories.CityDatabaseRepository;
import com.strikalov.weatherapp.model.repositories.CityDatabaseRepositoryImpl;
import com.strikalov.weatherapp.model.repositories.NetworkRepository;
import com.strikalov.weatherapp.model.repositories.NetworkRepositoryImpl;
import com.strikalov.weatherapp.model.repositories.WeatherForecastDatabaseRepository;
import com.strikalov.weatherapp.model.repositories.WeatherForecastDatabaseRepositoryImpl;

import java.io.InputStream;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    private InputStream assetsInputStream;
    private AppDatabase appDatabase;

    public AppModule(InputStream assetsInputStream, AppDatabase appDatabase){
        this.assetsInputStream = assetsInputStream;
        this.appDatabase = appDatabase;
    }

    @Provides
    @Singleton
    public AppDatabase provideAppDatabase(){
        return appDatabase;
    }

    @Provides
    @Singleton
    public InputStream provideAssetsInputStream(){
        return assetsInputStream;
    }

    @Provides
    @Singleton
    public WebApi provideWebApi(){
        return new WebApi();
    }

    @Provides
    @Singleton
    public AssetsInputStreamRepository provideAssetsInputStreamRepository(InputStream assetsInputStream){
        return new AssetsInputStreamRepositoryImpl(assetsInputStream);
    }

    @Provides
    @Singleton
    public CityDatabaseRepository provideCityDatabaseRepository(AppDatabase appDatabase){
        return new CityDatabaseRepositoryImpl(appDatabase);
    }

    @Provides
    @Singleton
    public CityInteractor provideCityInteractor(AssetsInputStreamRepository assetsInputStreamRepository, CityDatabaseRepository cityDatabaseRepository){
        return new CityInteractorImpl(assetsInputStreamRepository, cityDatabaseRepository);
    }

    @Provides
    @Singleton
    public NetworkRepository provideNetworkRepository(WebApi webApi){
        return new NetworkRepositoryImpl(webApi);
    }

    @Provides
    @Singleton
    public WeatherForecastDatabaseRepository provideWeatherForecastDatabaseRepository(AppDatabase appDatabase){
        return new WeatherForecastDatabaseRepositoryImpl(appDatabase);
    }

    @Provides
    @Singleton
    public WeatherForecastInteractor provideWeatherForecastInteractor(NetworkRepository networkRepository,
                                                                      WeatherForecastDatabaseRepository weatherForecastDatabaseRepository){

        return new WeatherForecastInteractorImpl(networkRepository, weatherForecastDatabaseRepository);
    }

}
