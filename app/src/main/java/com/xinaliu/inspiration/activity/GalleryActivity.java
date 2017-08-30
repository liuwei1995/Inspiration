package com.xinaliu.inspiration.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.view.jameson.library.CardAdapter;
import com.view.jameson.library.CardScaleHelper;
import com.xinaliu.inspiration.R;

import java.util.ArrayList;
import java.util.List;

public class GalleryActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private CardScaleHelper mCardScaleHelper;
    private int mLastPos;
    private ImageView mBlurView;

    public static void startActivity(Context context) {
        context.startActivity(new Intent(context, GalleryActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        setContentView(R.layout.activity_gallery);

        init();
    }



    private void init() {
        List<String> list = new ArrayList<>();
        list.add("http://img0.imgtn.bdimg.com/it/u=2472221060,3111898232&fm=26&gp=0.jpg");
        list.add("http://img2.imgtn.bdimg.com/it/u=3556326025,2943004307&fm=26&gp=0.jpg");
        list.add("http://img1.imgtn.bdimg.com/it/u=396929541,4241644058&fm=26&gp=0.jpg");
        list.add("http://img5.imgtn.bdimg.com/it/u=2112203937,3426633288&fm=26&gp=0.jpg");
        list.add("http://img0.imgtn.bdimg.com/it/u=2472221060,3111898232&fm=26&gp=0.jpg");
        list.add("http://img2.imgtn.bdimg.com/it/u=3556326025,2943004307&fm=26&gp=0.jpg");
        list.add("http://img1.imgtn.bdimg.com/it/u=396929541,4241644058&fm=26&gp=0.jpg");
        list.add("http://img5.imgtn.bdimg.com/it/u=2112203937,3426633288&fm=26&gp=0.jpg");

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(new CardAdapter(list));
        // mRecyclerView绑定scale效果
        mCardScaleHelper = new CardScaleHelper();
        mCardScaleHelper.setScale(0.7f);
        mCardScaleHelper.setCurrentItemPos(0);
        mCardScaleHelper.attachToRecyclerView(mRecyclerView);

        initBlurBackground();
    }

    private void initBlurBackground() {
        mBlurView = (ImageView) findViewById(R.id.blurView);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    notifyBackgroundChange();
                }
            }
        });

        notifyBackgroundChange();
    }

    private void notifyBackgroundChange() {
        if (mLastPos == mCardScaleHelper.getCurrentItemPos()) return;
//        mLastPos = mCardScaleHelper.getCurrentItemPos();
//        final int resId = mList.get(mCardScaleHelper.getCurrentItemPos());
//        mBlurView.removeCallbacks(mBlurRunnable);
//        mBlurRunnable = new Runnable() {
//            @Override
//            public void run() {
//                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), resId);
//                ViewSwitchUtils.startSwitchBackgroundAnim(mBlurView, BlurBitmapUtils.getBlurBitmap(mBlurView.getContext(), bitmap, 15));
//            }
//        };
//        mBlurView.postDelayed(mBlurRunnable, 500);
    }

}
