package com.example.wellhope.mywanandroid.ui.search;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.beloo.widget.chipslayoutmanager.ChipsLayoutManager;
import com.beloo.widget.chipslayoutmanager.gravity.IChildGravityResolver;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.wellhope.mywanandroid.R;
import com.example.wellhope.mywanandroid.base.BaseActivity;
import com.example.wellhope.mywanandroid.bean.HistoryBean;
import com.example.wellhope.mywanandroid.bean.HotWordBean;

import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class SearchActivity extends BaseActivity<SearchPresenter> implements SearchContract.View {

    @BindView(R.id.search_toolbar)
    Toolbar mToolbar;

    @BindView(R.id.rv_hotword)
    RecyclerView mRvHotWrod;

    @BindView(R.id.rv_history)
    RecyclerView mRvHistory;

    BaseQuickAdapter mHotWordAdapter;

    BaseQuickAdapter mHistoryAdapter;

    HistoryAdapter mHistoryTipAdapter;

    private SearchView searchView;

    @Override
    protected int getContentLayout() {
        return R.layout.activity_search;
    }

    @Override
    protected void bindView(@Nullable Bundle savedInstanceState) {
//        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setSupportActionBar(mToolbar);

        //定义瀑布流管理器，第一个参数是列数，第二个是方向。
//        final StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(
//                1,StaggeredGridLayoutManager.HORIZONTAL);
        //不设置的话，图片闪烁错位，有可能有整列错位的情况。
//        layoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
        mRvHotWrod.setLayoutManager(getLayoutManager());//设置瀑布流管理器
//        mRvHotWrod.addItemDecoration(new GridSpacingItemDecoration(40));//边距和分割线，需要自己定义
        mRvHotWrod.setAdapter(mHotWordAdapter = new BaseQuickAdapter<HotWordBean, BaseViewHolder>(R.layout.item_hotword) {
            @Override
            protected void convert(BaseViewHolder helper, HotWordBean item) {
                helper.setText(R.id.tv_hotword, item.getName());
            }
        });

        mRvHistory.setLayoutManager(getLayoutManager());//设置瀑布流管理器
        mRvHistory.setAdapter(mHistoryAdapter = new BaseQuickAdapter<HistoryBean, BaseViewHolder>(R.layout.item_hotword) {
            @Override
            protected void convert(BaseViewHolder helper, HistoryBean item) {
                helper.setText(R.id.tv_hotword, item.getHistoryContent());
            }
        });
        mHistoryAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                HistoryBean item = (HistoryBean) adapter.getItem(position);
                mPresenter.search(item.getHistoryContent());
            }
        });

        mHistoryAdapter.setOnItemLongClickListener(new BaseQuickAdapter.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(final BaseQuickAdapter adapter, View view, final int position) {

                new AlertDialog.Builder(SearchActivity.this)
                        .setTitle("确定删除改历史记录？")
                        .setPositiveButton("是", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mPresenter.deleteHistory((HistoryBean)adapter.getItem(position));
                            }
                        })
                        .setNegativeButton("否",null)
                        .show();
                return true;
            }
        });
    }

    private ChipsLayoutManager getLayoutManager() {
        return ChipsLayoutManager.newBuilder(this)
                //set vertical gravity for all items in a row. Default = Gravity.CENTER_VERTICAL
                .setChildGravity(Gravity.TOP)
                //whether RecyclerView can scroll. TRUE by default
                .setScrollingEnabled(true)
                //set maximum views count in a particular row
//                .setMaxViewsInRow(2)
                //set gravity resolver where you can determine gravity for item in position.
                //This method have priority over previous one
                .setGravityResolver(new IChildGravityResolver() {
                    @Override
                    public int getItemGravity(int position) {
                        return Gravity.CENTER;
                    }
                })
                //you are able to break row due to your conditions. Row breaker should return true for that views
//                .setRowBreaker(new IRowBreaker() {
//                    @Override
//                    public boolean isItemBreakRow(@IntRange(from = 0) int position) {
//                        return position == 6 || position == 11 || position == 2;
//                    }
//                })
                //a layoutOrientation of layout manager, could be VERTICAL OR HORIZONTAL. HORIZONTAL by default
                .setOrientation(ChipsLayoutManager.HORIZONTAL)
                // row strategy for views in completed row, could be STRATEGY_DEFAULT, STRATEGY_FILL_VIEW,
                //STRATEGY_FILL_SPACE or STRATEGY_CENTER
                .setRowStrategy(ChipsLayoutManager.STRATEGY_DEFAULT)
                // whether strategy is applied to last row. FALSE by default
                .withLastRow(true)
                .build();
    }

    @Override
    protected void loadData() {
        this.mPresenter.getHistory();
        this.mPresenter.getHotWord();
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
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //保存历史记录
                SearchActivity.this.mPresenter.saveHistory(
                        new HistoryBean(query, new Date().getTime()));
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        searchView.setOnSuggestionListener(new SearchView.OnSuggestionListener() {
            @Override
            public boolean onSuggestionSelect(int position) {

                Toast.makeText(SearchActivity.this, "onSuggestionSelect: " + position, Toast.LENGTH_SHORT).show();
                return false;
            }

            @Override
            public boolean onSuggestionClick(int position) {
                Toast.makeText(SearchActivity.this, "onSuggestionClick: " + position, Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        SearchView.SearchAutoComplete searchSrcView = searchView.findViewById(R.id.search_src_text);
        searchSrcView.setTextSize(15);
        searchSrcView.setThreshold(0);
        searchSrcView.setGravity(Gravity.CENTER_VERTICAL);
//        searchSrcView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//            }
//        });
        searchSrcView.setAdapter(mHistoryTipAdapter == null ? mHistoryTipAdapter = new HistoryAdapter(this,
                R.layout.item_history_tip) : mHistoryTipAdapter);

        mHistoryTipAdapter.setOnClickListener(new HistoryAdapter.OnClickListener() {
            @Override
            public void textClick(HistoryBean bean) {
                mPresenter.search(bean.getHistoryContent());
            }

            @Override
            public void delClick(HistoryBean bean) {
                mPresenter.deleteHistory(bean);
            }
        });

        return true;
    }

    @OnClick(R.id.iv_back)
    public void backward() {
        finish();
    }

    @Override
    public void loadHistory(List<HistoryBean> histories) {
        mHistoryAdapter.getData().clear();
        mHistoryAdapter.addData(histories);
        if (mHistoryTipAdapter == null) {
            mHistoryTipAdapter = new HistoryAdapter(this, R.layout.item_history_tip);
        }
        mHistoryTipAdapter.changeData(histories);
    }


    @Override
    public void loadHotWord(List<HotWordBean> hotWords) {
        mHotWordAdapter.addData(hotWords);
    }

    @Override
    public void saveHistory(HistoryBean historyBean, boolean success) {
//        mRvHistory.addView();
        if (success) {
            mHistoryAdapter.addData(0, historyBean);
            mHistoryTipAdapter.addHistory(historyBean);
        }
    }

    @Override
    public void deleteHistory(HistoryBean historyBean) {
        for (int i = 0; i < mHistoryAdapter.getItemCount(); i++) {
            if (historyBean.equals(mHistoryAdapter.getItem(i)))
                mHistoryAdapter.remove(i);
        }
        mHistoryTipAdapter.deletHistory(historyBean);
    }

}
