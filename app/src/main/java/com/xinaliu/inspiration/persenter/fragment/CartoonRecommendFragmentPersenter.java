package com.xinaliu.inspiration.persenter.fragment;

import com.xinaliu.inspiration.entity.KanDongManEntity;

import java.util.List;

/**
 * Created by liuwei on 2017/8/28 11:43
 */

public interface CartoonRecommendFragmentPersenter {

    void getRecommend();

    void setTabFragmentAdapter(List<KanDongManEntity> listKanDongManEntity);

    void onDestroy();

}
