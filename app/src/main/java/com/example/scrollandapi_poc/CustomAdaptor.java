package com.example.scrollandapi_poc;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.scrollandapi_poc.model.NewsHeadline;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CustomAdaptor extends RecyclerView.Adapter<CustomViewHolder> {

    private Context context;
    private List<NewsHeadline> headlines;

    public CustomAdaptor(Context context, List<NewsHeadline> headlines) {
        this.context = context;
        this.headlines = headlines;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CustomViewHolder(LayoutInflater.from(context).inflate(R.layout.headline_list_items, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        holder.textTitle.setText(headlines.get(position).getTitle());
        holder.textSource.setText(headlines.get(position).getSource().getName());
        if(headlines.get(position).getUrlToImage()!=null){
            Picasso.get().load(headlines.get(position).getUrlToImage()).into(holder.imgHeadline);
        }
    }

    @Override
    public int getItemCount() {
        return headlines.size();
    }
}
