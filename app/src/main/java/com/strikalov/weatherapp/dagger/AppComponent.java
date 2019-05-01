package com.strikalov.weatherapp.dagger;

import com.strikalov.weatherapp.view.SettingsActivity;
import com.strikalov.weatherapp.view.SplashActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent {

    void injectSplashActivity(SplashActivity splashActivity);
    void injectSettingsActivity(SettingsActivity settingsActivity);

}
