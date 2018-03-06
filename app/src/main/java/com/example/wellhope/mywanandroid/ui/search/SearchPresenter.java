package com.example.wellhope.mywanandroid.ui.search;

import com.example.wellhope.mywanandroid.base.BasePresenter;
import com.example.wellhope.mywanandroid.bean.HistoryBean;
import com.example.wellhope.mywanandroid.bean.HotWordBean;
import com.example.wellhope.mywanandroid.bean.MsgBean;
import com.example.wellhope.mywanandroid.net.RxSchedulers;
import com.example.wellhope.mywanandroid.net.WanAndroidApi;

import org.litepal.crud.DataSupport;
import org.litepal.crud.callback.SaveCallback;
import org.litepal.crud.callback.UpdateOrDeleteCallback;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

/**
 * Created by Wellhope on 2018/3/6.
 */

public class SearchPresenter extends BasePresenter<SearchContract.View> implements SearchContract.Presenter {

    WanAndroidApi mApi;

    @Inject
    public SearchPresenter(WanAndroidApi api, SearchContract.View view) {
        this.mApi = api;
        this.mView = view;
    }

    @Override
    public void getHistory() {
        List<HistoryBean> historyBeans = DataSupport.findAll(HistoryBean.class);
        mView.loadHistory(historyBeans);
    }

    @Override
    public void getHotWord() {
        mApi.getHotWord()
                .compose(RxSchedulers.<MsgBean<List<HotWordBean>>>applySchedulers())
                .map(new Function<MsgBean<List<HotWordBean>>, List<HotWordBean>>() {
                    @Override
                    public List<HotWordBean> apply(MsgBean<List<HotWordBean>> listMsgBean) throws Exception {
                        return listMsgBean.getData();
                    }
                })
                .subscribe(new Consumer<List<HotWordBean>>() {
                    @Override
                    public void accept(List<HotWordBean> hotWordBeans) throws Exception {
                        mView.loadHotWord(hotWordBeans);
                    }
                });
    }

    @Override
    public void saveHistory(final HistoryBean history) {
        if (history == null)
            return;

        history.saveAsync().listen(new SaveCallback() {
            @Override
            public void onFinish(boolean success) {
                mView.saveHistory(history, success);
            }
        });
        List<HistoryBean> historyBeans =
                DataSupport.where("historyContent = ?", history.getHistoryContent())
                        .find(HistoryBean.class);
        DataSupport.markAsDeleted(historyBeans);

    }

    @Override
    public void deleteHistory(HistoryBean history) {
        if (history == null)
            return;
        history.deleteAsync().listen(new UpdateOrDeleteCallback() {
            @Override
            public void onFinish(int rowsAffected) {

            }
        });
    }
}
