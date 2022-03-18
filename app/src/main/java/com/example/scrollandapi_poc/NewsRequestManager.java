package com.example.scrollandapi_poc;

import android.content.Context;
import android.widget.Toast;

import com.example.scrollandapi_poc.model.NewsApiResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class NewsRequestManager {
    Context context;

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://newsapi.org/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();



    public void getNewsHeadlines(OnFetchDataListener listener, String query, String apiKey){
        CallNewsApi callNewsApi = retrofit.create(CallNewsApi.class);
        Call<NewsApiResponse> call = callNewsApi.callHeadlines(query,apiKey);

        try{
            call.enqueue(new Callback<NewsApiResponse>() {
                @Override
                public void onResponse(Call<NewsApiResponse> call, Response<NewsApiResponse> response) {
                    if(!response.isSuccessful())
                        Toast.makeText(context, "error", Toast.LENGTH_LONG).show();
                    listener.onFetchData(response.body().getArticles(), response.message());
                }

                @Override
                public void onFailure(Call<NewsApiResponse> call, Throwable t) {
                    listener.onError("Request Failed");
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    public NewsRequestManager(Context context){
        this.context = context;
    }

    public interface CallNewsApi {
        @GET("everything")
        Call<NewsApiResponse> callHeadlines(
            @Query("q") String query,
            @Query("apiKey") String api_key

        );
    }
}
