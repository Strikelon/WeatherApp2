package com.strikalov.weatherapp.model.network.apimodels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Вспомогательный класс, для преобразования полученного от сервера json объекта в прогноз погоды WeatherForecast
 */
public class WeatherForecastApi {

    @SerializedName("main")
    @Expose
    private Main main;

    public class Main {

        @SerializedName("temp")
        @Expose
        private double temp;

        @SerializedName("pressure")
        @Expose
        private double pressure;

        @SerializedName("humidity")
        @Expose
        private String humidity;

        public double getTemp() {
            return temp;
        }

        public double getPressure() {
            return pressure;
        }

        public String getHumidity() {
            return humidity;
        }
    }

    @SerializedName("wind")
    @Expose
    private Wind wind;

    public class Wind {

        @SerializedName("speed")
        @Expose
        private double speed;

        @SerializedName("deg")
        @Expose
        private double deg;

        public double getSpeed() {
            return speed;
        }

        public double getDeg() {
            return deg;
        }
    }

    @SerializedName("weather")
    @Expose
    private Weather[] weather;

    public class Weather {
        @SerializedName("icon")
        @Expose
        private String icon;

        public String getIcon() {
            return icon;
        }
    }

    @SerializedName("dt")
    @Expose
    private long dt;

    @SerializedName("sys")
    @Expose
    private Sys sys;

    public class Sys{

        @SerializedName("sunrise")
        @Expose
        private long sunrise;

        @SerializedName("sunset")
        @Expose
        private long sunset;

        public long getSunrise() {
            return sunrise;
        }

        public long getSunset() {
            return sunset;
        }
    }

    public double getTemp() {
        return main.getTemp();
    }

    public double getPressure() {
        return main.getPressure();
    }

    public String getHumidity() {
        return main.getHumidity();
    }

    public double getSpeed() {
        return wind.getSpeed();
    }

    public double getDeg() {
        return wind.getDeg();
    }

    public String getIcon() {
        return weather[0].getIcon();
    }

    public long getDt() {
        return dt;
    }

    public long getSunrise(){
        return sys.getSunrise();
    }

    public long getSunset(){
        return sys.getSunset();
    }


}
