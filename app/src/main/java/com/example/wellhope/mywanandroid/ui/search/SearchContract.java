package com.example.wellhope.mywanandroid.ui.search;

import com.example.wellhope.mywanandroid.base.BaseView;
import com.example.wellhope.mywanandroid.bean.HistoryBean;
import com.example.wellhope.mywanandroid.bean.HotWordBean;

import java.util.List;

/**
 * Created by Wellhope on 2018/3/6.
 */

public class SearchContract {

    public interface View extends BaseView {
        void loadHistory(List<HistoryBean> histories);

        void saveHistory(HistoryBean historyBean, boolean success);

        void deleteHistory(HistoryBean historyBean);

        void loadHotWord(List<HotWordBean> hotWords);
    }

    public interface Presenter {

        void getHistory();

        void getHotWord();

        void saveHistory(HistoryBean history);

        void deleteHistory(HistoryBean history);

        void search(String content);

    }
}
