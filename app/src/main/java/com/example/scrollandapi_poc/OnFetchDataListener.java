package com.example.scrollandapi_poc;

import com.example.scrollandapi_poc.model.NewsHeadline;

import java.util.List;

public interface OnFetchDataListener<NewsApiResponse>{
    void onFetchData(List<NewsHeadline> list, String message);
    void onError(String message);
}
