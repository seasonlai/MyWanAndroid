package com.example.wellhope.mywanandroid.ui.system;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.wellhope.mywanandroid.bean.SystemBean;
import com.example.wellhope.mywanandroid.ui.system.articles.ArticleListFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Wellhope on 2018/3/7.
 */

public class SystemAdapter extends FragmentStatePagerAdapter {

    private List<SystemBean.SystemChild> kinds;

    public SystemAdapter(FragmentManager fm, List<SystemBean.SystemChild> kinds) {
        super(fm);
        this.kinds = kinds == null ? new ArrayList<SystemBean.SystemChild>() : kinds;
    }


    public void updateKind(List<SystemBean.SystemChild> kinds) {
        this.kinds = kinds == null ? new ArrayList<SystemBean.SystemChild>() : kinds;
        notifyDataSetChanged();
    }

    @Override
    public Fragment getItem(int position) {
        return ArticleListFragment.newInstance(kinds.get(position).getId());
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return kinds.get(position).getName();
    }

    @Override
    public int getCount() {
        return kinds.size();
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }
}
