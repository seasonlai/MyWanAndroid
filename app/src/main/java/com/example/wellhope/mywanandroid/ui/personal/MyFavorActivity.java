package com.example.wellhope.mywanandroid.ui.personal;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.wellhope.mywanandroid.MyApp;
import com.example.wellhope.mywanandroid.R;
import com.example.wellhope.mywanandroid.base.BaseActivity;
import com.example.wellhope.mywanandroid.bean.ArticlePageBean;
import com.example.wellhope.mywanandroid.ui.article.ArticleActivity;
import com.example.wellhope.mywanandroid.ui.article.ArticleAdapter;
import com.example.wellhope.mywanandroid.ui.login.LoginActivity;

import butterknife.BindView;

public class MyFavorActivity extends BaseActivity<MyFavorPresenter> implements MyFavorContract.View {

    @Override
    protected int getContentLayout() {
        return R.layout.activity_my_favor;
    }

    public static void launch(Context context){
        context.startActivity(new Intent(context,MyFavorActivity.class));
    }

    int pageNum;

    @BindView(R.id.myFavor_rv)
    RecyclerView mRecyclerView;

    ArticleAdapter mArticleAdapter;

    @Override
    protected void bindView(@Nullable Bundle savedInstanceState) {
        mRecyclerView.setAdapter(mArticleAdapter = new ArticleAdapter(null));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
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
                    LoginActivity.launch(MyFavorActivity.this);
                }
            }
        });

        mArticleAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ArticleActivity.launch(MyFavorActivity.this, (Parcelable) adapter.getItem(position));
            }
        });

        mArticleAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                mPresenter.getFavor(pageNum);
            }
        }, mRecyclerView);

    }

    @Override
    public void loadResult(ArticlePageBean articlePage) {
        if (articlePage.getDatas() == null || articlePage.getDatas().size() == 0) {
            mArticleAdapter.setEnableLoadMore(false);
            View view = LayoutInflater.from(this).inflate(R.layout.item_tips,null);
            ((TextView)view.findViewById(R.id.tips_content)).setText("拉到底啦");
            mArticleAdapter.addFooterView(view);
            return;
        }
        pageNum++;
        mArticleAdapter.replaceData(articlePage.getDatas());
    }

    @Override
    public void loadMoreResult(ArticlePageBean articlePage) {
        mArticleAdapter.loadMoreComplete();
        if (articlePage.getDatas() == null || articlePage.getDatas().size() == 0) {
            mArticleAdapter.setEnableLoadMore(false);
            View view = LayoutInflater.from(this).inflate(R.layout.item_tips,null);
            ((TextView)view.findViewById(R.id.tips_content)).setText("拉到底啦");
            mArticleAdapter.addFooterView(view);
            return;
        }
        pageNum++;
        mArticleAdapter.addData(articlePage.getDatas());
    }

    @Override
    public void unCollectArticle(int position) {
        ArticlePageBean.ItemBean item = mArticleAdapter.getItem(position);
        item.setCollect(false);
        mArticleAdapter.setData(position, item);
    }

    @Override
    public void collectArticle(int position) {
        ArticlePageBean.ItemBean item = mArticleAdapter.getItem(position);
        item.setCollect(true);
        mArticleAdapter.setData(position, item);
    }
}
