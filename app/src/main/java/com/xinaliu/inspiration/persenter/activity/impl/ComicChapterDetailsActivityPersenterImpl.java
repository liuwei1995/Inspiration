package com.xinaliu.inspiration.persenter.activity.impl;

import com.xinaliu.inspiration.IView.ComicChapterDetailsActivityView;
import com.xinaliu.inspiration.entity.ComicChapterDetailsEntity;
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
    public void getComicInfo(String comic_id) {
        Map<String, String> map = new HashMap<>();
        map.put("comic_id",comic_id);
        mComicChapterDetailsActivityModel.getComicInfo(map, new ModelHttpCallback() {
            @Override
            public void startRequest() {
                mComicChapterDetailsActivityView.startRequest();
            }

            @Override
            public void endRequest() {
                mComicChapterDetailsActivityView.endRequest();
            }

            @Override
            public void connectionFailed(String failedMessge) {
                mComicChapterDetailsActivityView.connectionFailed(failedMessge);
            }

            @Override
            public void resultError(int code, String errorMessge) {
                mComicChapterDetailsActivityView.resultError(code,errorMessge);
            }
        });
    }

    @Override
    public void setComicChapterDetailsEntity(ComicChapterDetailsEntity mComicChapterDetailsEntity) {
        mComicChapterDetailsActivityView.setComicChapterDetailsEntity(mComicChapterDetailsEntity);
    }
}
