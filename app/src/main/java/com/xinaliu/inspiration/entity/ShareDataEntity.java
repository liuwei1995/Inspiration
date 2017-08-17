package com.xinaliu.inspiration.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * 分享 数据
 * Created by liuwei on 2017/7/31 10:09
 */

public class ShareDataEntity implements Serializable, Parcelable {

    private String title;
    private String generalContent;
    private String shareUrl;
    private String generalImg;

    public ShareDataEntity() {
    }

    public ShareDataEntity(String title, String generalContent, String shareUrl, String generalImg) {
        this.title = title;
        this.generalImg = generalImg;
        this.generalContent = generalContent;
        this.shareUrl = shareUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGeneralImg() {
        return generalImg;
    }

    public void setGeneralImg(String generalImg) {
        this.generalImg = generalImg;
    }

    public String getGeneralContent() {
        return generalContent;
    }

    public void setGeneralContent(String generalContent) {
        this.generalContent = generalContent;
    }

    public String getShareUrl() {
        return shareUrl;
    }

    public void setShareUrl(String shareUrl) {
        this.shareUrl = shareUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeString(this.generalImg);
        dest.writeString(this.generalContent);
        dest.writeString(this.shareUrl);
    }

    protected ShareDataEntity(Parcel in) {
        this.title = in.readString();
        this.generalImg = in.readString();
        this.generalContent = in.readString();
        this.shareUrl = in.readString();
    }

    public static final Creator<ShareDataEntity> CREATOR = new Creator<ShareDataEntity>() {
        @Override
        public ShareDataEntity createFromParcel(Parcel source) {
            return new ShareDataEntity(source);
        }

        @Override
        public ShareDataEntity[] newArray(int size) {
            return new ShareDataEntity[size];
        }
    };

    @Override
    public String toString() {
        return "ShareDataEntity{" +
                "title='" + title + '\'' +
                ", generalImg='" + generalImg + '\'' +
                ", generalContent='" + generalContent + '\'' +
                ", shareUrl='" + shareUrl + '\'' +
                '}';
    }
}
