package com.xinaliu.inspiration.model.impl;

import com.xinaliu.inspiration.entity.ComicChapterDetailsEntity;
import com.xinaliu.inspiration.http.HttpUtils;
import com.xinaliu.inspiration.http.util.HttpCallback;
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
    public void getComicInfo(Map<String, String> map, final ModelHttpCallback modelHttpCallback) {
        modelHttpCallback.startRequest();
        HttpUtils.getComicInfo(map, this, new HttpCallback<ComicChapterDetailsEntity>() {
            @Override
            public void onChildCallbackResult(Boolean isSuccess, ComicChapterDetailsEntity result) {
                modelHttpCallback.endRequest();
                if (isSuccess){
                    if (result != null){
                        mComicChapterDetailsActivityPersenter.setComicChapterDetailsEntity(result);
                    }else {
                        modelHttpCallback.resultError(-1,"数据出问题啦");
                    }
                }else {
                    modelHttpCallback.connectionFailed("网络开小差啦");
                }
            }
        });
    }
}
