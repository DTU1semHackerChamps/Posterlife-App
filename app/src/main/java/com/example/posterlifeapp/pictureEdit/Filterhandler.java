package com.example.posterlifeapp.pictureEdit;

import android.media.Image;

import androidx.camera.core.ImageAnalysis;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

public class Filterhandler {

    List<Filter> filters = Arrays.asList(
            new Filter("Invert"),
            new Filter("Black and White"),
            new Filter("Red")
    );

    private static class Filter implements Function<Image, Image>
    {

        String name;

        Filter(String name)
        {
            this.name = name;
        }

        @Override
        public Image apply(Image image) {
            return null;
        }
    }
}
