package com.wstro.chatdemoapp.widget;

import android.content.Context;
import android.util.AttributeSet;

import in.srain.cube.views.ptr.PtrFrameLayout;


public class PtrChatFrameLayout extends PtrFrameLayout {

    /**
     * headerView
     */
     ChatRefreshHeader mHeaderView;

    public PtrChatFrameLayout(Context context) {
        super(context);
        initView();
    }

    public PtrChatFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public PtrChatFrameLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView();
    }


    private void initView() {
        mHeaderView= new ChatRefreshHeader(getContext());
        setHeaderView(mHeaderView);
        addPtrUIHandler(mHeaderView);
    }


}
