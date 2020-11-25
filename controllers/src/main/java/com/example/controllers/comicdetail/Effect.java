package com.example.controllers.comicdetail;

import android.view.View;

public class Effect {
    public static class ImageLoaded<T> extends Effect {
        public ImageLoaded(){

        }
    }
    public static class ClickCardEffect<T> extends Effect {
        public T item;
        public View view;
        public ClickCardEffect (View view, T item) {
            this.view = view;
            this.item = item;
        }
    }
}
