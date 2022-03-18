package com.example.scrollandapi_poc;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomNewsWidget {
    private static CustomNewsWidget customNewsWidget = null;

    public static CustomNewsWidget getInstance() {
        if(customNewsWidget == null) customNewsWidget = new CustomNewsWidget();
        return customNewsWidget;
    }

    public List<Map<String, String>> getNewsWidgetElements(JSONObject reader) throws JSONException {
        ArrayList<Map<String, String>> resultList = new ArrayList<Map<String, String>>();
//        for(int index=0;index<1;index++){
            Map<String, String> newsObject = this.getNewsWidgetMapObject(reader.getJSONArray("articles").getJSONObject(0));
            resultList.add(newsObject);
//        }
        return resultList;
    }

    private Map<String, String> getNewsWidgetMapObject(JSONObject reader) throws JSONException {
        Map<String, String> news = new HashMap<String, String>();
        String publisher = reader.getJSONObject("source").getString("name");
        news.put("publisher", publisher);
        return news;
    }
}
