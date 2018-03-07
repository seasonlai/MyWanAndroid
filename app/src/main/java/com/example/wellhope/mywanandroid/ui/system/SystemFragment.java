package com.example.wellhope.mywanandroid.ui.system;


import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.wellhope.mywanandroid.MyApp;
import com.example.wellhope.mywanandroid.R;
import com.example.wellhope.mywanandroid.base.BaseFragment;
import com.example.wellhope.mywanandroid.base.SupportFragment;
import com.example.wellhope.mywanandroid.bean.SystemBean;
import com.example.wellhope.mywanandroid.widget.CustomViewPager;

import java.util.List;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class SystemFragment extends BaseFragment<SystemPresenter> implements SystemContract.View {

    @BindView(R.id.rv_kind)
    RecyclerView mRvKind;

    @BindView(R.id.viewpager)
    CustomViewPager mViewPager;

    BaseQuickAdapter mRvAdapter;

    @BindView(R.id.slidingTabLayout)
    com.flyco.tablayout.SlidingTabLayout slidingTabLayout;

    SystemAdapter mSystemAdapter;

    int curFirstKind;

    @Override
    protected int getLayoutID() {
        return R.layout.fragment_system;
    }

    @Override
    protected void viewCreated(View view, @Nullable Bundle savedInstanceState) {
        mRvKind.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false));
        mRvKind.setAdapter(mRvAdapter = new BaseQuickAdapter<SystemBean, BaseViewHolder>(R.layout.item_system_kind) {

            @Override
            protected void convert(BaseViewHolder helper, SystemBean item) {
                helper.setText(R.id.tv_kind, item.getName());
                if (curFirstKind == helper.getAdapterPosition()) {
                    helper.setTextColor(R.id.tv_kind, ContextCompat.getColor(MyApp.getContext(),
                            R.color.colorAccent));
                } else {
                    helper.setTextColor(R.id.tv_kind, Color.rgb(0, 0, 0));
                }
            }
        });

        mRvAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (position != curFirstKind) {
                    loadSecondKind(((SystemBean) adapter.getItem(position)).getChildren());
                    curFirstKind = position;
                }
            }
        });
    }

    @Override
    protected void lazyInit() {
        mPresenter.getSystemKind();
    }

    @Override
    public void loadFirstKind(final List<SystemBean> systemBeans) {
        mRvAdapter.addData(systemBeans);
        loadSecondKind(systemBeans.get(0).getChildren());
    }

    @Override
    public void loadSecondKind(List<SystemBean.SystemChild> systemChildren) {
        mViewPager.setAdapter(mSystemAdapter = new SystemAdapter(getChildFragmentManager(), systemChildren));
        mViewPager.setOffscreenPageLimit(2);
        mViewPager.setCurrentItem(0, false);
        slidingTabLayout.setViewPager(mViewPager);
    }

    public static SupportFragment newInstance() {
        return new SystemFragment();
    }
}
