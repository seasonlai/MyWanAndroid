package com.example.wellhope.mywanandroid.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Wellhope on 2018/3/5.
 */

public class ParcelableBean implements Parcelable {
    protected ParcelableBean(Parcel in) {
    }

    public static final Creator<ParcelableBean> CREATOR = new Creator<ParcelableBean>() {
        @Override
        public ParcelableBean createFromParcel(Parcel in) {
            return new ParcelableBean(in);
        }

        @Override
        public ParcelableBean[] newArray(int size) {
            return new ParcelableBean[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
    }
}
