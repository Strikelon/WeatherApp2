package com.strikalov.weatherapp.dagger;

import com.strikalov.weatherapp.view.activities.CitySelectionActivity;
import com.strikalov.weatherapp.view.activities.MainActivity;
import com.strikalov.weatherapp.view.activities.SettingsActivity;
import com.strikalov.weatherapp.view.activities.SplashActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent {

    void injectSplashActivity(SplashActivity splashActivity);
    void injectSettingsActivity(SettingsActivity settingsActivity);
    void injectCitySelectionActivity(CitySelectionActivity citySelectionActivity);
    void injectMainActivity(MainActivity mainActivity);

}
