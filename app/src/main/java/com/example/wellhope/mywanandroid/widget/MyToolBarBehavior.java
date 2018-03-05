package com.example.wellhope.mywanandroid.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.example.wellhope.mywanandroid.R;
import com.example.wellhope.mywanandroid.utils.ContextUtils;
import com.example.wellhope.mywanandroid.utils.StatusBarUtil;

/**
 * Created by Wellhope on 2018/3/5.
 */

public class MyToolBarBehavior extends CoordinatorLayout.Behavior<View> {

    private float targetHeight = -1;
    public static final String TAG = "season";

    public MyToolBarBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
//        targetHeight = ContextUtils.dip2px(context, context.getResources().getDimension(R.dimen.banner_height));
    }

    @Override
    public boolean onStartNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View child,
                                       @NonNull View directTargetChild, @NonNull View target, int axes, int type) {

        return axes == ViewCompat.SCROLL_AXIS_VERTICAL ||
                super.onStartNestedScroll(coordinatorLayout, child, directTargetChild, target, axes, type);
    }

    @Override
    public void onNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View child,
                               @NonNull View target, int dxConsumed, int dyConsumed,
                               int dxUnconsumed, int dyUnconsumed, int type) {
//        Log.e(TAG, "onNestedScroll: child-" + child);
        if (child instanceof MyToolBar) {
            resetBarLayout(child, target);
        }
        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, type);
    }

    private void resetBarLayout(View child, View target) {
        float scrollY = getScrollYDistance((RecyclerView) target, child);

        if (targetHeight < 0 || scrollY < targetHeight) {
            animateChild((MyToolBar) child, false);
            return;
        }
        animateChild((MyToolBar) child, true);

//
//        if (targetHeight <= 0 && child.getVisibility() == View.INVISIBLE) {
//            child.setVisibility(View.VISIBLE);
//            child.setAlpha(1f);
//        }
//
//        Log.e(TAG, "resetBarLayout: targetScrollY:" + scrollY);
//        if (scrollY <= 0) {
//            if (child.getVisibility() == View.V) {
//                child.setAlpha(1f);
//                child.setVisibility(View.VISIBLE);
//                return;
//            }
//        }
//        child.setVisibility(View.VISIBLE);
//        float alpha = scrollY / targetHeight;
//        child.setAlpha(alpha > 1f ? 1f : alpha);

    }

    @Override
    public boolean onNestedFling(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View child,
                                 @NonNull View target, float velocityX, float velocityY, boolean consumed) {
        resetBarLayout(child, target);
        return super.onNestedFling(coordinatorLayout, child, target, velocityX, velocityY, consumed);
    }

    private float getScrollYDistance(RecyclerView recyclerView, View child) {
        LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        int position = layoutManager.findFirstVisibleItemPosition();
        if (position != 0)
            return targetHeight;
        View firstVisiableChildView = layoutManager.findViewByPosition(position);
        if (targetHeight < 0) {
            targetHeight = firstVisiableChildView.getHeight() - child.getHeight() - child.getTop();
        }
//        Log.e(TAG, "getScrollYDistance: position : " + position +
//                "  firstVisiableChildView.getTop() : ");
        return -firstVisiableChildView.getTop();
    }

    private boolean isAnimating;

    private void animateChild(final MyToolBar child, final boolean isShow) {

        if (child.getVisibility() == View.VISIBLE && isShow
                || ((child.getVisibility() == View.INVISIBLE || child.getVisibility() == View.GONE) && !isShow)
                || isAnimating)
            return;

        ValueAnimator animator = isShow ?
                ValueAnimator.ofFloat(0, 1).setDuration(1000)
                : ValueAnimator.ofFloat(1, 0).setDuration(1000);

        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float value = (float) valueAnimator.getAnimatedValue();
                child.setAlpha(value);
                child.alphaChange(value, isShow);
            }
        });
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationCancel(Animator animation) {
                isAnimating = false;
                super.onAnimationCancel(animation);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                isAnimating = false;
                if (!isShow)
                    child.setVisibility(View.INVISIBLE);
                child.alphaChangeEnd(isShow);
                super.onAnimationEnd(animation);
            }

            @Override
            public void onAnimationStart(Animator animation) {
                isAnimating = true;
                if (isShow)
                    child.setVisibility(View.VISIBLE);
                super.onAnimationStart(animation);
            }
        });
        animator.start();
    }
}
