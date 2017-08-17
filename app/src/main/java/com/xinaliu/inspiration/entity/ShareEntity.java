package com.xinaliu.inspiration.entity;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.DrawableRes;

import java.io.Serializable;

/**
 * 分享
 * Created by liuwei on 2017/6/9 14:01
 */

public class ShareEntity implements Parcelable ,Serializable{

    private int type;
    private String name;
    private @DrawableRes int drawableRes;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDrawableRes() {
        return drawableRes;
    }

    public void setDrawableRes(int drawableRes) {
        this.drawableRes = drawableRes;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.type);
        dest.writeString(this.name);
        dest.writeInt(this.drawableRes);
    }

    public ShareEntity(int type, String name, int drawableRes) {
        this.type = type;
        this.name = name;
        this.drawableRes = drawableRes;
    }

    public ShareEntity() {
    }

    protected ShareEntity(Parcel in) {
        this.type = in.readInt();
        this.name = in.readString();
        this.drawableRes = in.readInt();
    }

    public static final Creator<ShareEntity> CREATOR = new Creator<ShareEntity>() {
        @Override
        public ShareEntity createFromParcel(Parcel source) {
            return new ShareEntity(source);
        }

        @Override
        public ShareEntity[] newArray(int size) {
            return new ShareEntity[size];
        }
    };
}
