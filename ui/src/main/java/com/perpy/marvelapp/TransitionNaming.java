package com.perpy.marvelapp;

public interface TransitionNaming {
    String getStartAnimationTag(Screen source, Listing listing, ViewElement element, String extra);
    String getEndAnimationTag(Screen current, ViewElement element);
}




