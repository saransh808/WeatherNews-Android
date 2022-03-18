package com.example.scrollandapi_poc;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

public class CustomViewHolder extends RecyclerView.ViewHolder {

    TextView textTitle;
    TextView textSource;
    TextView textAuthor;
    ImageView imgHeadline;


    CardView cardView;

    public CustomViewHolder(@NonNull View itemView) {
        super(itemView);
        textTitle = itemView.findViewById(R.id.text_title);
        textSource = itemView.findViewById(R.id.text_source);
        textAuthor = itemView.findViewById(R.id.text_author);


        imgHeadline = itemView.findViewById(R.id.img_headline);

        cardView = itemView.findViewById(R.id.main_news_container);
    }
}
