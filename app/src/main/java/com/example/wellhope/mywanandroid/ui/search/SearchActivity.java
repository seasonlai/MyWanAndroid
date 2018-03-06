package com.example.wellhope.mywanandroid.ui.search;

import android.annotation.SuppressLint;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.wellhope.mywanandroid.R;
import com.example.wellhope.mywanandroid.base.BaseActivity;
import com.example.wellhope.mywanandroid.bean.HistoryBean;
import com.example.wellhope.mywanandroid.bean.HotWordBean;
import com.example.wellhope.mywanandroid.widget.FlowLayoutManager;

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
        mRvHotWrod.setLayoutManager(new FlowLayoutManager());//设置瀑布流管理器
//        mRvHotWrod.addItemDecoration(new GridSpacingItemDecoration(40));//边距和分割线，需要自己定义
        mRvHotWrod.setAdapter(mHotWordAdapter = new BaseQuickAdapter<HotWordBean, BaseViewHolder>(R.layout.item_hotword) {
            @Override
            protected void convert(BaseViewHolder helper, HotWordBean item) {
                helper.setText(R.id.tv_hotword, item.getName());
            }
        });

        mRvHistory.setLayoutManager(new FlowLayoutManager());//设置瀑布流管理器
        mRvHistory.setAdapter(mHistoryAdapter = new BaseQuickAdapter<HistoryBean, BaseViewHolder>(R.layout.item_hotword) {
            @Override
            protected void convert(BaseViewHolder helper, HistoryBean item) {
                helper.setText(R.id.tv_hotword, item.getHistoryContent());
            }
        });

    }

    @Override
    protected void loadData() {
        this.mPesenter.getHistory();
        this.mPesenter.getHotWord();
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
                SearchActivity.this.mPesenter.saveHistory(
                        new HistoryBean(query, new Date().getTime()));
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

//        SearchView.SearchAutoComplete searchSrcView = searchView.findViewById(R.id.search_src_text);
//        searchSrcView.setThreshold(0);
//        searchSrcView.setAdapter(mHistoryTipAdapter = new HistoryAdapter(this,
//                R.layout.item_history_tip, null));
        return true;
    }

    @OnClick(R.id.iv_back)
    public void backward() {
        finish();
    }

    @Override
    public void loadHistory(List<HistoryBean> histories) {
//        for (HistoryBean history:histories){
//            View inflate = LayoutInflater.from(this).inflate(R.layout.item_hotword, null);
//            TextView tv = inflate.findViewById(R.id.tv_hotword);
//            tv.setText(history.getHistoryContent());
//            tv.setOnLongClickListener(new View.OnLongClickListener() {
//                @Override
//                public boolean onLongClick(View view) {
//                    Toast.makeText(SearchActivity.this,"删除"
//                            ,Toast.LENGTH_SHORT).show();
//                    return true;
//                }
//            });
//            mRvHistory.addView(inflate);
//        }
        Toast.makeText(this,"histories size:"+histories.size(),Toast.LENGTH_SHORT).show();
        mHistoryAdapter.addData(histories);
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
//            mHistoryTipAdapter.add(historyBean);
        }
    }

}
