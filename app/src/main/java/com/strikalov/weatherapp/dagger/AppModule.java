package com.strikalov.weatherapp.dagger;

import com.strikalov.weatherapp.model.databases.AppDatabase;
import com.strikalov.weatherapp.model.interactors.AssetsInputStreamInteractor;
import com.strikalov.weatherapp.model.interactors.AssetsInputStreamInteractorImpl;
import com.strikalov.weatherapp.model.interactors.CityDatabaseInteractor;
import com.strikalov.weatherapp.model.interactors.CityDatabaseInteractorImpl;
import com.strikalov.weatherapp.model.repositories.AssetsInputStreamRepository;
import com.strikalov.weatherapp.model.repositories.AssetsInputStreamRepositoryImpl;
import com.strikalov.weatherapp.model.repositories.CityDatabaseRepository;
import com.strikalov.weatherapp.model.repositories.CityDatabaseRepositoryImpl;

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
    public AssetsInputStreamRepository provideAssetsInputStreamRepository(InputStream assetsInputStream){
        return new AssetsInputStreamRepositoryImpl(assetsInputStream);
    }

    @Provides
    @Singleton
    public AssetsInputStreamInteractor provideAssetsInputStreamInteractor(AssetsInputStreamRepository assetsInputStreamRepository){
        return new AssetsInputStreamInteractorImpl(assetsInputStreamRepository);
    }

    @Provides
    @Singleton
    public CityDatabaseRepository provideCityDatabaseRepository(AppDatabase appDatabase){
        return new CityDatabaseRepositoryImpl(appDatabase);
    }

    @Provides
    @Singleton
    public CityDatabaseInteractor provideCityDatabaseInteractor(CityDatabaseRepository cityDatabaseRepository){
        return new CityDatabaseInteractorImpl(cityDatabaseRepository);
    }

}
