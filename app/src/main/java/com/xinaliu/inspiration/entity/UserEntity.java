package com.xinaliu.inspiration.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * 用户
 * Created by liuwei on 2017/8/25 11:39
 */

public class UserEntity implements Serializable,Parcelable{


    private String phoneNumber;
    private String password;

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.phoneNumber);
        dest.writeString(this.password);
    }

    public UserEntity() {
    }

    public UserEntity(String phoneNumber, String password) {
        this.phoneNumber = phoneNumber;
        this.password = password;
    }

    protected UserEntity(Parcel in) {
        this.phoneNumber = in.readString();
        this.password = in.readString();
    }

    public static final Creator<UserEntity> CREATOR = new Creator<UserEntity>() {
        @Override
        public UserEntity createFromParcel(Parcel source) {
            return new UserEntity(source);
        }

        @Override
        public UserEntity[] newArray(int size) {
            return new UserEntity[size];
        }
    };
}
