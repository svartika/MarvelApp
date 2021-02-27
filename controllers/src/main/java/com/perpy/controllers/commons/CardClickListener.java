package com.perpy.controllers.commons;

import android.view.View;

public interface CardClickListener<T> {
    public void invoke(View view, T character);
 }
