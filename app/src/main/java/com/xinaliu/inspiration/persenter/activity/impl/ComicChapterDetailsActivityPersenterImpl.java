package com.xinaliu.inspiration.persenter.activity.impl;

import com.xinaliu.inspiration.IView.ComicChapterDetailsActivityView;
import com.xinaliu.inspiration.model.ComicChapterDetailsActivityModel;
import com.xinaliu.inspiration.model.ModelHttpCallback;
import com.xinaliu.inspiration.model.impl.ComicChapterDetailsActivityModelImpl;
import com.xinaliu.inspiration.persenter.activity.ComicChapterDetailsActivityPersenter;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by liuwei on 2017/8/28 18:00
 */

public class ComicChapterDetailsActivityPersenterImpl implements ComicChapterDetailsActivityPersenter {

    private ComicChapterDetailsActivityView mComicChapterDetailsActivityView;
    private ComicChapterDetailsActivityModel mComicChapterDetailsActivityModel;
    public ComicChapterDetailsActivityPersenterImpl(ComicChapterDetailsActivityView mComicChapterDetailsActivityView) {
        this.mComicChapterDetailsActivityView = mComicChapterDetailsActivityView;
        mComicChapterDetailsActivityModel = new ComicChapterDetailsActivityModelImpl(this);

    }

    @Override
    public void getComicInfo() {
        Map<String, String> map = new HashMap<>();
        mComicChapterDetailsActivityModel.getComicInfo(map, new ModelHttpCallback() {
            @Override
            public void startRequest() {

            }

            @Override
            public void endRequest() {

            }

            @Override
            public void connectionFailed(String failedMessge) {

            }

            @Override
            public void resultError(int code, String errorMessge) {

            }
        });
    }
}
