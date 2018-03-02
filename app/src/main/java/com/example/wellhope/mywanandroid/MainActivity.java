package com.example.wellhope.mywanandroid;

import android.os.Bundle;
import android.widget.FrameLayout;

import com.example.wellhope.mywanandroid.base.BaseActivity;
import com.example.wellhope.mywanandroid.widget.BottomBar;
import com.example.wellhope.mywanandroid.widget.BottomBarItem;

import butterknife.BindView;

public class MainActivity extends BaseActivity {

    @BindView(R.id.container)
    FrameLayout container;
    @BindView(R.id.bottomBar)
    BottomBar bottomBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomBar.addItem(this,R.drawable.ic_home,"首页")
        .addItem(this,R.drawable.ic_system,"知识体系")
        .addItem(this,R.drawable.ic_person,"我的");


    }
}
