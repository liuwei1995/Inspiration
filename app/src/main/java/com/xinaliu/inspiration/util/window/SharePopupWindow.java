package com.xinaliu.inspiration.util.window;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.xinaliu.inspiration.R;
import com.xinaliu.inspiration.adapter.base.HealthyAdapter;
import com.xinaliu.inspiration.adapter.base.HealthyViewHolder;
import com.xinaliu.inspiration.entity.ShareDataEntity;
import com.xinaliu.inspiration.entity.ShareEntity;
import com.xinaliu.inspiration.handler.TaskHandler;
import com.xinaliu.inspiration.handler.TaskHandlerImpl;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * 分享PopupWindow
 *
 * Created by liuwei on 2017/7/31 09:24
 */

public class SharePopupWindow extends HealthyPopupWindow implements TaskHandler<SharePopupWindow>,PopupWindow.OnDismissListener{

    private ShareDataEntity mShareDataEntity;

    public SharePopupWindow(Context context,ShareDataEntity mShareDataEntity) {
        super(context, R.layout.share_webview_activity);
        this.mShareDataEntity = mShareDataEntity;
        setOnDismissListener(this);
    }

    @Override
    public void setContentView(Context context, View contentView) {
        RecyclerView rv = getView(R.id.rv_share_webview_activity);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 4);
        rv.setLayoutManager(gridLayoutManager);
        List<ShareEntity> listShare = new ArrayList<>();
        listShare.add(new ShareEntity(1,"QQ好友",R.mipmap.qq_friend));
        listShare.add(new ShareEntity(2,"QQ空间",R.mipmap.qq_space));
        listShare.add(new ShareEntity(3,"微信好友",R.mipmap.weixin_friend));
        listShare.add(new ShareEntity(4,"微信朋友圈",R.mipmap.weixin_space));
        listShare.add(new ShareEntity(5,"更多",R.mipmap.more));
        HealthyAdapter<ShareEntity> healthyAdapter = new HealthyAdapter<ShareEntity>(listShare, R.layout.item_share_webview_activity) {
            @Override
            public void convert(HealthyViewHolder holder, ShareEntity item, int position) {
                TextView view = holder.getView(R.id.actv_item_webview_activity);
                Drawable nav_up = null;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    nav_up = holder.itemView.getContext().getResources().getDrawable(item.getDrawableRes(),null);
                }else {
                    nav_up = holder.itemView.getContext().getResources().getDrawable(item.getDrawableRes());
                }
                if (nav_up != null){
                    nav_up.setBounds(0, 0, 120, 120);
                    view.setCompoundDrawables(null,nav_up,null,null);
                }
                view.setText(item.getName());
            }
        };
        healthyAdapter.setOnItemClickListener(new HealthyAdapter.OnItemClickListener<ShareEntity>() {
            @Override
            public void onItemClick(View view, ShareEntity item, int position) {
                if (mShareDataEntity == null)return;
//                if (item.getType() == 1 || item.getType() == 2){
//                    ShareQQActivity.startActivity(view.getContext(),item.getType() == 1? 1 : 2,mShareDataEntity);
//                }else if (item.getType() == 3 || item.getType() == 4){
//                    WXEntryActivity.startActivity(view.getContext(),1,item.getType() == 3 ? SendMessageToWX.Req.WXSceneSession : SendMessageToWX.Req.WXSceneTimeline,mShareDataEntity);
//                }else{
//                    Intent share_intent = new Intent();
//                    share_intent.setAction(Intent.ACTION_SEND);
//                    share_intent.setType("text/plain");
//                    share_intent.putExtra(Intent.EXTRA_SUBJECT, "f分享");
//                    share_intent.putExtra(Intent.EXTRA_TEXT, mShareDataEntity.getShareUrl());
//                    share_intent = Intent.createChooser(share_intent, "分享");
//                    view.getContext().startActivity(share_intent);
//                }
                dismiss();
            }
        });
        rv.setAdapter(healthyAdapter);
    }

    private static final int ZERO = 0;

    @Override
    public void showAtLocation(View parent) {
        mHandler.removeMessages(ZERO);
        mHandler.sendEmptyMessageDelayed(ZERO,300);
        super.showAtLocation(parent);
    }

    private Handler mHandler = new TaskHandlerImpl<>(this);

    @Override
    public void handleMessage(WeakReference<SharePopupWindow> weakReference, Message msg) {
        // 设置背景颜色变暗
        setAlpha(0.5f);
    }

    /**
     * 设置Activity的背景透明度
     * @param alpha
     */
    private synchronized void setAlpha(float alpha){

        // 设置背景颜色变暗
        Context con = SharePopupWindow.this.getContentView().getContext();
        if (con != null && con instanceof Activity){
            Window window = ((Activity) con).getWindow();
            if (window != null){
                WindowManager.LayoutParams params = window.getAttributes();
                params.alpha = alpha;
                window.setAttributes(params);
            }
        }
    }

    @Override
    public void onDismiss() {
        mHandler.removeMessages(ZERO);
        setAlpha(1f);
    }
}
