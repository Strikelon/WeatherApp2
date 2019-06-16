package com.strikalov.weatherapp.view;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.strikalov.weatherapp.model.entities.City;
import com.strikalov.weatherapp.model.entities.WeatherForecast;

import java.util.List;

public interface MainView extends MvpView {

    /**
     * Метод передает в адаптер cityNavDrawerRecyclerViewAdapter новый список
     * избранных городов и обновляет адаптер
     * @param favoritesCityList
     */
    @StateStrategyType(SkipStrategy.class)
    void updateNavigationRecyclerView(List<City> favoritesCityList);

    /**
     * В данном методе из preference файла получаем cityIndex
     */
    @StateStrategyType(SkipStrategy.class)
    void getCityIndex();

    /**
     * В данном методе из preference файла получаем cityName
     */
    @StateStrategyType(SkipStrategy.class)
    void getCityName();

    /**
     * В этом методе в preference файл сохранятся новые значения cityIndex
     * и cityName
     * @param cityIndex
     * @param cityName
     */
    @StateStrategyType(SkipStrategy.class)
    void setCityDownloadsPreferences(String cityIndex, String cityName);

    /**
     * Этот метод устанавливает для toolbar в качестве заголовка
     * название города cityName
     * @param cityName
     */
    @StateStrategyType(SkipStrategy.class)
    void setToolbarTitle(String cityName);

    /**
     * В данном методе из preference файла получаем текущие настроки
     * для единицы измерения скорости ветра
     */
    @StateStrategyType(SkipStrategy.class)
    void getWindMeasure();

    /**
     * В данном методе из preference файла получаем текущие настроки
     * для единице измерения температуры
     */
    @StateStrategyType(SkipStrategy.class)
    void getTemperatureMeasure();

    /**
     * В данном методе из preference файла получаем текущие настройки
     * для единицы измерения атмосферного давления
     */
    @StateStrategyType(SkipStrategy.class)
    void getPressureMeasure();

    /**
     * В этом методе мы запрашиваем из интента boolean значение,
     * которое говорит нам, получали ли мы до старта MainActivity
     * доступ к сети и сохраняли ли в базу данных актуальный прогноз погоды
     */
    @StateStrategyType(SkipStrategy.class)
    void getFromIntentWasOnline();

    /**
     * Метод показывает snackbar, с сообщением, что невозможно показать прогноз
     * погоды из-за отсутствия соединения с интернетом, просим проверить соединение
     * и обновить страницу
     */
    @StateStrategyType(SkipStrategy.class)
    void showSnackBarNoDataToDisplay();

    /**
     * Метод показывает snackbar с сообщением о том, что настройки успешно были сохранены
     */
    @StateStrategyType(SkipStrategy.class)
    void showSnackBarSettingsSavedSuccessfully();

    /**
     * Метод показывает progressbar
     */
    @StateStrategyType(SkipStrategy.class)
    void swipeRefreshShow();

    /**
     * Метод убирает progressbar
     */
    @StateStrategyType(SkipStrategy.class)
    void swipeRefreshHide();

    /**
     * Метод показывает прогноз погоды пользователю с учетом настроек единиц измерения
     * @param weatherForecast
     * @param windMeasure
     * @param temperatureMeasure
     * @param pressureMeasure
     */
    @StateStrategyType(SkipStrategy.class)
    void showWeatherForecast(WeatherForecast weatherForecast, int windMeasure, int temperatureMeasure, int pressureMeasure);

    /**
     * Метод очишает все поля, где показывается прогноз погоды
     */
    @StateStrategyType(SkipStrategy.class)
    void resetWeatherForecastData();

    /**
     * Метод проверяет, есть ли связь с интернетом
     */
    @StateStrategyType(SkipStrategy.class)
    void isOnline();

    /**
     * Метод срабатывает, когда пользователь хочет отправить сообщение об ошибке, и предоставляет
     * пользователю на выбор почтовые приложения. После выбора пользователем приложения,
     * в графе кому, уже будет вставлен контактный email, команды разработки
     */
    @StateStrategyType(SkipStrategy.class)
    void causeMailApp();

    /**
     * Метод стартует SettingsActivity и позволяет получить результат из этой активти
     * после завершения ее работы
     * @param requestCode
     */
    @StateStrategyType(SkipStrategy.class)
    void startSettingsActivityForResult(int requestCode);

    /**
     * Метод стартует CitySelectionActivity и позволяет получить результат из этой активти
     * после завершения ее работы
     * @param requestCode
     */
    @StateStrategyType(SkipStrategy.class)
    void startCitySelectionActivityForResult(int requestCode);

    /**
     * Метод завершает текущую активити
     */
    @StateStrategyType(SkipStrategy.class)
    void finishActivity();

}
