package com.strikalov.weatherapp.view;

import com.arellomobile.mvp.MvpView;

public interface SplashView extends MvpView {

    void getIsCityDatabaseDownloaded();

    void setIsCityDatabaseDownloaded(boolean value);

    void getCityIndex();

    void isOnline();

    /**
     * Эти методы нужно удалить, именно для этой активити они нужны, использовал для тестов
     */

    void setCityIndex(String cityIndex);

}
