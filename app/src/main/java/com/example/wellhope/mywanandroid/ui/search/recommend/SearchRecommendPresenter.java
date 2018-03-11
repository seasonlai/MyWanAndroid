package com.example.wellhope.mywanandroid.ui.search.recommend;

import android.util.Log;

import com.blankj.utilcode.util.ToastUtils;
import com.example.wellhope.mywanandroid.base.BaseObserver;
import com.example.wellhope.mywanandroid.base.BasePresenter;
import com.example.wellhope.mywanandroid.bean.MsgBean;
import com.example.wellhope.mywanandroid.bean.RecommendBean;
import com.example.wellhope.mywanandroid.net.RxSchedulers;
import com.example.wellhope.mywanandroid.net.WanAndroidApi;

import org.litepal.crud.DataSupport;
import org.litepal.crud.callback.UpdateOrDeleteCallback;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by Wellhope on 2018/3/9.
 */

public class SearchRecommendPresenter extends BasePresenter<SearchRecommendContract.View> implements SearchRecommendContract.Presenter {
    public static final String TAG = SearchRecommendPresenter.class.getSimpleName();
    WanAndroidApi mApi;

    @Inject
    public SearchRecommendPresenter(WanAndroidApi api, SearchRecommendContract.View view) {
        super(null);
        this.mApi = api;
        this.mView = view;
    }

    @Override
    public void getHistory() {
        List<RecommendBean.HistoryBean> historyBeans = DataSupport.
                order("historyDate desc").find(RecommendBean.HistoryBean.class);
        mView.loadHistory(historyBeans);
    }

    @Override
    public void getHotWord() {
        mApi.getHotWord()
                .compose(RxSchedulers.<MsgBean<List<RecommendBean.HotWordBean>>>switchSchedulers())
                .map(RxSchedulers.<List<RecommendBean.HotWordBean>>mapResult())
                .subscribe(new BaseObserver<List<RecommendBean.HotWordBean>>() {
                    @Override
                    protected void success(List<RecommendBean.HotWordBean> hotWordBeans) {
                        mView.loadHotWord(hotWordBeans);
                    }

                    @Override
                    protected void fail(Throwable e) {

                    }
                });
    }

    @Override
    public void getStarWeb() {
        mApi.getStarWeb()
                .compose(RxSchedulers.<MsgBean<List<RecommendBean.StarWebBean>>>switchSchedulers())
                .map(RxSchedulers.<List<RecommendBean.StarWebBean>>mapResult())
                .subscribe(new BaseObserver<List<RecommendBean.StarWebBean>>() {
                    @Override
                    protected void success(List<RecommendBean.StarWebBean> starWebBeans) {
                        mView.loadStartWeb(starWebBeans);
                    }

                    @Override
                    protected void fail(Throwable e) {

                    }
                });
    }

    @Override
    public void saveHistory(final RecommendBean.HistoryBean history) {
        if (history == null)
            return;
        DataSupport.deleteAllAsync(RecommendBean.HistoryBean.class, "historyContent = ?",
                history.getHistoryContent())
                .listen(new UpdateOrDeleteCallback() {
                    @Override
                    public void onFinish(int rowsAffected) {
                        if (rowsAffected > 0) {
                            Log.e(TAG, "onFinish: rowsAffected " + rowsAffected);
                            history.save();
                            getHistory();
                        } else {
                            boolean success = history.save();
                            if (success)
                                mView.saveHistory(history);
                        }
                    }
                });
//        }
    }

    @Override
    public void deleteHistory(final RecommendBean.HistoryBean history) {
        if (history == null)
            return;
        history.deleteAsync().listen(new UpdateOrDeleteCallback() {
            @Override
            public void onFinish(int rowsAffected) {
                if (rowsAffected > 0)
                    mView.deleteHistory(history);
            }
        });
    }

    @Override
    public void clearHistory() {
        DataSupport.deleteAllAsync(RecommendBean.HistoryBean.class)
                .listen(new UpdateOrDeleteCallback() {
                    @Override
                    public void onFinish(int rowsAffected) {
                        if (rowsAffected > 0) {
                            mView.clearHistory();
                        }
                        ToastUtils.showShort("历史记录删除成功");
                    }
                });
    }

}
