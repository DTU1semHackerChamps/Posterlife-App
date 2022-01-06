package com.example.posterlifeapp.taskhandler;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import com.example.posterlifeapp.model.Poster;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public class RetrieveFeedTask extends AsyncTask<String, Void, Integer>{
    private Exception exception;
    List<String> urls;
    ArrayList<Bitmap> bitmaps;

    RetrieveFeedTask(List<Poster> urls)
    {
        for (Poster post: urls) {
            this.urls.add(post.getImageUrl());
        }
    }
    @Override
    protected Integer doInBackground(String... strings) {
        bitmaps = new ArrayList<>();
        try {
            for (String str: urls) {
                URL url = new URL(str);
                URLConnection connection = url.openConnection();
                connection.connect();
                InputStream inputStream = connection.getInputStream();
                bitmaps.add(BitmapFactory.decodeStream(inputStream));
            }
        } catch (Exception e)
        {
            this.exception = e;
            return null;
        }
        return 1;
    }
}
