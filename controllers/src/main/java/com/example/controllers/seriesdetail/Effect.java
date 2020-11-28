package com.example.controllers.seriesdetail;

import android.view.View;


public class Effect {
    public static class ImageLoaded<T> extends Effect {
        public ImageLoaded() {

        }
    }
    public static class CardClickedEffect<T> extends Effect {
        public T item;
        public View view;
        public CardClickedEffect(View view, T item) {
            this.view = view;
            this.item = item;
        }
    }
}
