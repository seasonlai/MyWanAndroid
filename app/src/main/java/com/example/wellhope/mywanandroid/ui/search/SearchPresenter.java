package com.example.wellhope.mywanandroid.ui.search;

import android.util.Log;

import com.example.wellhope.mywanandroid.base.BaseObserver;
import com.example.wellhope.mywanandroid.base.BasePresenter;
import com.example.wellhope.mywanandroid.bean.HistoryBean;
import com.example.wellhope.mywanandroid.bean.HotWordBean;
import com.example.wellhope.mywanandroid.bean.MsgBean;
import com.example.wellhope.mywanandroid.net.RxSchedulers;
import com.example.wellhope.mywanandroid.net.WanAndroidApi;

import org.litepal.crud.DataSupport;
import org.litepal.crud.callback.UpdateOrDeleteCallback;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

/**
 * Created by Wellhope on 2018/3/6.
 */

public class SearchPresenter extends BasePresenter<SearchContract.View> implements SearchContract.Presenter {

    public static final String TAG = SearchPresenter.class.getSimpleName();
    WanAndroidApi mApi;

    @Inject
    public SearchPresenter(WanAndroidApi api, SearchContract.View view) {
        this.mApi = api;
        this.mView = view;
    }

    @Override
    public void getHistory() {
        List<HistoryBean> historyBeans = DataSupport.order("historyDate desc").find(HistoryBean.class);
        mView.loadHistory(historyBeans);
    }

    @Override
    public void getHotWord() {
        mApi.getHotWord()
                .compose(RxSchedulers.<MsgBean<List<HotWordBean>>>switchSchedulers())
                .map(new Function<MsgBean<List<HotWordBean>>, List<HotWordBean>>() {
                    @Override
                    public List<HotWordBean> apply(MsgBean<List<HotWordBean>> listMsgBean) throws Exception {
                        return listMsgBean.getData();
                    }
                })
                .subscribe(new BaseObserver<List<HotWordBean>>() {
                    @Override
                    protected void success(List<HotWordBean> hotWordBeans) {
                        mView.loadHotWord(hotWordBeans);
                    }

                    @Override
                    protected void fail(Throwable e) {

                    }
                });
    }

    @Override
    public void saveHistory(final HistoryBean history) {
        if (history == null)
            return;
//        List<HistoryBean> historyBeans = DataSupport.where("historyContent = ?& historyDate < ?",
//                history.getHistoryContent(), Long.toString(history.getHistoryDate()))
//                .find(HistoryBean.class);
//        if (historyBeans != null && historyBeans.size() > 0) {
//            mView.deletePreHistory(historyBeans);
        DataSupport.deleteAllAsync(HistoryBean.class, "historyContent = ?",
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
                            mView.saveHistory(history, success);
                        }
                    }
                });
//        }
    }

    @Override
    public void deleteHistory(final HistoryBean history) {
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
    public void search(String content) {
        Log.e(TAG, "search: content" + content);
    }
}
