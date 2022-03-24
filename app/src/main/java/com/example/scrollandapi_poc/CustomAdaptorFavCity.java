package com.example.scrollandapi_poc;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.scrollandapi_poc.model.CityWeather;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class CustomAdaptorFavCity  extends RecyclerView.Adapter<CustomAdaptorFavCity.CustomViewHolderFavCity> {

    private Context context;
    private ArrayList<CityWeather> cityWeather;
    private SelectListener listener;


    public class CustomViewHolderFavCity extends RecyclerView.ViewHolder {

        TextView favCityCardCityName;
        TextView favCityCardTemp;
        ImageView favCityCardImg;
        CardView favCityCardMain;

        public CustomViewHolderFavCity(@NonNull View itemView) {
            super(itemView);
            favCityCardCityName = itemView.findViewById(R.id.fav_city_card_city_name);
            favCityCardTemp = itemView.findViewById(R.id.fav_city_card_temp);
            favCityCardImg = itemView.findViewById(R.id.fav_city_card_img);
            favCityCardMain = itemView.findViewById(R.id.fav_city_card_main);;
        }
    }

    public CustomAdaptorFavCity(Context context, ArrayList<CityWeather> cityWeather, SelectListener listener){
        this.context = context;
        this.cityWeather = cityWeather;
        this.listener = listener;
    }


    @NonNull
    @Override
    public CustomViewHolderFavCity onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        System.out.println("Creating onCreateViewHolder");

        return new CustomViewHolderFavCity(LayoutInflater.from(parent.getContext()).inflate(R.layout.favorite_city_item, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolderFavCity holder, int position) {
        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@\ngenerating holder");
        holder.favCityCardCityName.setText(cityWeather.get(position).getCityName());
        holder.favCityCardTemp.setText(cityWeather.get(position).getCityTemp());
//        if(cityWeather.get(position).getCityImg()!=null){
//            Picasso.get().load(cityWeather.get(position).getCityImg()).into(holder.favCityCardImg);
//        }

//        holder.cardView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                listener.onNewsClicked(headlines.get(position));
//            }
//        });
    }

    @Override
    public int getItemCount() {
        System.out.println(cityWeather.size()+1000);
        return cityWeather.size();
    }
}
