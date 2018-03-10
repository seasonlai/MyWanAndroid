package com.example.wellhope.mywanandroid.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by season on 2018/3/3.
 */

public class BannerBean implements Parcelable{

    /**
     * desc : 一起来做个App吧
     * id : 10
     * imagePath : http://www.wanandroid.com/blogimgs/50c115c2-cf6c-4802-aa7b-a4334de444cd.png
     * isVisible : 1
     * order : 0
     * title : 一起来做个App吧
     * type : 0
     * url : http://www.wanandroid.com/blog/show/2
     */

    private String desc;
    private int id;
    private String imagePath;
    private int isVisible;
    private int order;
    private String title;
    private int type;
    private String url;

    protected BannerBean(Parcel in) {
        desc = in.readString();
        id = in.readInt();
        imagePath = in.readString();
        isVisible = in.readInt();
        order = in.readInt();
        title = in.readString();
        type = in.readInt();
        url = in.readString();
    }

    public static final Creator<BannerBean> CREATOR = new Creator<BannerBean>() {
        @Override
        public BannerBean createFromParcel(Parcel in) {
            return new BannerBean(in);
        }

        @Override
        public BannerBean[] newArray(int size) {
            return new BannerBean[size];
        }
    };

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public int getIsVisible() {
        return isVisible;
    }

    public void setIsVisible(int isVisible) {
        this.isVisible = isVisible;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(desc);
        parcel.writeInt(id);
        parcel.writeString(imagePath);
        parcel.writeInt(isVisible);
        parcel.writeInt(order);
        parcel.writeString(title);
        parcel.writeInt(type);
        parcel.writeString(url);
    }
}
