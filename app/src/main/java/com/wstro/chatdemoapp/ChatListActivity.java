package com.wstro.chatdemoapp;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.wstro.app.common.base.BaseAppToolbarActivity;
import com.wstro.app.common.widget.DividerItemDecoration;
import com.wstro.chatdemoapp.model.ChatRecordInfo;
import com.wstro.chatdemoapp.model.ChatSession;
import com.wstro.chatdemoapp.model.ChatType;
import com.wstro.chatdemoapp.model.ChatUserInfo;
import com.wstro.chatdemoapp.model.MsgType;
import com.wstro.chatdemoapp.widget.CustomPtrFrameLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import in.srain.cube.views.ptr.PtrDefaultHandler2;
import in.srain.cube.views.ptr.PtrFrameLayout;

public class ChatListActivity extends BaseAppToolbarActivity {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    @BindView(R.id.ptr_layout)
    CustomPtrFrameLayout ptrLayout;

    ChatListAdapter adapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_chat_list;
    }

    @Override
    protected void initViewsAndEvents(Bundle bundle) {
        titleText.setText("聊天列表");

        initRecyclerView();

        initRefreshLayout();

        initAdapter();
    }

    private void initAdapter() {
        List<ChatSession> list = new ArrayList<>();

        for (int i = 1; i <= 5; i++) {
            ChatSession.UserBean userBean = new ChatSession.UserBean();
            userBean.setNickname("测试"+i);
            userBean.setAvatar("");
            userBean.setUsername(String.valueOf(i));

            ChatRecordInfo recordInfo = new ChatRecordInfo();
            recordInfo.setCreateDate("2017-10-31 16:21:28");
            recordInfo.setType(MsgType.text.getName());
            recordInfo.setData("消息内容"+i);
            recordInfo.setSenderId(String.valueOf(i));
            recordInfo.setReceiverId("-1");
            recordInfo.setSessionKey(String.valueOf(i));
            recordInfo.setSessionType(ChatType.PRIVATE.getType());

            ChatSession obj = new ChatSession();
            obj.setUnread(i);
            obj.setType(ChatType.PRIVATE.getType());
            obj.setSessionKey(String.valueOf(i));
            obj.setLastMessage(recordInfo);
            obj.setUser(userBean);

            list.add(obj);
        }

        adapter = new ChatListAdapter(R.layout.list_msg_item, list);
        recyclerView.setAdapter(adapter);
    }

    private void initRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
        recyclerView.addOnItemTouchListener(new OnItemChildClickListener() {
            @Override
            public void onSimpleItemChildClick(BaseQuickAdapter badapter, View view, int position) {
                ChatSession item = adapter.getItem(position);
                ChatUserInfo userInfo = new ChatUserInfo();

                userInfo.setTargetUserAvatar(item.getUser().getAvatar());
                userInfo.setTargetNickName(item.getUser().getNickname());
                userInfo.setTargetUserName(item.getUser().getUsername());

                CustomChatActivity.start(context,userInfo);
            }
        });

    }

    private void initRefreshLayout() {
        ptrLayout.setResistance(2.6f);
        ptrLayout.setKeepHeaderWhenRefresh(true);

        ptrLayout.setMode(PtrFrameLayout.Mode.REFRESH);

        ptrLayout.setPtrHandler(new PtrDefaultHandler2() {
            @Override
            public void onLoadMoreBegin(final PtrFrameLayout frame) {
            }

            @Override
            public void onRefreshBegin(final PtrFrameLayout frame) {
                ptrLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ptrLayout.refreshComplete();
                    }
                },1500);
            }
        });
    }


    @Override
    protected void initData() {

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
