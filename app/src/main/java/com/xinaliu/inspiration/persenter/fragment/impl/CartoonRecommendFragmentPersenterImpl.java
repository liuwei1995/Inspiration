package com.xinaliu.inspiration.persenter.fragment.impl;

import com.xinaliu.inspiration.IView.CartoonRecommendFragmentView;
import com.xinaliu.inspiration.entity.KanDongManEntity;
import com.xinaliu.inspiration.model.CartoonRecommendFragmentModel;
import com.xinaliu.inspiration.model.ModelHttpCallback;
import com.xinaliu.inspiration.model.impl.CartoonRecommendFragmentModelImpl;
import com.xinaliu.inspiration.persenter.fragment.CartoonRecommendFragmentPersenter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 漫画推荐
 * Created by liuwei on 2017/8/28 11:43
 */

public class CartoonRecommendFragmentPersenterImpl implements CartoonRecommendFragmentPersenter {

    private CartoonRecommendFragmentView mCartoonRecommendFragmentView;
    private CartoonRecommendFragmentModel mCartoonRecommendFragmentModel;

    public CartoonRecommendFragmentPersenterImpl(CartoonRecommendFragmentView mCartoonRecommendFragmentView) {
        this.mCartoonRecommendFragmentView = mCartoonRecommendFragmentView;
        mCartoonRecommendFragmentModel = new CartoonRecommendFragmentModelImpl(this);
    }

    @Override
    public void getRecommend() {
        Map<String,String> map = new HashMap<>();
        map.put("client-channel",android.os.Build.MANUFACTURER.toLowerCase());
        map.put("client-version","1.6.2");
        map.put("client-type","android");
        mCartoonRecommendFragmentModel.getRecommend(map, new ModelHttpCallback() {
            @Override
            public void startRequest() {
                mCartoonRecommendFragmentView.getRecommendStartRequest();
            }

            @Override
            public void endRequest() {
                mCartoonRecommendFragmentView.getRecommendEndRequest();
            }

            @Override
            public void connectionFailed(String failedMessge) {
                mCartoonRecommendFragmentView.getRecommendConnectionFailed(failedMessge);
            }

            @Override
            public void resultError(int code, String errorMessge) {
                mCartoonRecommendFragmentView.getRecommendResultError(code,errorMessge);
            }
        });
    }

    @Override
    public void setTabFragmentAdapter(List<KanDongManEntity> listKanDongManEntity) {
        mCartoonRecommendFragmentView.setTabFragmentAdapter(listKanDongManEntity);
    }

    @Override
    public void onDestroy() {
        mCartoonRecommendFragmentModel.onDestroy();
    }
}
