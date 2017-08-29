package com.xinaliu.inspiration.persenter.activity;

import com.xinaliu.inspiration.entity.ComicChapterDetailsEntity;

/**
 * Created by liuwei on 2017/8/28 18:00
 */

public interface ComicChapterDetailsActivityPersenter {

    void getComicInfo(String comic_id);

    void setComicChapterDetailsEntity(ComicChapterDetailsEntity mComicChapterDetailsEntity);

}
