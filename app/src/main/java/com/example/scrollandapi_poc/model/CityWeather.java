package com.example.scrollandapi_poc.model;

public class CityWeather {
    private String cityName;
    private String cityTemp;
    private String cityImg;

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCityTemp() {
        return cityTemp;
    }

    public void setCityTemp(String cityTemp) {
        this.cityTemp = cityTemp;
    }

    public String getCityImg() {
        return cityImg;
    }

    public void setCityImg(String cityImg) {
        this.cityImg = cityImg;
    }

    public CityWeather(String cityName, String cityTemp, String cityImg) {
        this.cityName = cityName;
        this.cityTemp = cityTemp;
        this.cityImg = cityImg;
    }

    public CityWeather() {
    }

    @Override
    public String toString() {
        return "CityWeather{" +
                "cityName='" + cityName + '\'' +
                ", cityTemp='" + cityTemp + '\'' +
                ", cityImg='" + cityImg + '\'' +
                '}';
    }
}
