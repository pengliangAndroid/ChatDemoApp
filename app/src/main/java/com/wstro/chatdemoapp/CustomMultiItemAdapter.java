package com.wstro.chatdemoapp;

import android.support.annotation.LayoutRes;
import android.util.SparseArray;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

public abstract class CustomMultiItemAdapter<T> extends BaseQuickAdapter<T,BaseViewHolder> {
    private SparseArray<Integer> layouts;
    private static final int DEFAULT_VIEW_TYPE = -255;

    public CustomMultiItemAdapter(List<T> data) {
        super(data);
    }

    protected int getDefItemViewType(int position) {
        return getItemType(position);
    }

    protected abstract int getItemType(int position);

    protected void setDefaultViewTypeLayout(@LayoutRes int layoutResId) {
        this.addItemType(-255, layoutResId);
    }

    protected BaseViewHolder onCreateDefViewHolder(ViewGroup parent, int viewType) {
        return this.createBaseViewHolder(parent, this.getLayoutId(viewType));
    }

    private int getLayoutId(int viewType) {
        return ((Integer) this.layouts.get(viewType)).intValue();
    }

    protected void addItemType(int type, @LayoutRes int layoutResId) {
        if (this.layouts == null) {
            this.layouts = new SparseArray();
        }

        this.layouts.put(type, Integer.valueOf(layoutResId));
    }
}

