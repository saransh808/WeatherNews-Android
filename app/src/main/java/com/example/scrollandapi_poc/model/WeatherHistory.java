package com.example.scrollandapi_poc.model;

import android.widget.ImageView;

public class WeatherHistory {

    String cityName;
    String cityTemperature;
    ImageView cityWeatherImg;

    public WeatherHistory(String cityName, String cityTemperature, ImageView cityWeatherImg) {
        this.cityName = cityName;
        this.cityTemperature = cityTemperature;
        this.cityWeatherImg = cityWeatherImg;
    }

    public WeatherHistory() {

    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCityTemperature() {
        return cityTemperature;
    }

    public void setCityTemperature(String cityTemperature) {
        this.cityTemperature = cityTemperature;
    }

    public ImageView getCityWeatherImg() {
        return cityWeatherImg;
    }

    public void setCityWeatherImg(ImageView cityWeatherImg) {
        this.cityWeatherImg = cityWeatherImg;
    }
}
