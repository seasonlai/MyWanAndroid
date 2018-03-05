package com.example.wellhope.mywanandroid.widget;

import android.content.Context;
import android.support.annotation.FloatRange;
import android.support.annotation.IntRange;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;

/**
 * Created by Wellhope on 2018/3/5.
 */

public class MyToolBar extends Toolbar {


    public MyToolBar(Context context) {
        super(context);
    }

    public MyToolBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyToolBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private AlphaChangeListener mAlphaChangeListener;

    public void setAlphaChangeListener(AlphaChangeListener alphaChangeListener) {
        mAlphaChangeListener = alphaChangeListener;
    }

    public void alphaChange(@FloatRange(from = 0,to = 1) float val, boolean isShow){
        if (mAlphaChangeListener!=null)
            mAlphaChangeListener.alphaChange(val,isShow);
    }
    public void alphaChangeEnd( boolean isShow){
        if (mAlphaChangeListener!=null)
            mAlphaChangeListener.alphaChangeEnd(isShow);
    }

    public interface AlphaChangeListener{

        void alphaChange(@FloatRange(from = 0,to = 1) float val, boolean isShow);

        void alphaChangeEnd(boolean isShow);
    }
}
