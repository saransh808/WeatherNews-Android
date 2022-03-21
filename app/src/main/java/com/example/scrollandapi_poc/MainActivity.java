package com.example.scrollandapi_poc;


import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Constraints;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

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

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class MainActivity extends AppCompatActivity implements SelectListener{


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


    ImageButton imgBtnSave;

    TextView  test;

    LinearLayout mainLayout;

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



        imgBtnSave = findViewById(R.id.img_btn_save);

        mainLayout = findViewById(R.id.mainLinearLayout);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(etCityNameInput.getText().length()!=0){
                    String city = etCityNameInput.getText().toString();

                    String WeatherUrl = "https://api.openweathermap.org/data/2.5/weather?units=metric&q=";
                    String APIKey = "a6144f2599dd8e4b5f8f4711edde917f";
                    processWeatherRequest(WeatherUrl, city, APIKey);

                    String NewsUrl = "https://newsapi.org/v2/everything?q=";
                    String NewsAPIKey = "9700591ad5d844b7af3f04baa1e58c69";
                    processNewsRequestRetrofit(NewsUrl, city, NewsAPIKey);
                }
            }
        });
        imgBtnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(etCityNameInput.getText().length()!=0) {
                    String city = etCityNameInput.getText().toString();
                    SharedPreferences sharedPreferences = getSharedPreferences("CityHistoryPreference", MODE_PRIVATE);
                    if(sharedPreferences.contains("history")){
                        Set<String> historySet = new HashSet<String>
                                (sharedPreferences.getStringSet("history", new HashSet<String>()));
                        historySet.add(city.toLowerCase());
                        sharedPreferences.edit().putStringSet("history", historySet).apply();

                    }else{
                        Set<String> historySet = new HashSet<String>();
                        historySet.add(city.toLowerCase());
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putStringSet("history", historySet).apply();
                    }
                }
            }
        });



    }

    //button listener
    public void toSavedCities(View view) {
        Intent intent = new Intent(this, FavoriteCitiesActivity.class);
        intent.putExtra("city", etCityNameInput.getText().toString());
        startActivity(intent);
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





    RecyclerView recyclerView;
    CustomAdaptor adaptor;
//    ProgressDialog dialog;

    private void processNewsRequestRetrofit(String url, String city, String APIKey) {
        String urlReq = url+city+"&apiKey="+APIKey;
//        dialog.setTitle("Fetching news for "+city+"...");
//        dialog.show();
        NewsRequestManager manager = new NewsRequestManager(this);
        manager.getNewsHeadlines(listener, city, APIKey);
    }

    private final OnFetchDataListener<NewsApiResponse> listener = new OnFetchDataListener<NewsApiResponse>() {
        @Override
        public void onFetchData(List<NewsHeadline> list, String message) {
            showNews(list);
//            dialog.dismiss();
        }

        @Override
        public void onError(String message) {

        }
    };

    private void showNews(List<NewsHeadline> newsList) {
        recyclerView = findViewById(R.id.recycler_main);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        adaptor = new CustomAdaptor(this, newsList, this);
        recyclerView.setAdapter(adaptor);



    }

    @Override
    public void onNewsClicked(NewsHeadline headlines) {
        startActivity(new Intent(MainActivity.this, DetailsActivity.class)
        .putExtra("data", headlines));
    }


    //https://api.openweathermap.org/data/2.5/weather?q=toronto&appid=a6144f2599dd8e4b5f8f4711edde917f
}