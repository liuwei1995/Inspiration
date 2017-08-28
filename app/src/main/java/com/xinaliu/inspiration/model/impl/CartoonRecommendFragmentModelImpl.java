package com.xinaliu.inspiration.model.impl;

import com.alibaba.fastjson.JSON;
import com.xinaliu.inspiration.entity.KanDongManEntity;
import com.xinaliu.inspiration.http.HttpUtils;
import com.xinaliu.inspiration.http.util.HttpCallback;
import com.xinaliu.inspiration.model.CartoonRecommendFragmentModel;
import com.xinaliu.inspiration.model.ModelHttpCallback;
import com.xinaliu.inspiration.persenter.fragment.CartoonRecommendFragmentPersenter;

import java.util.List;
import java.util.Map;

/**
 * Created by liuwei on 2017/8/28 11:48
 */

public class CartoonRecommendFragmentModelImpl implements CartoonRecommendFragmentModel {

    private CartoonRecommendFragmentPersenter mCartoonRecommendFragmentPersenter;

    public CartoonRecommendFragmentModelImpl(CartoonRecommendFragmentPersenter mCartoonRecommendFragmentPersenter) {
        this.mCartoonRecommendFragmentPersenter = mCartoonRecommendFragmentPersenter;
    }

    @Override
    public void getRecommend(Map<String,String> map, final ModelHttpCallback modelHttpCallback){
        HttpUtils.cancel(this);
        modelHttpCallback.startRequest();
        HttpUtils.getRecommend(map,this,new HttpCallback<String>() {
            @Override
            public void onChildCallbackResult(Boolean isSuccess, String result) {
                modelHttpCallback.endRequest();
                if (isSuccess){
                    if (result != null){
                        try {
                            List<KanDongManEntity> kanDongManEntities = JSON.parseArray(result, KanDongManEntity.class);
                            mCartoonRecommendFragmentPersenter.setTabFragmentAdapter(kanDongManEntities);
                        } catch (Exception e) {
                            modelHttpCallback.resultError(-2,"解析错误");
                        }
                    }else {
                        modelHttpCallback.resultError(-1,"数据为空");
                    }
                }else {
                    modelHttpCallback.connectionFailed("网络开小差啦");
                }
            }
        });
    }

    @Override
    public void onDestroy() {
        HttpUtils.cancel(this);
    }
}
