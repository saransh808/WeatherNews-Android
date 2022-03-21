package com.example.scrollandapi_poc;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.scrollandapi_poc.model.NewsHeadline;

import java.util.HashSet;
import java.util.Set;

public class FavoriteCitiesActivity  extends AppCompatActivity {

    TextView test;

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

        if(!historySet.isEmpty()){
            test.append("Set is not empty");
            for(String city : historySet){
                test.append(" "+city);
            }
        }else{

        }


    }
}
