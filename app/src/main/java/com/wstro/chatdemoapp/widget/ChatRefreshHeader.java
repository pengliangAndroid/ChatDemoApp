package com.wstro.chatdemoapp.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import com.wstro.chatdemoapp.R;

import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrUIHandler;
import in.srain.cube.views.ptr.indicator.PtrIndicator;


/**
 * ClassName: CommonRefreshHeader
 * Function:
 * Date:     2017/9/13 0013 16:22
 *
 * @author Administrator
 * @see
 */
public class ChatRefreshHeader extends FrameLayout implements PtrUIHandler {

    private View mProgressBar;


    public ChatRefreshHeader(Context context) {
        super(context);
        initViews(context);
    }

    public ChatRefreshHeader(Context context, AttributeSet attrs) {
        super(context, attrs);
        initViews(context);
    }

    public ChatRefreshHeader(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initViews(context);
    }

    protected void initViews(Context context) {
        View header = LayoutInflater.from(getContext()).inflate(R.layout.chat_refresh_header_view, this);

        mProgressBar = header.findViewById(R.id.progress_bar);
        if (android.os.Build.VERSION.SDK_INT > 22) {
            //android 6.0替换clip的加载动画
            Drawable drawable =  context.getResources().getDrawable(R.drawable.progress_loading_v23);
            ((ProgressBar)mProgressBar).setIndeterminateDrawable(drawable);
        }
        resetView();
    }

    private void resetView() {
        mProgressBar.setVisibility(VISIBLE);
    }



    @Override
    public void onUIReset(PtrFrameLayout frame) {
        resetView();
    }

    @Override
    public void onUIRefreshPrepare(PtrFrameLayout frame) {
        mProgressBar.setVisibility(VISIBLE);

    }

    @Override
    public void onUIRefreshBegin(PtrFrameLayout frame) {
        mProgressBar.setVisibility(VISIBLE);
    }

    @Override
    public void onUIRefreshComplete(PtrFrameLayout frame) {
        mProgressBar.setVisibility(INVISIBLE);

    }

    @Override
    public void onUIPositionChange(PtrFrameLayout frame, boolean isUnderTouch, byte status, PtrIndicator ptrIndicator) {
    }

}
