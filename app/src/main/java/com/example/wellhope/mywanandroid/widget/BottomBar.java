package com.example.wellhope.mywanandroid.widget;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.LinearLayout;

/**
 * Created by Wellhope on 2018/3/2.
 */

public class BottomBar extends LinearLayout {

    private static final int TRANSLATE_DURATION_MILLIS = 200;

    private final Interpolator mInterpolator = new AccelerateDecelerateInterpolator();
    private boolean mVisible = true;

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

        item.setPosition(getChildCount());
        item.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                itemClick(((BottomBarItem) view).getPosition());
            }
        });
        LayoutParams params = new LayoutParams(0,
                ViewGroup.LayoutParams.MATCH_PARENT, 1);
        item.setLayoutParams(params);
        item.setSelected(mCurPosition == item.getPosition());
        addView(item);
        return this;
    }

    public BottomBar addItem(Context context, int icon, String text) {
        addItem(new BottomBarItem(context, icon, text));
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

            }
            getChildAt(mCurPosition).setSelected(false);
            getChildAt(position).setSelected(true);
        }

        mCurPosition = position;
    }


    public void setCurrentItem(final int position) {
        post(new Runnable() {
            @Override
            public void run() {
                getChildAt(position).performClick();
            }
        });
    }

    @Nullable
    @Override
    protected Parcelable onSaveInstanceState() {
        Parcelable parcelable = super.onSaveInstanceState();
        return new SavedState(parcelable, mCurPosition);
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        super.onRestoreInstanceState(state);
        SavedState ss = (SavedState) state;
        if (mCurPosition != ss.position) {
            getChildAt(mCurPosition).setSelected(false);
            getChildAt(ss.position).setSelected(true);
        }
        this.mCurPosition = ss.position;
    }

    public interface OnTabSelectedListener {
        void onTabSelected(int position, int prePosition);

        void onTabUnselected(int position);

        void onTabReselected(int position);
    }

    public void setTabSelectedListener(OnTabSelectedListener tabSelectedListener) {
        this.tabSelectedListener = tabSelectedListener;
    }

    static class SavedState extends BaseSavedState {

        public int position;

        public SavedState(Parcel source) {
            super(source);
            this.position = source.readInt();

        }

        public SavedState(Parcelable source, int position) {
            super(source);
            this.position = position;
        }

        @Override
        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeInt(position);
        }

        public static final Creator<SavedState> CREATOR = new Creator<SavedState>() {

            @Override
            public SavedState createFromParcel(Parcel parcel) {
                return new SavedState(parcel);
            }

            @Override
            public SavedState[] newArray(int i) {
                return new SavedState[i];
            }
        };
    }

    //-------------------隐藏/显示--------------

    public void hide() {
        hide(true);
    }

    public void show() {
        show(true);
    }

    public void hide(boolean anim) {
        toggle(false, anim, false);
    }

    public void show(boolean anim) {
        toggle(true, anim, false);
    }

    public boolean isVisible() {
        return mVisible;
    }

    private void toggle(final boolean visible, final boolean animate, boolean force) {
        if (mVisible != visible || force) {
            mVisible = visible;
            int height = getHeight();
            if (height == 0 && !force) {
                ViewTreeObserver vto = getViewTreeObserver();
                if (vto.isAlive()) {
                    // view树完成测量并且分配空间而绘制过程还没有开始的时候播放动画。
                    vto.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                        @Override
                        public boolean onPreDraw() {
                            ViewTreeObserver currentVto = getViewTreeObserver();
                            if (currentVto.isAlive()) {
                                currentVto.removeOnPreDrawListener(this);
                            }
                            toggle(visible, animate, true);
                            return true;
                        }
                    });
                    return;
                }
            }
            int translationY = visible ? 0 : height;
            if (animate) {
                animate().setInterpolator(mInterpolator)
                        .setDuration(TRANSLATE_DURATION_MILLIS)
                        .translationY(translationY);
            } else {
                ViewCompat.setTranslationY(this, translationY);
            }
        }
    }
}
