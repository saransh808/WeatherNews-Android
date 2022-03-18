package com.example.scrollandapi_poc;

import org.json.JSONException;
import org.json.JSONObject;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.Map;

public class CustomWeatherWidget {

    private static CustomWeatherWidget customWeatherWidget = null;

    private String cityName = "Toronto";

    /*
     * Singleton pattern
     */
    public static CustomWeatherWidget getInstance(){
        if(customWeatherWidget == null){
            customWeatherWidget = new CustomWeatherWidget();
        }
        return customWeatherWidget;
    }

    public Map<String, String> getWeatherWidgetElements(JSONObject reader) throws JSONException {
        Map<String, String> result = new HashMap<String, String>();

        /*
         * City Name and weather condition
         */
        result.put("title",reader.getString("name"));
        result.put("weather",reader.getJSONArray("weather").getJSONObject(0).getString("main"));
        result.put("icon",reader.getJSONArray("weather").getJSONObject(0).getString("icon"));

        /*
         * Sunset and Sunrise
         */
        String sunsetTime = getDateWithZoneFix(reader.getJSONObject("sys").getLong("sunset"), reader.getLong("timezone"));
        result.put("sunset", sunsetTime);

        String sunriseTime = getDateWithZoneFix(reader.getJSONObject("sys").getLong("sunrise"), reader.getLong("timezone"));
        result.put("sunrise", sunriseTime);

        /*
         * Wind speed and humidity
         */
        result.put("wind", Integer.toString((int)(reader.getJSONObject("wind").getDouble("speed")*1.609F))+"kmph");
        result.put("humidity", reader.getJSONObject("main").getString("humidity")+"%");

        /*
         * Temperature and Feels Like
         */
        String temp = reader.getJSONObject("main").getString("temp");
        temp = temp.substring(0, temp.indexOf("."));
        result.put("temp", temp+(char)0x00B0+"");

        String feelsLike = reader.getJSONObject("main").getString("feels_like");
        feelsLike = feelsLike.substring(0, feelsLike.indexOf("."));
        result.put("feels_like", "Feels Like "+feelsLike+(char)0x00B0+"C");

        return result;
    }

    public String getDateWithZoneFix(Long epochSeconds, Long timeZone){
        OffsetDateTime odt = OffsetDateTime.now (ZoneId.systemDefault());
        ZoneOffset zoneOffset = odt.getOffset ();
        int offsetSeconds = zoneOffset.getTotalSeconds();
        long totalEpoch = epochSeconds + timeZone - offsetSeconds;
        LocalDateTime time = Instant.ofEpochSecond(totalEpoch).atZone(ZoneId.systemDefault()).toLocalDateTime();
        return time.toString().substring(11, 16);
    }



    /*
    Easier than file and DB, just use sharedprefs

11:55 am
developer.android.com/training/data-storage/shared-preferences

11:55 am
geeksforgeeks.org/shared-preferences-in-android-with-examples
     */
}
