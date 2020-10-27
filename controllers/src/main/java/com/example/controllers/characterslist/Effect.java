package com.example.controllers.characterslist;

import android.view.View;

public class Effect {
    static class ClickCharacterEffect<T> extends Effect {
        public T item;
        public View view;

        public ClickCharacterEffect(View view, T item) {
            this.item = item;
            this.view = view;
        }
    }
}
