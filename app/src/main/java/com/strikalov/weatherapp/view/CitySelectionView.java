package com.strikalov.weatherapp.view;

import com.arellomobile.mvp.MvpView;
import com.strikalov.weatherapp.model.entities.City;

import java.util.List;

public interface CitySelectionView extends MvpView {

    /**
     * Инициализация виджета AutoCompleteTextView
     * @param cityList
     */
    void initAutoCompleteTextView(List<City> cityList);

    /**
     * Метод делает ProgressBar видимым
     */
    void setProgressBarVisible();

    /**
     * Метод делает ProgressBar невидимым
     */
    void setProgressBarGone();

    /**
     * Метод делает видимым FrameLayout с тектом, что список избранных городов пуст
     */
    void setFrameLayoutEmptyListVisible();
    /**
     * Метод делает невидимым FrameLayout с тектом, что список избранных городов пуст
     */
    void setFrameLayoutEmptyListGone();

    /**
     * Метод делает RecyclerView с видимыми городами видимым
     */
    void setRecyclerViewVisible();
    /**
     * Метод делает RecyclerView с видимыми городами невидимым
     */
    void setRecyclerViewGone();

    /**
     * Метод передает в адаптер RecyclerView новый список избранных городов
     * и обновляет его
     * @param favoritesCityList
     */
    void updateRecyclerView(List<City> favoritesCityList);

    /**
     * Метод сохраняет в Preferences файл индекс и имя выбранного
     * пользователем города для прогноза погоды
     * @param cityIndex
     * @param cityName
     */
    void setCityDownloadsPreferences(String cityIndex, String cityName);

    /**
     * Метод возвращает из Preferences файла индекс выбранного
     * пользователем города для прогноза погоды
     */
    void getCityDownloadsPreferences();

    /**
     * Метод завершает выбор города
     */
    void citySelectionComplete(boolean isParentWaitingResult);

    /**
     * Метод выдает сообщение через snackbar, о том,
     * что город не выбран
     */
    void cityNotSelectedShow();

    /**
     * Метод получает из интента boolean значение. По этому значению мы понимаени
     * запущено ли CitySelectionActivity из MainActivity, и MainActivity ожидает, результат, либо
     * CitySelectionActivity запущено из SplashActivity, тогда результат не ожидается
     */
    void getFromIntentIsParentWaitingResult();
}
