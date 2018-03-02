package com.example.wellhope.mywanandroid.widget;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.wellhope.mywanandroid.R;
import com.example.wellhope.mywanandroid.utils.ContextUtils;

/**
 * Created by Wellhope on 2018/3/2.
 */

public class BottomBarItem extends LinearLayout {

    public BottomBarItem(Context context, @DrawableRes int icon, String text) {
        this(context, null, icon, text);
    }

    public BottomBarItem(Context context, @Nullable AttributeSet attrs, @DrawableRes int icon, String text) {
        this(context, attrs, 0, icon, text);
    }

    public BottomBarItem(Context context, @Nullable AttributeSet attrs, @DrawableRes int defStyleAttr, int icon, String text) {
        super(context, attrs, defStyleAttr);
        init(context,icon,text);
    }

    private ImageView iconView;
    private TextView textView;
    private Context mContext;
    private int position;

    private int unSelectColor;
    private int selectColor;

    private void init(Context context, @DrawableRes int icon, String text) {
        selectColor=ContextCompat.getColor(mContext, R.color.colorPrimary);
        unSelectColor =ContextCompat.getColor(mContext, R.color.tab_unSelect);
        mContext = context;
        setOrientation(VERTICAL);
        setPadding(4,4,4,4);
        iconView = new ImageView(context);
        iconView.setImageResource(icon);
        LayoutParams iconParams = new LayoutParams(ContextUtils.px2Dp(context,20),ContextUtils.px2Dp(context,20));
        iconParams.gravity = Gravity.CENTER_HORIZONTAL;
        iconParams.topMargin = ContextUtils.px2Dp(context,2.5f);
        iconView.setLayoutParams(iconParams);

        textView = new TextView(context);
        textView.setText(text);
        LayoutParams textParams = new LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        textParams.gravity = Gravity.CENTER_HORIZONTAL;
        textParams.topMargin = ContextUtils.px2Dp(context,2.5f);
        textParams.bottomMargin = ContextUtils.px2Dp(context,2.5f);
        textView.setLayoutParams(iconParams);

        addView(iconView);
        addView(textView);
    }


    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    @Override
    public void setSelected(boolean selected) {
        super.setSelected(selected);
        if (selected) {
           setColor(selectColor);

        } else {
            setColor(unSelectColor);
        }
    }

    private void setColor(int color){
        iconView.setColorFilter(color);
        textView.setTextColor(color);
    }

}
