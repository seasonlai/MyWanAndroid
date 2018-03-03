package com.example.wellhope.mywanandroid;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.FrameLayout;

import com.example.wellhope.mywanandroid.base.BaseActivity;
import com.example.wellhope.mywanandroid.base.SupportFragment;
import com.example.wellhope.mywanandroid.ui.home.HomeFragment;
import com.example.wellhope.mywanandroid.widget.BottomBar;

import butterknife.BindView;

public class MainActivity extends BaseActivity {

    @BindView(R.id.contentContainer)
    FrameLayout mContentContainer;
    @BindView(R.id.bottomBar)
    BottomBar bottomBar;

    SupportFragment[] mFragments = new SupportFragment[1];

    @Override
    protected int getContentLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void bindView(@Nullable Bundle savedInstanceState) {

        if(savedInstanceState==null){
            mFragments[0] = HomeFragment.newInstance();

            getSupportDelegate().loadMultipleRootFragment(R.id.contentContainer,0,mFragments);
        }else {
            mFragments[0] = findFragment(HomeFragment.class);

        }

        bottomBar.addItem(this, R.drawable.ic_home, "首页")
                .addItem(this, R.drawable.ic_system, "知识体系")
                .addItem(this, R.drawable.ic_person, "我的");

    }

}
