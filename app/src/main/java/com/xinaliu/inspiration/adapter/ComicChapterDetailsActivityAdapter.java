package com.xinaliu.inspiration.adapter;

import android.support.annotation.LayoutRes;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.ViewGroup;

import com.xinaliu.inspiration.R;
import com.xinaliu.inspiration.adapter.base.HealthyAdapter;
import com.xinaliu.inspiration.adapter.base.HealthyMultipleAdapter;
import com.xinaliu.inspiration.adapter.base.HealthyMultipleViewHolder;
import com.xinaliu.inspiration.adapter.base.HealthyViewHolder;
import com.xinaliu.inspiration.entity.ComicChapterDetailsEntity;
import com.xinaliu.inspiration.util.GridSpacingItemDecoration;

import java.util.List;

/**
 * 漫画章节详情
 * Created by liuwei on 2017/8/28 17:40
 */

public class ComicChapterDetailsActivityAdapter extends HealthyMultipleAdapter<ComicChapterDetailsEntity.ComicChapterBean>{

    public ComicChapterDetailsActivityAdapter(List<ComicChapterDetailsEntity.ComicChapterBean> mDatas) {
        super(mDatas);
    }

    @Override
    public HealthyMultipleViewHolder<ComicChapterDetailsEntity.ComicChapterBean> onCreateHolder(ViewGroup parent, int viewType) {
        if (viewType == 1){
            return new HealthyMultipleViewHolder<>(parent,viewType, R.layout.item_activity_comic_chapter_details_serial);
        }else if (viewType == 2){
            return new HealthyMultipleViewHolder<>(parent,viewType, R.layout.item_activity_comic_chapter_details_fanwai);
        }
        return null;
    }


    @Override
    public int getItemViewType(int position, ComicChapterDetailsEntity.ComicChapterBean item) {
        String chapter_type = item.getChapter_type();
        if (TextUtils.isEmpty(chapter_type))return -1;
        if (chapter_type.equals("连载")){
            return 1;
        }else if (chapter_type.equals("番外")){
            return 2;
        }
        return -1;
    }

    @Override
    public void onBindHolder(HealthyMultipleViewHolder holder, ComicChapterDetailsEntity.ComicChapterBean item, int position, int viewType) {
        if (viewType == 1){
            RecyclerView mRecyclerView = (RecyclerView) holder.getView(R.id.rlv_item_activity_comic_chapter_details_serial);
            int spanCount = 4;
            mRecyclerView.setLayoutManager(new GridLayoutManager(holder.itemView.getContext(),spanCount));
            int spacing = 30;
            mRecyclerView.addItemDecoration(new GridSpacingItemDecoration(spanCount, spacing, true));
            List<ComicChapterDetailsEntity.ComicChapterBean.ChapterListBean> chapter_list = item.getChapter_list();
            ItemActivityComicChapterDetailsSerial itemActivityComicChapterDetailsSerial = new ItemActivityComicChapterDetailsSerial(chapter_list, R.layout.item_item_activity_comic_chapter_details);
            mRecyclerView.setAdapter(itemActivityComicChapterDetailsSerial);
        }else if (viewType == 2){
            RecyclerView mRecyclerView = (RecyclerView) holder.getView(R.id.rlv_item_activity_comic_chapter_details_fanwai);
            int spanCount = 4;
            mRecyclerView.setLayoutManager(new GridLayoutManager(holder.itemView.getContext(),spanCount));
            int spacing = 30;
            mRecyclerView.addItemDecoration(new GridSpacingItemDecoration(spanCount, spacing, true));
            List<ComicChapterDetailsEntity.ComicChapterBean.ChapterListBean> chapter_list = item.getChapter_list();
            ItemActivityComicChapterDetailsFanwai itemActivityComicChapterDetailsFanwai = new ItemActivityComicChapterDetailsFanwai(chapter_list, R.layout.item_item_activity_comic_chapter_details);
            mRecyclerView.setAdapter(itemActivityComicChapterDetailsFanwai);
        }
    }
    public class ItemActivityComicChapterDetailsSerial extends HealthyAdapter<ComicChapterDetailsEntity.ComicChapterBean.ChapterListBean>{

        public ItemActivityComicChapterDetailsSerial(List<ComicChapterDetailsEntity.ComicChapterBean.ChapterListBean> mDatas, @LayoutRes int itemLayoutId) {
            super(mDatas, itemLayoutId);
        }

        @Override
        public void convert(HealthyViewHolder holder, ComicChapterDetailsEntity.ComicChapterBean.ChapterListBean item, int position) {

        }
    }
    public class ItemActivityComicChapterDetailsFanwai extends HealthyAdapter<ComicChapterDetailsEntity.ComicChapterBean.ChapterListBean>{

        public ItemActivityComicChapterDetailsFanwai(List<ComicChapterDetailsEntity.ComicChapterBean.ChapterListBean> mDatas, @LayoutRes int itemLayoutId) {
            super(mDatas, itemLayoutId);
        }

        @Override
        public void convert(HealthyViewHolder holder, ComicChapterDetailsEntity.ComicChapterBean.ChapterListBean item, int position) {

        }
    }

}
