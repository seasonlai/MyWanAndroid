package com.example.wellhope.mywanandroid.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import org.litepal.crud.DataSupport;

/**
 * Created by Wellhope on 2018/3/9.
 */

public class RecommendBean{

    public static final int TYPE_TITLE = 0xf;
    public static final int TYPE_TIPS = 0xf0;
    public static final int TYPE_HISTORY = 0xff;
    public static final int TYPE_HOTWORD = 0xfff;
    public static final int TYPE_STARWEB = 0xffff;

    public static class Title implements MultiItemEntity {
        String title;
        boolean canClear;

        public Title(String title) {
            this(title, false);
        }

        public Title(String title, boolean canClear) {
            this.title = title;
            this.canClear = canClear;
        }

        @Override
        public int getItemType() {
            return TYPE_TITLE;
        }

        public String getTitle() {
            return title;
        }

        public boolean isCanClear() {
            return canClear;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Title title1 = (Title) o;

            if (canClear != title1.canClear) return false;
            return title != null ? title.equals(title1.title) : title1.title == null;
        }

        @Override
        public int hashCode() {
            int result = title != null ? title.hashCode() : 0;
            result = 31 * result + (canClear ? 1 : 0);
            return result;
        }
    }

    public static class Tips implements MultiItemEntity{

        String tips;

        public Tips(String tips) {
            this.tips = tips;
        }

        public String getTips() {
            return tips;
        }

        @Override
        public int getItemType() {
            return TYPE_TIPS;
        }
    }

    public static class HistoryBean extends DataSupport implements MultiItemEntity {

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

        @Override
        public int getItemType() {
            return TYPE_HISTORY;
        }
    }


    public static class HotWordBean implements MultiItemEntity {
        /**
         * id : 6
         * link :
         * name : 面试
         * order : 1
         * visible : 1
         */

        private int id;
        private String link;
        private String name;
        private int order;
        private int visible;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getOrder() {
            return order;
        }

        public void setOrder(int order) {
            this.order = order;
        }

        public int getVisible() {
            return visible;
        }

        public void setVisible(int visible) {
            this.visible = visible;
        }

        @Override
        public int getItemType() {
            return TYPE_HOTWORD;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            HotWordBean that = (HotWordBean) o;

            if (id != that.id) return false;
            if (order != that.order) return false;
            if (visible != that.visible) return false;
            if (link != null ? !link.equals(that.link) : that.link != null) return false;
            return name != null ? name.equals(that.name) : that.name == null;
        }

        @Override
        public int hashCode() {
            int result = id;
            result = 31 * result + (link != null ? link.hashCode() : 0);
            result = 31 * result + (name != null ? name.hashCode() : 0);
            result = 31 * result + order;
            result = 31 * result + visible;
            return result;
        }
    }


    public static class StarWebBean implements MultiItemEntity {
        /**
         * icon :
         * id : 17
         * link : http://www.wanandroid.com/article/list/0?cid=176
         * name : 国内大牛博客集合
         * order : 1
         * visible : 1
         */

        private String icon;
        private int id;
        private String link;
        private String name;
        private int order;
        private int visible;

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getOrder() {
            return order;
        }

        public void setOrder(int order) {
            this.order = order;
        }

        public int getVisible() {
            return visible;
        }

        public void setVisible(int visible) {
            this.visible = visible;
        }

        @Override
        public int getItemType() {
            return TYPE_STARWEB;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            StarWebBean that = (StarWebBean) o;

            if (id != that.id) return false;
            if (order != that.order) return false;
            if (visible != that.visible) return false;
            if (icon != null ? !icon.equals(that.icon) : that.icon != null) return false;
            if (link != null ? !link.equals(that.link) : that.link != null) return false;
            return name != null ? name.equals(that.name) : that.name == null;
        }

        @Override
        public int hashCode() {
            int result = icon != null ? icon.hashCode() : 0;
            result = 31 * result + id;
            result = 31 * result + (link != null ? link.hashCode() : 0);
            result = 31 * result + (name != null ? name.hashCode() : 0);
            result = 31 * result + order;
            result = 31 * result + visible;
            return result;
        }
    }

}
