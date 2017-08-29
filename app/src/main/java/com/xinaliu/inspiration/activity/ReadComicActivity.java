package com.xinaliu.inspiration.activity;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;

import com.xinaliu.inspiration.R;
import com.xinaliu.inspiration.adapter.ReadComicActivityAdapter;
import com.xinaliu.inspiration.entity.ComicChapterDetailsEntity;
import com.xinaliu.inspiration.util.LogUtils;
import com.xinaliu.inspiration.util.NetWorkUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuwei on 2017/8/29 10:57
 */

public class ReadComicActivity extends BaseNewActivity {

    private static final String TAG = "ReadComicActivity";

    public static final String CHAPTER_SOURCE_KEY = "CHAPTER_SOURCE_KEY";
    private RecyclerView mRecyclerView;

    public static void startActivity(Context context, ComicChapterDetailsEntity.ComicChapterBean.ChapterListBean.ChapterSourceBean chapter_source) {
        if (chapter_source == null) return;
        Intent intent = new Intent(context, ReadComicActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable(CHAPTER_SOURCE_KEY, chapter_source);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_comic);
        initView();
        boolean wifiConnected = NetWorkUtils.isWifiConnected(this.getApplicationContext());
        if (!wifiConnected){
//            NetWorkActivity.startActivityForResult(this,100);
            showAlertDialog();
        }else {
            getData();
        }
    }
    private AlertDialog  mDialog;

    private void showAlertDialog() {
        AlertDialog.Builder mAlertDialog = new AlertDialog.Builder(this);
        View view = LayoutInflater.from(this.getApplicationContext()).inflate(R.layout.hint_network, null);
        view.findViewById(R.id.tv_cancel).setOnClickListener(this);
        view.findViewById(R.id.tv_continue).setOnClickListener(this);
        mAlertDialog.setView(view);
        mAlertDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                finish();
                return false;
            }
        });
        mDialog = mAlertDialog.show();
    }

    private void getData() {
        ComicChapterDetailsEntity.ComicChapterBean.ChapterListBean.ChapterSourceBean chapter_source = getIntent().getParcelableExtra(CHAPTER_SOURCE_KEY);
        if (chapter_source == null) {
            finish();
            return;
        }
//        ViewGroup.LayoutParams layoutParams = itemViewHolder.itemView.getLayoutParams();
//        layoutParams.height = LinearLayout.LayoutParams.WRAP_CONTENT;
        int start_num = chapter_source.getStart_num();
        int end_num = chapter_source.getEnd_num();

        String url = "http://mhpic.";
        String chapter_domain = chapter_source.getChapter_domain();
        if (TextUtils.isEmpty(chapter_domain))chapter_domain = "samanlehua.com";
        url += chapter_domain+"/";
        String rule = chapter_source.getRule();
        url += rule;
        String suffix = "";
        if ("zymkcdn.com".equals(chapter_domain)){
            suffix = "-mht.middle";
        }else if ("samanlehua.com".equals(chapter_domain)){
            suffix += "";
        }
        LogUtils.d(TAG, chapter_source.toString());
        List<String> list = new ArrayList<>(end_num);
        for (int i = start_num; i <= end_num; i++) {
            String s  = url.replace("$$",i+"") + suffix;
            list.add(s);
        }
        setAdapter(list);
    }

    private void setAdapter(List<String> list) {
        if (list == null || list.size() == 0){
            finish();
            return;
        }
        ReadComicActivityAdapter readComicActivityAdapter = new ReadComicActivityAdapter(list, R.layout.item_activity_read_comic);
        mRecyclerView.setAdapter(readComicActivityAdapter);
    }

    private void initView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.rlv_ReadComicActivity);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100){
            if (resultCode == Activity.RESULT_OK){
                getData();
            }else {
                finish();
            }
        }
    }

    @Override
    public void onClick(View v) {
        if (mDialog != null)mDialog.dismiss();
        if (v.getId() == R.id.tv_cancel){
            finish();
        }else if (v.getId() == R.id.tv_continue){
            getData();
        }
    }
}
