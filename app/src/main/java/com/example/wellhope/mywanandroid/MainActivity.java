package com.example.wellhope.mywanandroid;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.widget.FrameLayout;

import com.example.wellhope.mywanandroid.base.BaseActivity;
import com.example.wellhope.mywanandroid.base.SupportFragment;
import com.example.wellhope.mywanandroid.ui.home.HomeFragment;
import com.example.wellhope.mywanandroid.ui.system.SystemFragment;
import com.example.wellhope.mywanandroid.utils.StatusBarUtil;
import com.example.wellhope.mywanandroid.widget.BottomBar;

import butterknife.BindView;

public class MainActivity extends BaseActivity {

    @BindView(R.id.contentContainer)
    FrameLayout mContentContainer;
    @BindView(R.id.bottomBar)
    BottomBar bottomBar;

    SupportFragment[] mFragments = new SupportFragment[2];

    @Override
    protected int getContentLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void bindView(@Nullable Bundle savedInstanceState) {

        //contentView根部局为RelativeLayout
//        StatusBarUtil.setTranslucentForImageView(this,
//                null);
//        StatusBarUtil.setColor(this, ContextCompat.getColor(this,R.color.colorPrimary),
//                0);

        if(savedInstanceState==null){
            mFragments[0] = HomeFragment.newInstance();
            mFragments[1] = SystemFragment.newInstance();
            getSupportDelegate().loadMultipleRootFragment(R.id.contentContainer,0,mFragments);
        }else {
            mFragments[0] = findFragment(HomeFragment.class);
            mFragments[1] = findFragment(SystemFragment.class);
        }

        bottomBar.addItem(this, R.drawable.ic_home, "首页")
                .addItem(this, R.drawable.ic_system, "知识体系")
                .addItem(this, R.drawable.ic_person, "我的");

        bottomBar.setTabSelectedListener(new BottomBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position, int prePosition) {
                showHideFragment(mFragments[position],mFragments[prePosition]);
            }

            @Override
            public void onTabUnselected(int position) {

            }

            @Override
            public void onTabReselected(int position) {

            }
        });
    }

}
