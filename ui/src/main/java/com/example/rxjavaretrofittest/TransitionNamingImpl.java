package com.example.rxjavaretrofittest;

public class TransitionNamingImpl implements TransitionNaming{
    @Override
    public String getStartAnimationTag(Screen source, Listing listing, ViewElement element, String extra) {
        return source.name().concat(listing.name()).concat(element.name()).concat(extra);
    }

    @Override
    public String getEndAnimationTag(Screen current, ViewElement element) {
        return current.name().concat(element.name());
    }
}
