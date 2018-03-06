package com.example.wellhope.mywanandroid.bean;

import org.litepal.annotation.Column;
import org.litepal.crud.DataSupport;

/**
 * Created by Wellhope on 2018/3/6.
 */

public class HistoryBean extends DataSupport {

    private String historyContent;
    private long historyDate;

    public HistoryBean(String historyContent, long historyDate) {
        this.historyContent = historyContent;
        this.historyDate = historyDate;
    }

    public String getHistoryContent() {
        return historyContent;
    }

    public void setHistoryContent(String historyContent) {
        this.historyContent = historyContent;
    }

    public long getHistoryDate() {
        return historyDate;
    }

    public void setHistoryDate(long historyDate) {
        this.historyDate = historyDate;
    }
}
