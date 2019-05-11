package com.strikalov.weatherapp.view;

import com.arellomobile.mvp.MvpView;
import com.strikalov.weatherapp.model.entities.City;
import com.strikalov.weatherapp.model.entities.WeatherForecast;

import java.util.List;

public interface MainView extends MvpView {

    /**
     * Метод передает в адаптер cityNavDrawerRecyclerViewAdapter новый список
     * избранных городов и обновляет адаптер
     * @param favoritesCityList
     */
    void updateNavigationRecyclerView(List<City> favoritesCityList);

    /**
     * В данном методе из preference файла получаем cityIndex
     */
    void getCityIndex();

    /**
     * В данном методе из preference файла получаем cityName
     */
    void getCityName();

    /**
     * В этом методе в preference файл сохранятся новые значения cityIndex
     * и cityName
     * @param cityIndex
     * @param cityName
     */
    void setCityDownloadsPreferences(String cityIndex, String cityName);

    /**
     * Этот метод устанавливает для toolbar в качестве заголовка
     * название города cityName
     * @param cityName
     */
    void setToolbarTitle(String cityName);

    /**
     * В данном методе из preference файла получаем текущие настроки
     * для единицы измерения скорости ветра
     */
    void getWindMeasure();

    /**
     * В данном методе из preference файла получаем текущие настроки
     * для единице измерения температуры
     */
    void getTemperatureMeasure();

    /**
     * В данном методе из preference файла получаем текущие настройки
     * для единицы измерения атмосферного давления
     */
    void getPressureMeasure();

    /**
     * В этом методе мы запрашиваем из интента boolean значение,
     * которое говорит нам, получали ли мы до старта MainActivity
     * доступ к сети и сохраняли ли в базу данных актуальный прогноз погоды
     */
    void getFromIntentWasOnline();

    /**
     * Метод показывает snackbar, с сообщением, что невозможно показать прогноз
     * погоды из-за отсутствия соединения с интернетом, просим проверить соединение
     * и обновить страницу
     */
    void showSnackBarNoDataToDisplay();

    /**
     * Метод показывает snackbar с сообщением о том, что настройки успешно были сохранены
     */
    void showSnackBarSettingsSavedSuccessfully();

    /**
     * Метод показывает progressbar
     */
    void swipeRefreshShow();

    /**
     * Метод убирает progressbar
     */
    void swipeRefreshHide();

    /**
     * Метод показывает прогноз погоды пользователю с учетом настроек единиц измерения
     * @param weatherForecast
     * @param windMeasure
     * @param temperatureMeasure
     * @param pressureMeasure
     */
    void showWeatherForecast(WeatherForecast weatherForecast, int windMeasure, int temperatureMeasure, int pressureMeasure);

    /**
     * Метод очишает все поля, где показывается прогноз погоды
     */
    void resetWeatherForecastData();

    /**
     * Метод проверяет, есть ли связь с интернетом
     */
    void isOnline();

    /**
     * Метод срабатывает, когда пользователь хочет отправить сообщение об ошибке, и предоставляет
     * пользователю на выбор почтовые приложения. После выбора пользователем приложения,
     * в графе кому, уже будет вставлен контактный email, команды разработки
     */
    void causeMailApp();

    /**
     * Метод стартует SettingsActivity и позволяет получить результат из этой активти
     * после завершения ее работы
     * @param requestCode
     */
    void startSettingsActivityForResult(int requestCode);

    /**
     * Метод стартует CitySelectionActivity и позволяет получить результат из этой активти
     * после завершения ее работы
     * @param requestCode
     */
    void startCitySelectionActivityForResult(int requestCode);

    /**
     * Метод завершает текущую активити
     */
    void finishActivity();

}
