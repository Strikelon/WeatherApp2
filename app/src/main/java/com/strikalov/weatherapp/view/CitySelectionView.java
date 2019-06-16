package com.strikalov.weatherapp.view;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.strikalov.weatherapp.model.entities.City;

import java.util.List;

public interface CitySelectionView extends MvpView {

    /**
     * Инициализация виджета AutoCompleteTextView
     * @param cityList
     */
    @StateStrategyType(SkipStrategy.class)
    void initAutoCompleteTextView(List<City> cityList);

    /**
     * Метод делает ProgressBar видимым
     */
    @StateStrategyType(SkipStrategy.class)
    void setProgressBarVisible();

    /**
     * Метод делает ProgressBar невидимым
     */
    @StateStrategyType(SkipStrategy.class)
    void setProgressBarGone();

    /**
     * Метод делает видимым FrameLayout с тектом, что список избранных городов пуст
     */
    @StateStrategyType(SkipStrategy.class)
    void setFrameLayoutEmptyListVisible();
    /**
     * Метод делает невидимым FrameLayout с тектом, что список избранных городов пуст
     */
    @StateStrategyType(SkipStrategy.class)
    void setFrameLayoutEmptyListGone();

    /**
     * Метод делает RecyclerView с видимыми городами видимым
     */
    @StateStrategyType(SkipStrategy.class)
    void setRecyclerViewVisible();
    /**
     * Метод делает RecyclerView с видимыми городами невидимым
     */
    @StateStrategyType(SkipStrategy.class)
    void setRecyclerViewGone();

    /**
     * Метод передает в адаптер RecyclerView новый список избранных городов
     * и обновляет его
     * @param favoritesCityList
     */
    @StateStrategyType(SkipStrategy.class)
    void updateRecyclerView(List<City> favoritesCityList);

    /**
     * Метод сохраняет в Preferences файл индекс и имя выбранного
     * пользователем города для прогноза погоды
     * @param cityIndex
     * @param cityName
     */
    @StateStrategyType(SkipStrategy.class)
    void setCityDownloadsPreferences(String cityIndex, String cityName);

    /**
     * Метод возвращает из Preferences файла индекс выбранного
     * пользователем города для прогноза погоды
     */
    @StateStrategyType(SkipStrategy.class)
    void getCityDownloadsPreferences();

    /**
     * Метод завершает выбор города
     */
    @StateStrategyType(SkipStrategy.class)
    void citySelectionComplete(boolean isParentWaitingResult);

    /**
     * Метод выдает сообщение через snackbar, о том,
     * что город не выбран
     */
    @StateStrategyType(SkipStrategy.class)
    void cityNotSelectedShow();

    /**
     * Метод получает из интента boolean значение. По этому значению мы понимаени
     * запущено ли CitySelectionActivity из MainActivity, и MainActivity ожидает, результат, либо
     * CitySelectionActivity запущено из SplashActivity, тогда результат не ожидается
     */
    @StateStrategyType(SkipStrategy.class)
    void getFromIntentIsParentWaitingResult();
}
