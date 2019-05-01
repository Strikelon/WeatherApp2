package com.strikalov.weatherapp.model.repositories;

import com.strikalov.weatherapp.model.entities.WeatherForecast;
import com.strikalov.weatherapp.model.entities.WeatherPicture;
import com.strikalov.weatherapp.model.entities.WindDirection;
import com.strikalov.weatherapp.model.network.WebApi;
import com.strikalov.weatherapp.model.network.apimodels.WeatherForecastApi;

import java.text.SimpleDateFormat;
import java.util.Date;

import io.reactivex.Observable;
import io.reactivex.functions.Function;

/**
 * Репозиторий, для получения прогноза погоды у сервера-погоды
 */
public class NetworkRepositoryImpl implements NetworkRepository{

    /**
     * Ключ зарегистрированного пользователя, для доступа к работе с сервером-погоды
     */
    private final String KEY_API = "e397147f38c213ae53717fb01f417e20";

    private WebApi webApi;

    public NetworkRepositoryImpl(WebApi webApi){

        this.webApi = webApi;

    }

    /**
     * Метод возвращает прогноз погоды по id города.
     * Прогноз получаем в ввиде объекта класса WeatherForecastApi, который получается из json ответа от сервера,
     * затем мы преобразовываем объект класса WeatherForecastApi в объект класса WeatherForecast
     * @param cityId
     * @return
     */
    @Override
    public Observable<WeatherForecast> getWeatherForecast(String cityId) {
        return webApi.getWebApiService().loadWeather(cityId, KEY_API)
                .map(new Function<WeatherForecastApi, WeatherForecast>() {
                    @Override
                    public WeatherForecast apply(WeatherForecastApi weatherForecastApi) throws Exception {

                        String date = renderDateTime(weatherForecastApi.getDt(),
                                new SimpleDateFormat("dd/MM"));

                        String sunrise = renderDateTime(weatherForecastApi.getSunrise(),
                                new SimpleDateFormat("HH:mm"));

                        String sunset = renderDateTime(weatherForecastApi.getSunset(),
                                new SimpleDateFormat("HH:mm"));

                        String temperatureDegreesCelsius =
                                renderTemperatureDegreesCelsius(weatherForecastApi.getTemp());

                        String temperatureDegreesFahrenheit =
                                renderTemperatureDegreesFahrenheit(weatherForecastApi.getTemp());

                        String windMetersPerSecond =
                                renderWindMetersPerSecond(weatherForecastApi.getSpeed());

                        String windKilometersPerHour =
                                renderWindKilometersPerHour(weatherForecastApi.getSpeed());

                        String pressureMillimeters =
                                renderPressureMillimeters(weatherForecastApi.getPressure());

                        String pressureHpa =
                                renderPressureHpa(weatherForecastApi.getPressure());

                        String humidity = weatherForecastApi.getHumidity();

                        WindDirection windDirection =
                                renderWindDirection((int) weatherForecastApi.getDeg());

                        WeatherPicture weatherPicture =
                                renderIcon(weatherForecastApi.getIcon());

                        return new WeatherForecast(date,
                                temperatureDegreesCelsius,
                                temperatureDegreesFahrenheit,
                                windMetersPerSecond,
                                windKilometersPerHour,
                                windDirection,
                                pressureMillimeters,
                                pressureHpa,
                                humidity,
                                weatherPicture,
                                sunrise,
                                sunset);
                    }
                });
    }

    /**
     * Преобразование числи типа long в даты формата "24/05" или например
     * время в формате "06:14" в Strng в зависимости от
     * настроек параметра SimpleDateFormat dateFormat
     * @param dt
     * @param dateFormat
     * @return
     */
    private String renderDateTime(long dt, SimpleDateFormat dateFormat){

        Date date = new Date(dt*1000);

        return dateFormat.format(date);

    }

    /**
     * Преобразование значения температуры из кельвинов в градуса цельсия
     * из double в string
     * @param temp
     * @return
     */
    private String renderTemperatureDegreesCelsius(double temp){

        temp = temp - 273.15;

        long tempLong = Math.round(temp);

        return Long.toString(tempLong);

    }

    /**
     * Преобразование температуры из кельвинов в градусы Фаренгейта
     * из double в string
     * @param temp
     * @return
     */
    private String renderTemperatureDegreesFahrenheit(double temp){

        temp = 9 * (temp - 273.15) / 5 + 32;

        long tempLong = Math.round(temp);

        return Long.toString(tempLong);
    }

