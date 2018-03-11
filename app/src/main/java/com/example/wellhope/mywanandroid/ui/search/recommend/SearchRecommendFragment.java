package com.example.wellhope.mywanandroid.ui.search.recommend;


import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;

import com.beloo.widget.chipslayoutmanager.ChipsLayoutManager;
import com.beloo.widget.chipslayoutmanager.gravity.IChildGravityResolver;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.example.wellhope.mywanandroid.R;
import com.example.wellhope.mywanandroid.base.BaseFragment;
import com.example.wellhope.mywanandroid.bean.RecommendBean;
import com.example.wellhope.mywanandroid.event.HistoryEvent;
import com.example.wellhope.mywanandroid.event.RxBus;
import com.example.wellhope.mywanandroid.event.SearchEvent;
import com.example.wellhope.mywanandroid.ui.article.ArticleActivity;

import java.util.List;

import butterknife.BindView;
import io.reactivex.functions.Consumer;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchRecommendFragment extends BaseFragment<SearchRecommendPresenter> implements SearchRecommendContract.View {

    @BindView(R.id.recommend_rv)
    RecyclerView mRecyclerView;

    RecommendAdapter mAdapter;

    public static SearchRecommendFragment newInstance() {
        return new SearchRecommendFragment();
    }

    @Override
    protected int getLayoutID() {
        return R.layout.fragment_search_recommend;
    }

    @Override
    protected void viewCreated(View view, @Nullable Bundle savedInstanceState) {
        mRecyclerView.setLayoutManager(getLayoutManager());
        mRecyclerView.setAdapter(mAdapter = new RecommendAdapter(null));
        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                clickHandle((MultiItemEntity) adapter.getItem(position), view);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        mRecyclerView.requestFocus();
    }

    private void subscribeEvent() {
        mPresenter.addDisposable(
                RxBus.getInstance().toFlowable(HistoryEvent.SingleEvent.class)
                        .subscribe(new Consumer<HistoryEvent.SingleEvent>() {
                            @Override
                            public void accept(HistoryEvent.SingleEvent singleEvent) throws Exception {
                                switch (singleEvent.getType()) {
                                    case HistoryEvent.TYPE_SAVE:
                                        mPresenter.saveHistory(singleEvent.getBean());
                                        break;
                                    case HistoryEvent.TYPE_DEL:
                                        mPresenter.deleteHistory(singleEvent.getBean());
                                        break;
                                }
                            }
                        }));
    }

    private void clickHandle(MultiItemEntity item, View view) {
        switch (item.getItemType()) {
            case RecommendBean.TYPE_TITLE:
                sureClearHistory();
                break;
            case RecommendBean.TYPE_HISTORY:
                RxBus.getInstance().post(new SearchEvent(
                        ((RecommendBean.HistoryBean) item).getHistoryContent()));
                break;
            case RecommendBean.TYPE_HOTWORD:
                RxBus.getInstance().post(new SearchEvent(
                        ((RecommendBean.HotWordBean) item).getName()));
                break;
            case RecommendBean.TYPE_STARWEB:
                ArticleActivity.launch(getContext(), (Parcelable) item);
                break;
        }
    }

    private void sureClearHistory() {
        new AlertDialog.Builder(getContext())
                .setTitle("确定清空历史记录？")
                .setNegativeButton("取消", null)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mPresenter.clearHistory();
                    }
                }).show();
    }

    private ChipsLayoutManager getLayoutManager() {
        return ChipsLayoutManager.newBuilder(getContext())
                .setChildGravity(Gravity.TOP)
                .setScrollingEnabled(true)
                .setGravityResolver(new IChildGravityResolver() {
                    @Override
                    public int getItemGravity(int position) {
                        return Gravity.CENTER;
                    }
                })
                .setOrientation(ChipsLayoutManager.HORIZONTAL)
                .setRowStrategy(ChipsLayoutManager.STRATEGY_DEFAULT)
                .withLastRow(true)
                .build();
    }

    @Override
    protected void lazyInit() {
        subscribeEvent();
        mPresenter.getHistory();
        mPresenter.getHotWord();
        mPresenter.getStarWeb();
    }

    int historyCount;
    int hotwordCount;

    @Override
    public void clearHistory() {
        RxBus.getInstance().post(new HistoryEvent.ListEvent(null, HistoryEvent.TYPE_DEL));
        for (int i = 1; i < historyCount; i++) {
            mAdapter.remove(1);
        }
        mAdapter.addData(1, new RecommendBean.Tips("暂无历史记录"));
    }

    @Override
    public void loadHistory(List<RecommendBean.HistoryBean> histories) {
        if (historyCount == 0) {
            mAdapter.addData(0, new RecommendBean.Title("历史记录", true));
        }

        for (int i = 1; i < historyCount; i++) {
            mAdapter.remove(1);
        }
//
//        mAdapter.replaceData();

        if (histories == null || histories.size() == 0) {
            mAdapter.addData(1, new RecommendBean.Tips("暂无历史记录"));
            historyCount = 2;
        } else {
            mAdapter.addData(1, histories);
            historyCount = histories.size() + 1;
        }
        RxBus.getInstance().post(new HistoryEvent.ListEvent(histories, HistoryEvent.TYPE_UPDATE));
    }

    @Override
    public void saveHistory(MultiItemEntity historyBean) {
        if (historyBean == null)
            return;
        if (mAdapter.getItem(1) instanceof RecommendBean.Tips) {
            mAdapter.remove(1);
            historyCount--;
        }
        mAdapter.addData(1, historyBean);
        historyCount++;
        RxBus.getInstance().post(new HistoryEvent.SingleEvent((RecommendBean.HistoryBean) historyBean,
                HistoryEvent.TYPE_SAVE_SUCCESS));
    }

    @Override
    public void deleteHistory(MultiItemEntity historyBean) {
        if (historyBean == null)
            return;
        for (int i = 0; i < mAdapter.getItemCount(); i++) {
            if (historyBean.equals(mAdapter.getItem(i))) {
                mAdapter.remove(i);
                historyCount--;
                break;
            }
        }
        if (historyCount == 1) {
            mAdapter.addData(1, new RecommendBean.Tips("暂无历史记录"));
            historyCount++;
        }
        RxBus.getInstance().post(new HistoryEvent.SingleEvent((RecommendBean.HistoryBean) historyBean,
                HistoryEvent.TYPE_DEL_SUCCESS));
    }

    @Override
    public void loadHotWord(List<RecommendBean.HotWordBean> hotWords) {
        if (hotWords == null || hotWords.size() == 0)
            return;
        mAdapter.addData(historyCount, new RecommendBean.Title("大家都在搜"));
        mAdapter.addData(historyCount + 1, hotWords);
        hotwordCount = hotWords.size() + 1;
    }

    @Override
    public void loadStartWeb(List<RecommendBean.StarWebBean> starWeb) {
        if (starWeb == null || starWeb.size() == 0)
            return;
        mAdapter.addData(historyCount + hotwordCount, new RecommendBean.Title("常用网站"));
        mAdapter.addData(historyCount + hotwordCount + 1, starWeb);
    }
}
