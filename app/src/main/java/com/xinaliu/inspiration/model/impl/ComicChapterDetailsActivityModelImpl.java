package com.xinaliu.inspiration.model.impl;

import com.xinaliu.inspiration.model.ComicChapterDetailsActivityModel;
import com.xinaliu.inspiration.model.ModelHttpCallback;
import com.xinaliu.inspiration.persenter.activity.ComicChapterDetailsActivityPersenter;

import java.util.Map;

/**
 * Created by liuwei on 2017/8/28 18:01
 */

public class ComicChapterDetailsActivityModelImpl implements ComicChapterDetailsActivityModel {

    private ComicChapterDetailsActivityPersenter mComicChapterDetailsActivityPersenter;

    public ComicChapterDetailsActivityModelImpl(ComicChapterDetailsActivityPersenter mComicChapterDetailsActivityPersenter) {
        this.mComicChapterDetailsActivityPersenter = mComicChapterDetailsActivityPersenter;

    }

    @Override
    public void getComicInfo(Map<String, String> map, ModelHttpCallback modelHttpCallback) {
//        HttpUtils.getComicInfo();
    }
}