    /**
     * Преобразование скорости ветра в метрах в секунду из double в string
     * @param windSpeed
     * @return
     */
    private String renderWindMetersPerSecond(double windSpeed){

        long windSpeedLong = Math.round(windSpeed);

        return Long.toString(windSpeedLong);
    }

    /**
     * Преобразование скорости ветра в метрах в секунду в килоетры в час
     * из double в string
     * @param windSpeed
     * @return
     */
    private String renderWindKilometersPerHour(double windSpeed){

        windSpeed = windSpeed * 3.6;

        long windSpeedLong = Math.round(windSpeed);

        return Long.toString(windSpeedLong);
    }

    /**
     * Преобразование давления из гекто паскалей в мм ртутного столбца
     * из double в string
     * @param pressure
     * @return
     */
    private String renderPressureMillimeters(double pressure){

        pressure = pressure * 0.75006;

        long pressureLong = Math.round(pressure);

        return Long.toString(pressureLong);

    }

    /**
     * Преобразование давления в гекто паскалях из double в string
     * @param pressure
     * @return
     */
    private String renderPressureHpa(double pressure){

        long pressureLong = Math.round(pressure);

        return Long.toString(pressureLong);

    }

    /**
     * Возвращает true если число deg находится в интервале
     * между start и end включительно
     * @param deg
     * @param start
     * @param end
     * @return
     */
    private boolean isInRange(int deg, int start, int end) {
        return start <= deg && deg <= end;
    }

    /**
     * Метод принимает на вход градусы в формате int и в зависимости
     * от значения deg возвращает направление ветра в ввиде объекта WindDirection
     * @param deg
     * @return
     */
    private WindDirection renderWindDirection(int deg){
        WindDirection windDirection;

        if (isInRange(deg, 337, 360)) {
            windDirection = WindDirection.NORTH;
        } else if (isInRange(deg, 292, 336)) {
            windDirection = WindDirection.NORTH_WEST;
        } else if (isInRange(deg, 247, 291)) {
            windDirection = WindDirection.WEST;
        } else if (isInRange(deg, 202, 246)) {
            windDirection = WindDirection.SOUTH_WEST;
        } else if (isInRange(deg, 157, 201)) {
            windDirection = WindDirection.SOUTH;
        } else if (isInRange(deg, 112, 156)) {
            windDirection = WindDirection.SOUTH_EAST;
        } else if (isInRange(deg, 67, 111)) {
            windDirection = WindDirection.EAST;
        } else if (isInRange(deg, 22, 66)) {
            windDirection = WindDirection.NORTH_EAST;
        } else if (isInRange(deg, 0, 21)) {
            windDirection = WindDirection.NORTH;
        } else {
            windDirection = WindDirection.NO_DIRECTION;
        }

        return windDirection;
    }

    /**
     * Метод принимает на вход String iconName значение, которое возвращается от сервера и
     * возвращает картинку в ввиде объекта WeatherPicture, которая ссответствует
     * значению String iconName
     * @param iconName
     * @return
     */
    private WeatherPicture renderIcon(String iconName){

        WeatherPicture weatherPicture;

        switch (iconName) {
            case "01d":
                weatherPicture = WeatherPicture.P01;
                break;
            case "01n":
                weatherPicture = WeatherPicture.P01;
                break;
            case "02d":
                weatherPicture = WeatherPicture.P02;
                break;
            case "02n":
                weatherPicture = WeatherPicture.P02;
                break;
            case "03d":
                weatherPicture = WeatherPicture.P03;
                break;
            case "03n":
                weatherPicture = WeatherPicture.P03;
                break;
            case "04d":
                weatherPicture = WeatherPicture.P04;
                break;
            case "04n":
                weatherPicture = WeatherPicture.P04;
                break;
            case "09d":
                weatherPicture = WeatherPicture.P09;
                break;
            case "09n":
                weatherPicture = WeatherPicture.P09;
                break;
            case "10d":
                weatherPicture = WeatherPicture.P10;
                break;
            case "10n":
                weatherPicture = WeatherPicture.P10;
                break;
            case "11d":
                weatherPicture = WeatherPicture.P11;
                break;
            case "11n":
                weatherPicture = WeatherPicture.P11;
                break;
            case "13d":
                weatherPicture = WeatherPicture.P13;
                break;
            case "13n":
                weatherPicture = WeatherPicture.P13;
                break;
            case "50d":
                weatherPicture = WeatherPicture.P50;
                break;
            case "50n":
                weatherPicture = WeatherPicture.P50;
                break;
            default:
                weatherPicture = WeatherPicture.NO_ICON;
                break;
        }
        return weatherPicture;
    }

}
