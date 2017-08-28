package com.xinaliu.inspiration.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.xinaliu.inspiration.IView.ComicChapterDetailsActivityView;
import com.xinaliu.inspiration.R;
import com.xinaliu.inspiration.adapter.ComicChapterDetailsActivityAdapter;
import com.xinaliu.inspiration.adapter.base.HealthyMultipleAdapter;
import com.xinaliu.inspiration.entity.ComicChapterDetailsEntity;
import com.xinaliu.inspiration.util.ToastUtil;

import java.util.List;

/**
 * 漫画章节详情
 * Created by liuwei on 2017/8/28 16:27
 */

public class ComicChapterDetailsActivity extends BaseNewActivity implements
        ComicChapterDetailsActivityView,HealthyMultipleAdapter.OnItemClickListener<ComicChapterDetailsEntity.ComicChapterBean>{

    private RecyclerView mRecyclerView;

    public static void startActivity(Context context) {
        context.startActivity(new Intent(context, ComicChapterDetailsActivity.class));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comic_chapter_details);
        initView();
    }

    private void initView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.rlv_activity_cartoon_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
//        int spanCount = 4;
//        mRecyclerView.setLayoutManager(new GridLayoutManager(this,spanCount));
//        int spacing = 30;
//        mRecyclerView.addItemDecoration(new GridSpacingItemDecoration(spanCount, spacing, true));
    }

    @Override
    public void setComicChapterDetailsEntity(ComicChapterDetailsEntity mComicChapterDetailsEntity) {
        if (mComicChapterDetailsEntity == null)return;
//        String comic_name = mComicChapterDetailsEntity.getComic_name();
        List<ComicChapterDetailsEntity.ComicChapterBean> comic_chapter = mComicChapterDetailsEntity.getComic_chapter();
        ComicChapterDetailsActivityAdapter comicChapterDetailsActivityAdapter = new ComicChapterDetailsActivityAdapter(comic_chapter);
        mRecyclerView.setAdapter(comicChapterDetailsActivityAdapter);
        comicChapterDetailsActivityAdapter.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(View view, ComicChapterDetailsEntity.ComicChapterBean item, int position) {
        ToastUtil.toastSome(this,item.getChapter_type());
    }
}
