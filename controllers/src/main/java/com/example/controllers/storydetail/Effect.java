package com.example.controllers.storydetail;

import android.view.View;

public class Effect {
    public static class ImageLoaded<T> extends Effect {
        public ImageLoaded() {

        }
    }

    public static class CardClickedEffect<T> extends Effect {
        public View view;
        public T item;
        public CardClickedEffect(View view, T item ) {
            this.view = view;
            this.item = item;
        }
    }
}
