package com.example.scrollandapi_poc;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;


public class CustomImageHandler extends AsyncTask<Void, Void, Bitmap> {

    private String url;
    private ImageView imageView;

    Bitmap bitmap;

    public CustomImageHandler(String url, ImageView imageView){
        this.url = url;
        this.imageView = imageView;
    }


    @Override
    protected Bitmap doInBackground(Void... voids) {
        try{
            URL connection = new URL(this.url);
            InputStream input = connection.openStream();
            bitmap = BitmapFactory.decodeStream(input);
            Bitmap resizedBitmap = Bitmap.createScaledBitmap(bitmap, 1000, 1000, true);
            return resizedBitmap;
        }catch(Exception e){

        }
        return null;
    }

    @Override
    protected void onPostExecute(Bitmap result) {
        super.onPreExecute();
        this.imageView.setImageBitmap(result);
    }
}
