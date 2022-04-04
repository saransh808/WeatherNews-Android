package com.example.scrollandapi_poc.model;

import java.util.ArrayList;

public class WeatherHistoryList {

    private static ArrayList<WeatherHistory> weatherHistoryList = null;

    public static ArrayList<WeatherHistory> getWeatherList(){
        if(weatherHistoryList == null) weatherHistoryList = new ArrayList<WeatherHistory>();
        return weatherHistoryList;
    }

}
