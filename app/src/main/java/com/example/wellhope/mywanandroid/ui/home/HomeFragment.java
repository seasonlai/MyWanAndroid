package com.example.wellhope.mywanandroid.ui.home;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.wellhope.mywanandroid.MyApp;
import com.example.wellhope.mywanandroid.R;
import com.example.wellhope.mywanandroid.base.BaseFragment;
import com.example.wellhope.mywanandroid.bean.ArticlePageBean;
import com.example.wellhope.mywanandroid.bean.BannerBean;
import com.example.wellhope.mywanandroid.event.LoginEvent;
import com.example.wellhope.mywanandroid.event.RxBus;
import com.example.wellhope.mywanandroid.ui.login.LoginActivity;
import com.example.wellhope.mywanandroid.ui.search.SearchActivity;
import com.example.wellhope.mywanandroid.utils.StatusBarUtil;
import com.example.wellhope.mywanandroid.widget.MyToolBar;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.functions.Consumer;

public class HomeFragment extends BaseFragment<HomePresenter> implements HomeContract.View {

    private static final String TAG = "HomeFragment";

    @BindView(R.id.recycleView_home)
    RecyclerView mRecyclerView;

    @BindView(R.id.myToolbar)
    MyToolBar mMyToolBar;

    Banner mBanner;

    ArticleAdapter mArticleAdapter;
    View mBanner_view;

    List<BannerBean> mBannerList;
    List<ArticlePageBean.ItemBean> mArticleList;

    int pageNum;


    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    protected int getLayoutID() {
        return R.layout.fragment_home;
    }

    @Override
    protected void viewCreated(View view, @Nullable Bundle savedInstanceState) {

        StatusBarUtil.setTranslucentForImageView(getActivity(),
                mMyToolBar);
        pageNum=0;
        mMyToolBar.setAlphaChangeListener(new MyToolBar.AlphaChangeListener() {
            @Override
            public void alphaChange(float val, boolean isShow) {
                StatusBarUtil.setTranslucentColor(getActivity(),
                        ContextCompat.getColor(getContext(), R.color.colorPrimary),
                        (int) (255 - 255 * val));
            }

            @Override
            public void alphaChangeEnd(boolean isShow) {
                if (isShow) {
                    StatusBarUtil.setTranslucentColor(getActivity(),
                            ContextCompat.getColor(getContext(), R.color.colorPrimary), 0);
                } else {
                    StatusBarUtil.setTranslucentColor(getActivity());
                }
            }
        });


        mBanner_view = view.inflate(getContext(), R.layout.home_headerview, null);
        mBanner = mBanner_view.findViewById(R.id.banner);
        mBanner.setBannerStyle(BannerConfig.NUM_INDICATOR_TITLE)
                .setIndicatorGravity(BannerConfig.RIGHT)
                .setImageLoader(new ImageLoader() {
                    @Override
                    public void displayImage(Context context, Object path, ImageView imageView) {
                        Glide.with(context)
                                .load(path)
                                .apply(new RequestOptions()
                                        .centerCrop()
                                        .diskCacheStrategy(DiskCacheStrategy.ALL))
                                .transition(new DrawableTransitionOptions().crossFade(800))
                                .into(imageView);
                    }
                })
                .setDelayTime(3000);
        mBanner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                if (mBannerList.size() < 1) return;
//                bannerToRead(mBannerList.get(position));
                ArticleActivity.launch(HomeFragment.this.getActivity(), mBannerList.get(position));
            }
        });
        mBannerList = new ArrayList<>();
        mArticleList = new ArrayList<>();
        mRecyclerView.setAdapter(mArticleAdapter = new ArticleAdapter(mArticleList));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mArticleAdapter.setEnableLoadMore(true);
        mArticleAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if(MyApp.isLogin()){
                    ArticlePageBean.ItemBean item = (ArticlePageBean.ItemBean) adapter.getItem(position);
                    if(item.isCollect()){
                        mPresenter.unCollectArticle(item.getId(),position);
                    }else {
                        mPresenter.collectArticle(item.getId(),position);
                    }
                }else {
                    LoginActivity.launch(getContext());
                }
            }
        });
        mArticleAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

            }
        });

        mArticleAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {

            }
        });

        RxBus.getInstance().toFlowable(LoginEvent.class)
                .subscribe(new Consumer<LoginEvent>() {
                    @Override
                    public void accept(LoginEvent loginEvent) throws Exception {
                        pageNum = 0;
                        mPresenter.getPageArticle(pageNum);
                    }
                });
    }

    @OnClick(R.id.iv_search)
    public void search() {
        getActivity().startActivity(new Intent(getContext(), SearchActivity.class));
    }

    @Override
    protected void lazyInit() {
        this.mPresenter.getBanner();
        this.mPresenter.getPageArticle(pageNum);
    }

    @Override
    public void loadBanner(List<BannerBean> bannerList) {
//        mBanner.removeAllViews();
        int size = bannerList.size();
        mBannerList.addAll(bannerList);
        List<String> titleList = new ArrayList<>(size);
        List<String> urlList = new ArrayList<>(size);
        for (BannerBean bannerBean : bannerList) {
            titleList.add(bannerBean.getTitle());
            urlList.add(bannerBean.getImagePath());
            Log.e(TAG, "loadBanner: title: " + bannerBean.getTitle()
                    + "  url: " + bannerBean.getUrl());
        }
        mBanner.setImages(urlList);
        mBanner.setBannerTitles(titleList);
        mBanner.start();
        mArticleAdapter.addHeaderView(mBanner_view);
    }

    @Override
    public void loadArticle(List<ArticlePageBean.ItemBean> articleList) {
        mArticleAdapter.replaceData(articleList);
    }

    @Override
    public void collectArticle(int position) {
        ArticlePageBean.ItemBean item = mArticleAdapter.getItem(position);
        item.setCollect(true);
        mArticleAdapter.setData(position,item);
    }

    @Override
    public void unCollectArticle(int position) {
        ArticlePageBean.ItemBean item = mArticleAdapter.getItem(position);
        item.setCollect(false);
        mArticleAdapter.setData(position,item);
    }
}
