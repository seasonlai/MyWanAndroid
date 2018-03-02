package com.example.wellhope.mywanandroid.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

/**
 * Created by Wellhope on 2018/3/2.
 */

public class BottomBar extends LinearLayout {

    public BottomBar(Context context) {
        this(context, null);
    }

    public BottomBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BottomBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOrientation(HORIZONTAL);
    }

    private int mCurPosition = 0;

    private OnTabSelectedListener tabSelectedListener;

    public BottomBar addItem(BottomBarItem item) {
        if (item == null)
            return this;

        LayoutParams params = new LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1);
//        item.setLayoutParams(params);
        addView(item, params);
        item.setPosition(getChildCount() - 1);
        item.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                itemClick(((BottomBarItem) view).getPosition());
            }
        });
        return this;
    }

    public BottomBar addItem(Context context,int icon,String text) {
        addItem(new BottomBarItem(context,icon,text));
        return this;
    }

    private void itemClick(int position) {

        if (mCurPosition == position) {
            if (tabSelectedListener != null) {
                tabSelectedListener.onTabReselected(position);
            }
        } else {
            if (tabSelectedListener != null) {
                tabSelectedListener.onTabSelected(position, mCurPosition);
                tabSelectedListener.onTabUnselected(mCurPosition);

                getChildAt(mCurPosition).setSelected(false);
                getChildAt(position).setSelected(true);
            }
        }

        mCurPosition = position;
    }

    public interface OnTabSelectedListener {
        void onTabSelected(int position, int prePosition);

        void onTabUnselected(int position);

        void onTabReselected(int position);
    }

    public void setTabSelectedListener(OnTabSelectedListener tabSelectedListener) {
        this.tabSelectedListener = tabSelectedListener;
    }
}
