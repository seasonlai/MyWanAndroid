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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        HistoryBean bean = (HistoryBean) o;

        if (historyDate != bean.historyDate) return false;
        return historyContent.equals(bean.historyContent);
    }

    @Override
    public int hashCode() {
        int result = historyContent.hashCode();
        result = 31 * result + (int) (historyDate ^ (historyDate >>> 32));
        return result;
    }
}
