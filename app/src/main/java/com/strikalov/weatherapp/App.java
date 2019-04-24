package com.strikalov.weatherapp;

import android.app.Application;
import android.arch.persistence.room.Room;
import android.util.Log;

import com.strikalov.weatherapp.common.Constants;
import com.strikalov.weatherapp.dagger.AppComponent;
import com.strikalov.weatherapp.dagger.AppModule;
import com.strikalov.weatherapp.dagger.DaggerAppComponent;
import com.strikalov.weatherapp.model.databases.AppDatabase;

import java.io.IOException;
import java.io.InputStream;

public class App extends Application {

    private static final String FILE_NAME = "citybase.json";

    private static App instance;

    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        InputStream inputStream = null;

        try {
            inputStream = getAssets().open(FILE_NAME);
        } catch (IOException e) {
            Log.e(Constants.TAG_APP_ERROS, "App inputStreamError: ", e);
        }

        AppDatabase appDatabase = Room.databaseBuilder(this, AppDatabase.class, "appdatabase").build();

        appComponent = DaggerAppComponent.builder().appModule(new AppModule(inputStream, appDatabase)).build();

    }

    public static App getInstance(){
        return instance;
    }

    public AppComponent getAppComponent(){
        return appComponent;
    }

}
