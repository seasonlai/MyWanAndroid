package com.example.wellhope.mywanandroid;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.FrameLayout;

import com.example.wellhope.mywanandroid.base.BaseActivity;
import com.example.wellhope.mywanandroid.widget.BottomBar;

import butterknife.BindView;

public class MainActivity extends BaseActivity {

    @BindView(R.id.contentContainer)
    FrameLayout mContentContainer;
    @BindView(R.id.bottomBar)
    BottomBar bottomBar;

    @Override
    protected int getContentLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void bindView(@Nullable Bundle savedInstanceState) {

        bottomBar.addItem(this, R.drawable.ic_home, "首页")
                .addItem(this, R.drawable.ic_system, "知识体系")
                .addItem(this, R.drawable.ic_person, "我的");

    }

}
