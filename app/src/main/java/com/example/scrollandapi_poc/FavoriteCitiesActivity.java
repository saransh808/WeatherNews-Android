package com.example.scrollandapi_poc;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.RequestFuture;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.scrollandapi_poc.model.CityWeather;
import com.example.scrollandapi_poc.model.NewsHeadline;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class FavoriteCitiesActivity  extends AppCompatActivity {

    TextView test;
    ArrayList<CityWeather> citiesWeather = new ArrayList<CityWeather>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.favorite_cities);

        test = findViewById(R.id.test);
        String s = (String)getIntent().getExtras().getString("city");
        test.setText("");



        SharedPreferences sharedPreferences = getSharedPreferences("CityHistoryPreference", MODE_PRIVATE);
        Set<String> historySet = new HashSet<String>();
        historySet = sharedPreferences.getStringSet("history",historySet);

        ArrayList<String> cities = new ArrayList<String>();
        if(!historySet.isEmpty()){
            test.append("Set is not empty");
            for(String city : historySet){
                test.append(" "+city);
                cities.add(city);
            }
        }else{

        }

        generateFavWeatherReportCityCards(cities);

    }

    private void generateFavWeatherReportCityCards(ArrayList<String> cities) {

        citiesWeather.clear();
        String WeatherUrl = "https://api.openweathermap.org/data/2.5/weather?units=metric&q=";
        String APIKey = "a6144f2599dd8e4b5f8f4711edde917f";
        for(String city : cities){
//            processWeatherRequestSync(WeatherUrl, city, APIKey);
            break;
        }
        test.append("before generateCards");

        generateCards(citiesWeather);
    }



    private void generateCards(ArrayList<CityWeather> citiesWeather) {

        citiesWeather.add(new CityWeather("Delhi","23"+(char)0x00B0+"C",null));
        citiesWeather.add(new CityWeather("Toronto","4"+(char)0x00B0+"C",null));
        if(!citiesWeather.isEmpty()) {
            for (CityWeather city : citiesWeather)
                test.append(city.getCityTemp());
        }else{
            test.append("API didn't work");
        }
        RecyclerView recyclerView;
        CustomAdaptorFavCity adaptor;

        recyclerView = findViewById(R.id.recycler_favorite_cities);
        adaptor = new CustomAdaptorFavCity(this, citiesWeather, null);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adaptor);
    }

    private void processWeatherRequest(String url, String city, String APIKey) {
        String urlReq = url+city+"&appid="+APIKey;
        StringRequest request = new StringRequest(Request.Method.GET, urlReq, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject reader = new JSONObject(response);
                    displayWeatherOnScreen(reader);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error){
            }
        });
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(request);
    }

    private void processWeatherRequestSync(String url, String city, String APIKey) {
        String urlReq = url+city+"&appid="+APIKey;
        RequestFuture<JSONObject> future = RequestFuture.newFuture();
        JsonObjectRequest request = new JsonObjectRequest(urlReq, new JSONObject(), future, future);
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(request);
        try{
            JSONObject response = future.get();
            displayWeatherOnScreen(response);
        }catch (Exception e){

        }

    }



    private void displayWeatherOnScreen(JSONObject reader) throws JSONException {

        CustomWeatherWidget customWeatherWidget = CustomWeatherWidget.getInstance();
        Map<String, String> data = customWeatherWidget.getWeatherWidgetElements(reader);

        CityWeather cityWeather = new CityWeather();
        cityWeather.setCityName(data.get("title"));
        cityWeather.setCityTemp(data.get("temp"));
        cityWeather.setCityImg("https://openweathermap.org/img/wn/"+data.get("icon")+"@4x.png");

        citiesWeather.add(cityWeather);
    }
}
