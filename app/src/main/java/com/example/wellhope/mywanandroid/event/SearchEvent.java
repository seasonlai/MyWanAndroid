package com.example.wellhope.mywanandroid.event;

/**
 * Created by Wellhope on 2018/3/9.
 */

public class SearchEvent {

    public String key;

    boolean beginSearch;


    public SearchEvent(String key) {
        this(key,false);
    }
    public SearchEvent(String key,boolean beginSearch) {
        this.key = key;
        this.beginSearch = beginSearch;
    }

    public boolean isBeginSearch() {
        return beginSearch;
    }
}
