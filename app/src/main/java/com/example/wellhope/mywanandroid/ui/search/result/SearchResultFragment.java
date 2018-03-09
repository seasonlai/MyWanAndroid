package com.example.wellhope.mywanandroid.ui.search.result;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.wellhope.mywanandroid.MyApp;
import com.example.wellhope.mywanandroid.R;
import com.example.wellhope.mywanandroid.base.BaseFragment;
import com.example.wellhope.mywanandroid.bean.ArticlePageBean;
import com.example.wellhope.mywanandroid.event.RxBus;
import com.example.wellhope.mywanandroid.event.SearchEvent;
import com.example.wellhope.mywanandroid.ui.home.ArticleAdapter;
import com.example.wellhope.mywanandroid.ui.login.LoginActivity;

import butterknife.BindView;
import io.reactivex.functions.Consumer;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchResultFragment extends BaseFragment<SearchResultPresenter> implements SearchResultContract.View {

    private static final String SEARCH_KEY = "search_key";

    int pageNum;

    @BindView(R.id.result_rv)
    RecyclerView mRecyclerView;

    ArticleAdapter mArticleAdapter;


    public static SearchResultFragment newInstance() {
        SearchResultFragment fragment = new SearchResultFragment();
        return fragment;
    }

    public static SearchResultFragment newInstance(String searchKey) {
        SearchResultFragment fragment = new SearchResultFragment();
        Bundle bundle = new Bundle();
        bundle.putString(SEARCH_KEY,searchKey);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void loadResult(ArticlePageBean articlePage) {
        if (articlePage.getDatas() == null || articlePage.getDatas().size() == 0) {
            mArticleAdapter.setEnableLoadMore(false);
            View view = LayoutInflater.from(getContext()).inflate(R.layout.item_tips,null);
            ((TextView)view.findViewById(R.id.tips_content)).setText("无相关结果");
            mArticleAdapter.addFooterView(view);
            return;
        }
        mArticleAdapter.replaceData(articlePage.getDatas());
        pageNum=1;
    }

    @Override
    public void loadMoreResult(ArticlePageBean articlePage) {
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

    @Override
    protected int getLayoutID() {
        return R.layout.fragment_search_result;
    }

    private String searchKey;

    @Override
    protected void viewCreated(View view, @Nullable Bundle savedInstanceState) {
        mRecyclerView.setAdapter(mArticleAdapter = new ArticleAdapter(null));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
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

            }
        });
        mArticleAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                mPresenter.search(searchKey, pageNum);
            }
        }, mRecyclerView);

        RxBus.getInstance().toFlowable(SearchEvent.class)
                .subscribe(new Consumer<SearchEvent>() {
                    @Override
                    public void accept(SearchEvent searchEvent) throws Exception {
                        if (searchEvent.isBeginSearch()) {
                            pageNum = 0;
                            mPresenter.search(searchKey = searchEvent.key, 0);
                            mArticleAdapter.setEnableLoadMore(true);
                        }
                    }
                });
    }

    @Override
    protected void lazyInit() {
//        if (!TextUtils.isEmpty(searchKey)) {
//            mPresenter.search(searchKey, 0);
//        }
    }
}
