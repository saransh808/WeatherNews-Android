package com.example.scrollandapi_poc;


import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.scrollandapi_poc.model.NewsApiResponse;
import com.example.scrollandapi_poc.model.NewsHeadline;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainActivity extends AppCompatActivity {


    TextView tvCityName;
    TextView tvTemp;
    ImageView ivWeatherMainImage;

    TextView tvFeelsLike;
    TextView tvWeatherMainDesc;


    TextView tvSunriseTime;
    TextView tvWindSpeed;
    TextView tvHumidity;
    TextView tvSunsetTime;


    Button btnSearch;
    EditText etCityNameInput;
    CardView mainCard;



    TextView  test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        etCityNameInput = findViewById(R.id.cityNameInput);
        btnSearch = findViewById(R.id.btn);

        tvCityName = findViewById(R.id.CityName);
        tvFeelsLike = findViewById(R.id.feelsLike);

        tvTemp = findViewById(R.id.temp);
        ivWeatherMainImage = findViewById(R.id.weatherMainImage);

        tvWeatherMainDesc = findViewById(R.id.weatherMainDesc);



        tvSunriseTime = findViewById(R.id.tvSunriseTime);
        tvWindSpeed = findViewById(R.id.tvWindSpeed);
        tvHumidity = findViewById(R.id.tvHumidity);
        tvSunsetTime = findViewById(R.id.tvSunsetTime);

        mainCard = findViewById(R.id.mainCard);



        test = findViewById(R.id.newsHeader);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(etCityNameInput.getText().length()!=0){
                    String city = etCityNameInput.getText().toString();

                    /*
                    Handling weather widget card
                     */
                    String WeatherUrl = "https://api.openweathermap.org/data/2.5/weather?units=metric&q=";
                    String APIKey = "a6144f2599dd8e4b5f8f4711edde917f";
                    processWeatherRequest(WeatherUrl, city, APIKey);

//                    https://newsapi.org/v2/everything?q=vapi&apiKey=9700591ad5d844b7af3f04baa1e58c69

                    /*
                    Handling news widget card
                     */
                    String NewsUrl = "https://newsapi.org/v2/everything?q=";
                    String NewsAPIKey = "9700591ad5d844b7af3f04baa1e58c69";
                    processNewsRequest(NewsUrl, city, NewsAPIKey);
                    processNewsRequestRetrofit(NewsUrl, city, NewsAPIKey);

                }
            }
        });
        try{

        }catch (Exception e){
        }



    }

    //button listener
    public void toSavedCities(View view) {
//        Intent intent = new Intent(this, MainWeatherActivity.class);
//        intent.putExtra("city", e.getText().toString());
//        startActivity(intent);
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
        }){
//            @Nullable
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String, String> mp = new HashMap<String, String>();
//                mp.put("q","toronto");
//                mp.put("appid","a6144f2599dd8e4b5f8f4711edde917f");
//                return mp;
//            }

        };
//        Toast.makeText(this, urlReq, Toast.LENGTH_LONG).show();
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(request);
    }

    @SuppressWarnings({})
//    @RequiresApi(api = Build.VERSION_CODES.O)
    private void displayWeatherOnScreen(JSONObject reader) throws JSONException {
        mainCard.setVisibility(View.GONE);
        mainCard.setAlpha(0f);
        mainCard.setVisibility(View.VISIBLE);
        mainCard.animate()
                .alpha(1f)
                .setDuration(getResources().getInteger(android.R.integer.config_longAnimTime))
                .setListener(null);
        tvCityName.setTextColor(Color.WHITE);

        CustomWeatherWidget customWeatherWidget = CustomWeatherWidget.getInstance();
        Map<String, String> data = customWeatherWidget.getWeatherWidgetElements(reader);

        this.setTitle("Weather | " + data.get("title"));


        tvCityName.setText(data.get("title"));
        tvWeatherMainDesc.setText(data.get("weather"));
        tvTemp.setText(data.get("temp"));
        tvFeelsLike.setText(data.get("feels_like"));

        CustomImageHandler handler = new CustomImageHandler(" https://openweathermap.org/img/wn/"+data.get("icon")+"@4x.png", ivWeatherMainImage);
        handler.execute();

        tvSunriseTime.setText(data.get("sunrise"));
        tvWindSpeed.setText(data.get("wind"));
        tvHumidity.setText(data.get("humidity"));
        tvSunsetTime.setText(data.get("sunset"));
    }




    private void processNewsRequest(String url, String city, String APIKey) {

        String urlReq = url+city+"&apiKey="+APIKey;
//        etCityNameInput.setText(urlReq);
        StringRequest request = new StringRequest(Request.Method.GET, urlReq, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                test.setText("onResponse");
                try {
                    JSONObject reader = new JSONObject(response);
                    displayNewsOnScreen(reader);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error){
//                test.setText("Error");
            }
        }){
//            @Nullable
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String, String> mp = new HashMap<String, String>();
//                mp.put("q","toronto");
//                mp.put("appid","a6144f2599dd8e4b5f8f4711edde917f");
//                return mp;
//            }

        };
//        Toast.makeText(this, urlReq, Toast.LENGTH_LONG).show();
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(request);
    }


    private void processNewsRequestRetrofit(String url, String city, String APIKey) {
        String urlReq = url+city+"&apiKey="+APIKey;


        NewsRequestManager manager = new NewsRequestManager(this);
        manager.getNewsHeadlines(listener, city, APIKey);




    }

    private final OnFetchDataListener<NewsApiResponse> listener = new OnFetchDataListener<NewsApiResponse>() {
        @Override
        public void onFetchData(List<NewsHeadline> list, String message) {

            showNews(list);
        }

        @Override
        public void onError(String message) {

        }
    };

    private void showNews(List<NewsHeadline> list) {
        test.setText(list.get(0).getAuthor());
    }


    private void displayNewsOnScreen(JSONObject reader) throws JSONException {
//        CustomNewsWidget customNewsWidget = CustomNewsWidget.getInstance();
//        List<Map<String, String>> data = customNewsWidget.getNewsWidgetElements(reader);


//
//        Map<String, String> news = new HashMap<String, String>();
//        String publisher = reader.getJSONArray("articles").getJSONObject(0).getJSONObject("source").getString("name");
//        news.put("publisher", publisher);


//        test.setText(data.get(0).get("publisher"));

    }

    private void generateCardContent(){

    }

    private void displayCardOnScreen(){

    }



    //https://api.openweathermap.org/data/2.5/weather?q=toronto&appid=a6144f2599dd8e4b5f8f4711edde917f
}