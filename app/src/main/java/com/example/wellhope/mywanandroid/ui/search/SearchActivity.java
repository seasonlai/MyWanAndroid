package com.example.wellhope.mywanandroid.ui.search;

import android.annotation.SuppressLint;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.ToastUtils;
import com.example.wellhope.mywanandroid.R;
import com.example.wellhope.mywanandroid.base.BaseActivity;
import com.example.wellhope.mywanandroid.base.SupportFragment;
import com.example.wellhope.mywanandroid.bean.RecommendBean.HistoryBean;
import com.example.wellhope.mywanandroid.event.HistoryEvent;
import com.example.wellhope.mywanandroid.event.RxBus;
import com.example.wellhope.mywanandroid.event.SearchEvent;
import com.example.wellhope.mywanandroid.ui.search.recommend.SearchRecommendFragment;
import com.example.wellhope.mywanandroid.ui.search.result.SearchResultFragment;

import java.util.Date;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.functions.Consumer;

public class SearchActivity extends BaseActivity {

    @BindView(R.id.search_toolbar)
    Toolbar mToolbar;

    private SearchView searchView;
    private HistoryAdapter mHistoryTipAdapter;

    @BindView(R.id.search_container)
    FrameLayout mContainer;

    @Override
    protected int getContentLayout() {
        return R.layout.activity_search;
    }


    @Override
    protected void bindView(@Nullable Bundle savedInstanceState) {
//        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);
        subscribeEvent();
        mHistoryTipAdapter = new HistoryAdapter(this, R.layout.item_history_tip);

        SearchRecommendFragment searchRecommendFragment;
        if (savedInstanceState == null) {
            searchRecommendFragment = SearchRecommendFragment.newInstance();
        } else {
            searchRecommendFragment = findFragment(SearchRecommendFragment.class);
        }
        getSupportDelegate().loadRootFragment(R.id.search_container, searchRecommendFragment);
    }

    private void subscribeEvent() {
        //更改搜索框文字并开始搜索
        mPresenter.addDisposable(
                RxBus.getInstance().toFlowable(SearchEvent.class)
                        .subscribe(new Consumer<SearchEvent>() {
                            @Override
                            public void accept(SearchEvent searchEvent) throws Exception {
                                if (!searchEvent.isBeginSearch())
                                    setSearchTxtAndSearch(searchEvent.key);
                            }
                        }));

        //更新搜索框历史记录提示
        mPresenter.addDisposable(
                RxBus.getInstance().toFlowable(HistoryEvent.SingleEvent.class)
                        .subscribe(new Consumer<HistoryEvent.SingleEvent>() {
                            @Override
                            public void accept(HistoryEvent.SingleEvent singleEvent) throws Exception {
                                switch (singleEvent.getType()) {
                                    case HistoryEvent.TYPE_DEL_SUCCESS:
                                        mHistoryTipAdapter.remove(singleEvent.getBean());
                                        break;
                                    case HistoryEvent.TYPE_SAVE_SUCCESS:
                                        mHistoryTipAdapter.add(singleEvent.getBean());
                                        break;
                                }
                            }
                        }));

        mPresenter.addDisposable(
                RxBus.getInstance().toFlowable(HistoryEvent.ListEvent.class)
                        .subscribe(new Consumer<HistoryEvent.ListEvent>() {
                            @Override
                            public void accept(HistoryEvent.ListEvent listEvent) throws Exception {
                                switch (listEvent.getType()) {
                                    case HistoryEvent.TYPE_UPDATE:
                                        mHistoryTipAdapter.changeData(listEvent.getList());
                                        break;

                                    case HistoryEvent.TYPE_DEL:
                                        mHistoryTipAdapter.clear();
                                        break;
                                }
                            }
                        }));
    }


    private void setSearchTxtAndSearch(String txt) {
        EditText searchSrcView = searchView.findViewById(R.id.search_src_text);
        searchSrcView.setText(txt);
        searchSrcView.setSelection(txt.length());
        searchSrcView.clearFocus();
        searchView.clearFocus();
        mContainer.requestFocus();
        toSearchResult(txt);
        //保存历史记录
        RxBus.getInstance().post(new HistoryEvent.SingleEvent(
                new HistoryBean(txt, new Date().getTime()), HistoryEvent.TYPE_SAVE));
    }


    @Override
    public void onBackPressedSupport() {
        super.onBackPressedSupport();
        searchView.clearFocus();
        mContainer.requestFocus();
    }

    private void toSearchResult(String txt) {
        if (findFragment(SearchResultFragment.class) != null) {
            ToastUtils.showShort("有了");
            RxBus.getInstance().post(new SearchEvent(txt, true));
        } else {
            getSupportDelegate().start(SearchResultFragment.newInstance(txt));
        }
    }

    @SuppressLint("RestrictedApi")
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search, menu);
        MenuItem item = menu.findItem(R.id.toolbar_search);
        searchView = (SearchView) item.getActionView();
        searchView.setQueryHint("搜索关键词以空格隔开");
        searchView.setBackgroundColor(ContextCompat.getColor(this, R.color.white));
        //展开
        searchView.setIconified(false);
        searchView.clearFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
//                searchView.clearFocus();
//                //保存历史记录
//                RxBus.getInstance().post(new HistoryEvent.SingleEvent(
//                        new HistoryBean(query, new Date().getTime()), HistoryEvent.TYPE_SAVE));
//                toSearchResult(query);
                setSearchTxtAndSearch(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        SearchView.SearchAutoComplete searchSrcView = searchView.findViewById(R.id.search_src_text);
        searchSrcView.setTextSize(15);
        searchSrcView.setThreshold(0);
        searchSrcView.setGravity(Gravity.CENTER_VERTICAL);
        searchSrcView.setAdapter(mHistoryTipAdapter);

        mHistoryTipAdapter.setOnClickListener(new HistoryAdapter.OnClickListener() {
            @Override
            public void textClick(HistoryBean bean) {
                setSearchTxtAndSearch(bean.getHistoryContent());
            }

            @Override
            public void delClick(HistoryBean bean) {
                RxBus.getInstance().post(new HistoryEvent.SingleEvent(bean, HistoryEvent.TYPE_DEL));
            }
        });

        return true;
    }

    @OnClick(R.id.iv_back)
    public void backward() {
        onBackPressed();
    }

}
