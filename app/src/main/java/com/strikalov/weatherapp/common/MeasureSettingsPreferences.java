package com.strikalov.weatherapp.common;

import android.content.Context;

public class MeasureSettingsPreferences {

    /**
     * Константа для доступа к файлу с preferences
     */
    private static final String APP_SETTINGS_PREFERENCES = "appSettingsPreferences";

    /**
     * Ключ для доступа к единицам измерения скорости ветра
     */
    private static final String KEY_WIND_MEASURE_SETTINGS = "windMeasureSettings";

    /**
     * Обозначает метры в секунду - единица измерения скорости ветра
     */
    public static final int METERS_PER_SECOND = 0;

    /**
     * Обозначает километры в час - единица измерения скорости ветра
     */
    public static final int KILOMETERS_PER_HOUR = 1;

    /**
     * Ключ для доступа к единицам измерения температуры
     */
    private static final String KEY_TEMPERATURE_MEASURE_SETTINGS = "temperatureMeasureSettings";

    /**
     * Обозначает градусы Цельсия - единица измерения температуры
     */
    public static final int DEGREES_CELSIUS = 0;

    /**
     * Обозначает градусы Фаренгейта - единица измерения температуры
     */
    public static final int DEGREES_FAHRENHEIT = 1;

    /**
     * Ключ для доступа к единицам измерения атмосферного давления
     */
    private static final String KEY_PRESSURE_MEASURE_SETTINGS = "pressureMeasureSettings";

    /**
     * Обозначает милиметр ртутного столба - единица измерения атмосферного давления
     */
    public static final int MILLIMETERS = 0;

    /**
     * Обозначает гекто паскали - единица измерения атмосферного давления
     */
    public static final int HPA = 1;

    /**
     * Получаем единицу измерения скорости ветра
     */
    public static int getWindMeasure(Context context){
        return context.getSharedPreferences(APP_SETTINGS_PREFERENCES, Context.MODE_PRIVATE)
                .getInt(KEY_WIND_MEASURE_SETTINGS, METERS_PER_SECOND);
    }

    /**
     * Устанавливаем единицу измерения скорости ветра
     */
    public static void setWindMeasure(Context context, int measure){
        context.getSharedPreferences(APP_SETTINGS_PREFERENCES, Context.MODE_PRIVATE)
                .edit()
                .putInt(KEY_WIND_MEASURE_SETTINGS, measure)
                .apply();
    }

    /**
     * Получаем единицу измерения температуры
     */
    public static int getTemperatureMeasure(Context context){
        return context.getSharedPreferences(APP_SETTINGS_PREFERENCES, Context.MODE_PRIVATE)
                .getInt(KEY_TEMPERATURE_MEASURE_SETTINGS, DEGREES_CELSIUS);
    }

    /**
     * Устанавливаем единицу измерения температуры
     */
    public static void setTemperatureMeasure(Context context, int measure){
        context.getSharedPreferences(APP_SETTINGS_PREFERENCES, Context.MODE_PRIVATE)
                .edit()
                .putInt(KEY_TEMPERATURE_MEASURE_SETTINGS, measure)
                .apply();
    }

    /**
     * Получаем единицу измерения атмосферного давления
     */
    public static int getPressureMeasure(Context context){
        return context.getSharedPreferences(APP_SETTINGS_PREFERENCES, Context.MODE_PRIVATE)
                .getInt(KEY_PRESSURE_MEASURE_SETTINGS, MILLIMETERS);
    }

    /**
     * Устанавливаем единицу измерения атмосферного давления
     */
    public static void setPressureMeasure(Context context, int measure){
        context.getSharedPreferences(APP_SETTINGS_PREFERENCES, Context.MODE_PRIVATE)
                .edit()
                .putInt(KEY_PRESSURE_MEASURE_SETTINGS, measure)
                .apply();
    }

}
