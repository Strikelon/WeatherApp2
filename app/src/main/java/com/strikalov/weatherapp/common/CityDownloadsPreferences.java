package com.strikalov.weatherapp.common;

import android.content.Context;

public class CityDownloadsPreferences {

    /**
     * Константа для доступа к файлу с preferences
     */
    private static final String CITY_DOWNLOADS_PREFERENCES = "cityDownloadsPreferences";

    /**
     * Константа для доступа к boolean флагу, который дает понять, загружена база данных с названиями городов
     * или нет. Загрузка происходит один раз, при первом запуске приложения.
     */
    private static final String IS_CITY_DATABASE_DOWNLOADED = "iSCityDatabaseDownloaded";

    /**
     * Константа для доступа к индексу выбранного пользователем города
     */
    private static final String KEY_CITY_INDEX_PREFERENCE = "key_city_index";

    /**
     * Получаем boolean значение из файла preferences
     */
    public static boolean isCityDatabaseDownloaded(Context context){
        return context.getSharedPreferences(CITY_DOWNLOADS_PREFERENCES, Context.MODE_PRIVATE)
                .getBoolean(IS_CITY_DATABASE_DOWNLOADED, false);
    }

    /**
     * Устанавливаем boolean значение, для флага isCityDatabaseDownloaded
     */
    public static void setIsCityDatabaseDownloaded(Context context, boolean value){
        context.getSharedPreferences(CITY_DOWNLOADS_PREFERENCES, Context.MODE_PRIVATE)
                .edit()
                .putBoolean(IS_CITY_DATABASE_DOWNLOADED, value)
                .apply();
    }

    /**
     * Получаем индекс выбранного пользователем города, если он не записан, получаем null
     */
    public static String getCityIndex(Context context){
        return context.getSharedPreferences(CITY_DOWNLOADS_PREFERENCES, Context.MODE_PRIVATE)
                .getString(KEY_CITY_INDEX_PREFERENCE, null);
    }

    /**
     * Сохраняем в файл, индекс выбранного пользователем города
     */
    public static void setCityIndex(Context context, String value){
        context.getSharedPreferences(CITY_DOWNLOADS_PREFERENCES, Context.MODE_PRIVATE)
                .edit()
                .putString(KEY_CITY_INDEX_PREFERENCE, value)
                .apply();
    }

}
