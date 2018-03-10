package com.example.wellhope.mywanandroid.ui.system.articles;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.wellhope.mywanandroid.MyApp;
import com.example.wellhope.mywanandroid.R;
import com.example.wellhope.mywanandroid.base.BaseFragment;
import com.example.wellhope.mywanandroid.bean.ArticlePageBean;
import com.example.wellhope.mywanandroid.event.LoginEvent;
import com.example.wellhope.mywanandroid.event.RxBus;
import com.example.wellhope.mywanandroid.ui.article.ArticleActivity;
import com.example.wellhope.mywanandroid.ui.article.ArticleAdapter;
import com.example.wellhope.mywanandroid.ui.login.LoginActivity;

import java.util.List;

import butterknife.BindView;
import io.reactivex.functions.Consumer;

/**
 * Created by Wellhope on 2018/3/10.
 */

public class ArticleListFragment extends BaseFragment<ArticleListPresenter> implements ArticleListContract.View {
    private int pageNum;

    @Override
    protected int getLayoutID() {
        return R.layout.fragment_article_list;
    }

    static final String CID = "CID_ARTICLE";

    public static ArticleListFragment newInstance(int cid) {
        ArticleListFragment fragment = new ArticleListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(CID, cid);
        fragment.setArguments(bundle);
        return fragment;
    }

    @BindView(R.id.article_list_rv)
    RecyclerView mRecyclerView;

    ArticleAdapter mArticleAdapter;

    @Override
    protected void viewCreated(View view, @Nullable Bundle savedInstanceState) {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setAdapter(mArticleAdapter = new ArticleAdapter(null));
        mArticleAdapter.setEnableLoadMore(true);
        mArticleAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (MyApp.isLogin()) {
                    ArticlePageBean.ItemBean item = (ArticlePageBean.ItemBean) adapter.getItem(position);
                    if (item.isCollect()) {
                        mPresenter.unCollectArticle(item.getId(), position);
                    } else {
                        mPresenter.collectArticle(item.getId(), position);
                    }
                } else {
                    LoginActivity.launch(getContext());
                }
            }
        });
        mArticleAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ArticleActivity.launch(getContext(), (Parcelable) adapter.getItem(position));
            }
        });
    }

    @Override
    protected void lazyInit() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            mPresenter.getArticles(bundle.getInt(CID), 0);
        }
    }

    @Override
    public void loadData(ArticlePageBean articlePage) {
        if (articlePage.getDatas() == null || articlePage.getDatas().size() == 0) {
            mArticleAdapter.setEnableLoadMore(false);
            View view = LayoutInflater.from(getContext()).inflate(R.layout.item_tips,null);
            ((TextView)view.findViewById(R.id.tips_content)).setText("拉到底啦");
            mArticleAdapter.addFooterView(view);
            return;
        }
        pageNum++;
        mArticleAdapter.replaceData(articlePage.getDatas());
    }

    @Override
    public void loadMoreData(ArticlePageBean articlePage) {
        mArticleAdapter.loadMoreComplete();
        if (articlePage.getDatas() == null || articlePage.getDatas().size() == 0) {
            mArticleAdapter.setEnableLoadMore(false);
            View view = LayoutInflater.from(getContext()).inflate(R.layout.item_tips,null);
            ((TextView)view.findViewById(R.id.tips_content)).setText("拉到底啦");
            mArticleAdapter.addFooterView(view);
            return;
        }
        pageNum++;
        mArticleAdapter.addData(articlePage.getDatas());
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
