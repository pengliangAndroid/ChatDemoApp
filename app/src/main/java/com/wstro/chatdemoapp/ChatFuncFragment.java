package com.wstro.chatdemoapp;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.wstro.app.common.base.BaseFragment;
import com.wstro.app.common.base.CommonAdapter;
import com.wstro.app.common.utils.LogUtil;
import com.wstro.app.common.widget.GridSpacingDecoration;

import java.util.Arrays;
import java.util.List;

public class ChatFuncFragment extends BaseFragment {
    private OnChatFuncItemClickListener onChatFuncItemClickListener;


    public interface OnChatFuncItemClickListener{
        void onChatFuncItemClick(ChatFuncItem item);
    }

    private RecyclerView rvToolItem;

    private CommonAdapter<String> adapter;

    private List<String> dataList;

    private static final String[] names = new String[]{"拍照","图片"};
    private static final int[] resIds = new int[]{
            R.mipmap.ic_wechat_camera,R.mipmap.ic_wechat_pic
    };

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_chat_func;
    }

    @Override
    protected void initViewsAndEvents(View self, Bundle savedInstanceState) {
        dataList = Arrays.asList(names);
        rvToolItem = (RecyclerView) self.findViewById(R.id.rv_chat_item);
    }

    @Override
    protected void initData() {
        adapter = new CommonAdapter<String>( R.layout.list_chat_tool_item,dataList) {
            @Override
            public void convertViewItem(BaseViewHolder baseViewHolder, String item) {
                baseViewHolder.setText(R.id.tv_text,item);
                baseViewHolder.setImageResource(R.id.iv_icon,resIds[baseViewHolder.getLayoutPosition()]);
            }
        };

        rvToolItem.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                    LogUtil.d("position:"+position);
                    ChatFuncItem item = null;
                    switch (position){
                        case 0:
                            item = ChatFuncItem.CAMERA;
                            break;
                        case 1:
                            item = ChatFuncItem.IMAGE;
                            break;
                       /* case 2:
                            item = ChatFuncItem.FILE;
                            break;*/

                    }

                    onChatItemClick(item);
            }
        });
        rvToolItem.setLayoutManager(new GridLayoutManager(context, 4));
        rvToolItem.addItemDecoration(new GridSpacingDecoration(context, R.dimen.list_item_space_10));
        rvToolItem.setAdapter(adapter);
    }

    public static ChatFuncFragment newInstance() {
        Bundle args = new Bundle();

        ChatFuncFragment fragment = new ChatFuncFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (getActivity() instanceof OnChatFuncItemClickListener) {
            onChatFuncItemClickListener = (OnChatFuncItemClickListener) getActivity();
        } else if (getParentFragment() instanceof OnChatFuncItemClickListener) {
            onChatFuncItemClickListener = (OnChatFuncItemClickListener) getParentFragment();
        } else {
            throw new IllegalArgumentException(context + " must implement interface " + OnChatFuncItemClickListener.class.getSimpleName());
        }
    }

    @Override
    public void onDetach() {
        onChatFuncItemClickListener = null;
        super.onDetach();
    }



    private void onChatItemClick(ChatFuncItem item){
        if(onChatFuncItemClickListener != null)
            onChatFuncItemClickListener.onChatFuncItemClick(item);
    }


}
