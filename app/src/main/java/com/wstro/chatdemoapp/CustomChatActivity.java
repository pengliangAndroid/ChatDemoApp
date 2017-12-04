package com.wstro.chatdemoapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.wstro.app.common.base.BaseAppToolbarActivity;
import com.wstro.app.common.utils.CommonUtils;
import com.wstro.app.common.utils.DeviceUtils;
import com.wstro.app.common.utils.LogUtil;
import com.wstro.app.common.utils.PermissionUtils;
import com.wstro.chatdemoapp.model.ChatRecordInfo;
import com.wstro.chatdemoapp.model.ChatSession;
import com.wstro.chatdemoapp.model.ChatUserInfo;
import com.wstro.chatdemoapp.model.MsgStatus;
import com.wstro.chatdemoapp.model.MsgType;
import com.wstro.chatdemoapp.presenter.CustomChatPresenter;
import com.wstro.chatdemoapp.view.CustomChatView;
import com.wstro.chatdemoapp.widget.ChatKeyboard;
import com.wstro.chatdemoapp.widget.PtrChatFrameLayout;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import in.srain.cube.views.ptr.PtrDefaultHandler2;
import in.srain.cube.views.ptr.PtrFrameLayout;
import io.github.rockerhieu.emojicon.CustomEmojiFragment;
import io.github.rockerhieu.emojicon.EmojiconsFragment;
import io.github.rockerhieu.emojicon.emoji.Emojicon;
import rx.Subscription;


public class CustomChatActivity extends BaseAppToolbarActivity implements CustomChatView,CustomEmojiFragment.OnEmojiconClickedListener,
        CustomEmojiFragment.OnEmojiconBackspaceClickedListener, ChatFuncFragment.OnChatFuncItemClickListener,
        ChatKeyboard.OnOperationListener {
    private static final int REQUEST_GET_IMAGE = 1;
    private static final int REQUEST_CAMERA = 2;
    private static final int REQUEST_IMAGE_SELECT = 3;

    CustomChatPresenter presenter;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.ptr_layout)
    PtrChatFrameLayout ptrLayout;
    @BindView(R.id.chat_keyboard)
    ChatKeyboard chatKeyboard;

    @BindView(R.id.view_online)
    View onlineView;

    private CustomChatAdapter adapter;

    private String dirPath,imagePath;

    private ChatUserInfo chatUserInfo;

    private Subscription rxReceiveMsg;

    private ChatSession chatSession;

    private boolean isGetCustomer;

    public static void start(Context context, ChatUserInfo info) {
        Intent starter = new Intent(context, CustomChatActivity.class);
        starter.putExtra("data", info);
        context.startActivity(starter);
    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_custom_chat;
    }

    @Override
    protected void initViewsAndEvents(Bundle bundle) {
        chatUserInfo = getIntent().getParcelableExtra("data");

        rightImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //ChatSetActivity.start(context);
            }
        });

        initRefreshLayout();

        initRecyclerView();

        initAdapter();
    }

    private void initAdapter() {
        //accountInfoMap = new HashMap<>();
        List<ChatRecordInfo> dataList = new ArrayList<>();

        adapter = new CustomChatAdapter(this, chatUserInfo, dataList);
        recyclerView.setAdapter(adapter);
        recyclerView.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter badapter, View view, int position) {
                /*ChatRecordInfo item = adapter.getItem(position);
                if(MsgType.image.getName().equals(item.getType())){
                    String imageUrl = item.getData();
                    if(imageUrl != null && !imageUrl.contains("/")){
                        imageUrl = DataConstants.BASE_FILE_URL + imageUrl;
                    }
                    ImageBrowserActivity.start(context,imageUrl);
                }*/
            }

        });
        recyclerView.post(new Runnable() {
            @Override
            public void run() {
                chatKeyboard.setBottomBarVisibility(View.GONE);
            }
        });
    }

    private void initRecyclerView() {
        chatKeyboard.setOnOperationListener(this);
        chatKeyboard.setFaceBtnInVisible();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void initRefreshLayout() {
        ptrLayout.setResistance(2.0f);
        ptrLayout.setKeepHeaderWhenRefresh(true);

        ptrLayout.setMode(PtrFrameLayout.Mode.REFRESH);

        ptrLayout.setPtrHandler(new PtrDefaultHandler2() {
            @Override
            public void onLoadMoreBegin(final PtrFrameLayout frame) {
            }

            @Override
            public void onRefreshBegin(final PtrFrameLayout frame) {
                /*if (presenter.isHasMoreData()) {
                    presenter.getChatRecordList(chatUserInfo.getSessionKey());
                }else{
                    ptrLayout.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            ptrLayout.refreshComplete();
                        }
                    },800);
                }*/

                ptrLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ptrLayout.refreshComplete();
                    }
                },800);

            }
        });
    }

    @Override
    protected void initData() {
        presenter = new CustomChatPresenter();
        presenter.attachView(this);

        dirPath = presenter.getCacheDirPath(this);

        loadChatData();
       /* if(!chatUserInfo.isCustomer()) {
            loadChatData();
        }else{
            showProgressDialog("获取客服中...");
            String groupId = chatUserInfo.getGroupId();
            if(TextUtils.isEmpty(groupId)){
                groupId = chatUserInfo.getTenantId();
                presenter.getCustomer(groupId);
            }else{
                presenter.getCustomerByGroupId(groupId);
            }
        }*/

        /*rxReceiveMsg = RxBus.getDefault().toObservable(ReceiveChatMsgEvent.class)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<ReceiveChatMsgEvent>() {
                    @Override
                    public void call(ReceiveChatMsgEvent event) {
                        if (event.isOnChat()) {
                            adapter.addData(presenter.buildChatRecordInfo(event.getMsg(),
                                    chatUserInfo.getTargetUserName(), true));
                            scrollToBottom(true);
                        }
                    }
                });*/
    }

    private void loadChatData() {
        String title = chatUserInfo.getTargetNickName();
        if (TextUtils.isEmpty(title)) {
            title = chatUserInfo.getTargetUserName();
        }
        titleText.setText(title);

        //JMessageHelper.get().enterSingleConversation(chatUserInfo.getTargetUserName());

        if (TextUtils.isEmpty(chatUserInfo.getSessionKey())) {
            presenter.saveSession(chatUserInfo.getTargetUserName(), titleText.getText().toString(),getChatType());
        } else {
            getSessionInfo(chatUserInfo.getSessionKey(), chatUserInfo.getSessionId());
        }
    }

    private void getSessionInfo(String sessionKey,int sessionId){
        presenter.getChatRecordList(sessionKey);
        presenter.markIMRead(sessionId);
    }

    private String getChatType(){
        return chatUserInfo.isCustomer()? CustomChatPresenter.CHAT_CUSTOM_TYPE:CustomChatPresenter.CHAT_TYPE;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (presenter != null)
            presenter.detachView();

        /*if(!chatUserInfo.isCustomer()) {
            RxBus.getDefault().post(new MarkReadEvent(false, chatUserInfo.getSessionId()));
        }*/

        //JMessageHelper.get().exitConversation();

        if (rxReceiveMsg != null)
            rxReceiveMsg.unsubscribe();
    }

    @Override
    public void onEmojiconBackspaceClicked(View v) {
        EmojiconsFragment.backspace(chatKeyboard.getEditText());
    }

    @Override
    public void onEmojiconClicked(Emojicon emojicon) {
        EmojiconsFragment.input(chatKeyboard.getEditText(), emojicon);
    }


    @Override
    public void onChatFuncItemClick(ChatFuncItem item) {
        /*if(!isGetCustomer)
            return;*/

        switch (item) {
            case IMAGE:
                PermissionUtils.requestMultiPermissions(this, new int[]{PermissionUtils.CODE_READ_EXTERNAL_STORAGE, PermissionUtils.CODE_WRITE_EXTERNAL_STORAGE}, grantListener);
                break;
            case CAMERA:
                PermissionUtils.requestPermission(this, PermissionUtils.CODE_CAMERA, grantListener);
                break;
            case FILE:
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        PermissionUtils.requestPermissionsResult(this, requestCode, permissions, grantResults, grantListener);
    }

    private final PermissionUtils.PermissionGrantListener grantListener = new PermissionUtils.PermissionGrantListener() {
        @Override
        public void onPermissionGranted(int requestCode) {
            LogUtil.d("onPermissionGranted:" + requestCode);
            if (requestCode == PermissionUtils.CODE_CAMERA) {
                Intent intent = new Intent(
                        MediaStore.ACTION_IMAGE_CAPTURE);
                intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                intent.setFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);//增加读写权限

                File file = new File(dirPath, String.valueOf(System.currentTimeMillis()));
                imagePath = file.getPath();
                intent.putExtra(MediaStore.EXTRA_OUTPUT, DeviceUtils.getExternalFileUri(context,file));
                startActivityForResult(intent, REQUEST_CAMERA);
            } else {
                SelectImageActivity.startMultiSelect(CustomChatActivity.this,true,5);
            }
        }

        @Override
        public void onPermissionDenied(int requestCode) {
            LogUtil.d("onPermissionDenied:" + requestCode);
            stopProgressDialog();
            showToast(R.string.permission_denied_hint);
        }
    };


    public void sendChatMessage(MsgType type, String content) {
        String username = chatUserInfo.getTargetUserName();

        /*Message chatMessage = null;
        try {
            chatMessage = JMessageConverter.createSingleMsg(username,type,content);
        } catch (Exception e) {
            //e.printStackTrace();
            AppUtils.saveCrashInfo2File(dirPath,e);
        }

        if (chatMessage == null) {
            showToast("创建消息失败，消息服务器连接断开");
            return;
        }*/

        adapter.addData(presenter.buildChatRecordInfo(type, username, content, false));
        scrollToBottom(true);

        //JMessageHelper.get().sendMessage(chatMessage);

        /*ChatMsgReq req = createUserMsgReq(type,content);

        if (type == MsgType.text) {
            presenter.uploadChatRecord(req, adapter.getData().size());
        } else if (type == MsgType.image) {
            File file = new File(content);
            LogUtil.d("msg", file.getAbsolutePath());
            presenter.updateChatFile(req, file,dirPath, adapter.getData().size());
        }
*/
        //if(!chatUserInfo.isCustomer())
        //RxBus.getDefault().post(new SendChatMsgEvent(chatMessage,username,chatSession));
    }

    /*private ChatMsgReq createUserMsgReq(MsgType type,String content) {
        ChatMsgReq req = new ChatMsgReq();
        req.setSessionType(getChatType());
        req.setContent(content);
        req.setType(type.getName());
        req.setReceiverId(chatUserInfo.getTargetUserName());
        req.setExtra(chatUserInfo.getExtra());
        return req;
    }

    private ChatMsgReq createCoustomerMsgReq(MsgType type,String content) {
        ChatMsgReq req = new ChatMsgReq();
        req.setSessionType(getChatType());
        req.setContent(content);
        req.setType(type.getName());
        //req.setReceiverId(chatUserInfo.getTargetUserName());
        //req.setExtra(chatUserInfo.getExtra());
        return req;
    }*/

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode != Activity.RESULT_OK)
            return;

        if (requestCode == REQUEST_GET_IMAGE) {
            sendChatMessage(MsgType.image, imagePath);
        } else if (requestCode == REQUEST_CAMERA) {
            sendChatMessage(MsgType.image, imagePath);
        }else if(requestCode == SelectImageActivity.CODE_MULTI){
            ArrayList<String> extra = data.getStringArrayListExtra(SelectImageActivity.EXTRA_RESULT);
            if(CommonUtils.isEmptyArray(extra))
                return;

            for (int i = 0; i < extra.size(); i++) {
                LogUtil.d("path:"+extra.get(i));
                sendChatMessage(MsgType.image, extra.get(i));
            }
        }
    }


    @Override
    public void onSoftKeyboardOpened() {
        //scrollToBottom(true);
    }

    @Override
    public void onSoftKeyboardClosed() {

    }

    @Override
    public void send(String content) {
        sendChatMessage(MsgType.text, content);
    }


    private void scrollToBottom(boolean isSmoothScroll) {
        if (adapter.getData().size() != 0) {
            if (isSmoothScroll) {
                recyclerView.post(new Runnable() {
                    @Override
                    public void run() {
                        recyclerView.smoothScrollToPosition(adapter.getData().size() - 1);
                    }
                });

            } else {
                //recyclerView.scrollToPosition(adapter.getData().size() - 1);
                recyclerView.post(new Runnable() {
                    @Override
                    public void run() {
                        recyclerView.scrollToPosition(adapter.getData().size() - 1);
                    }
                });

            }
        }
    }

    /**
     * 若软键盘或表情键盘弹起，点击上端空白处应该隐藏输入法键盘
     *
     * @return 会隐藏输入法键盘的触摸事件监听器
     */
    private View.OnTouchListener getOnTouchListener() {
        return new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                chatKeyboard.hideLayout();
                chatKeyboard.hideKeyboard(CustomChatActivity.this);
                return false;
            }
        };
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && chatKeyboard.isShow()) {
            chatKeyboard.hideLayout();
            return true;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }

    @Override
    public void onGetChatRecordListSuccess(List<ChatRecordInfo> list) {
        /*if(infoList != null){
            for (int i = 0; i < infoList.size(); i++) {
                String username = infoList.get(i).getUsername();
                AccountInfo info = accountInfoMap.get(username);
                if(info == null){
                    accountInfoMap.put(username,infoList.get(i));
                }
            }
        }*/

        if (list == null || list.size() == 0) {
        } else {
            Collections.reverse(list);
            if (ptrLayout.isRefreshing()) {
                List<ChatRecordInfo> tmpList = new ArrayList<>();

                tmpList.addAll(list);
                tmpList.addAll(adapter.getData());

                adapter.setNewData(tmpList);
            } else {
                adapter.setNewData(list);
                scrollToBottom(true);
            }
        }
        ptrLayout.refreshComplete();
    }

    @Override
    public void onGetChatRecordListFail(String error) {

    }

    @Override
    public void onSaveSessionSuccess(ChatSession session) {
        LogUtil.d("chatSession:"+session);
        chatSession = session;

        chatUserInfo.setSessionKey(session.getSessionKey());
        chatUserInfo.setSessionId(session.getId());

        getSessionInfo(session.getSessionKey(),session.getId());
    }

    @Override
    public void onSaveSessionFail(String error) {

    }


    @Override
    public void onMarkReadSuccess(Object session) {
        //RxBus.getDefault().post(new MarkReadEvent(true,chatUserInfo.getSessionId()));
    }

    @Override
    public void onMarkReadFail(String error) {

    }

    @Override
    public void onUploadChatRecordSuccess(Object object, int pos) {
        setMsgSendSuccess(pos, true);
    }

    @Override
    public void onUploadChatRecordFail(String error, int pos) {
        setMsgSendSuccess(pos, false);
    }

    @Override
    public void onUploadChatFileSuccess(Object object, int pos) {
        setMsgSendSuccess(pos, true);
    }

    @Override
    public void onUploadChatFileFail(String error, int pos) {
        setMsgSendSuccess(pos, false);
    }

    private void setMsgSendSuccess(int position, boolean isSuccess) {
        if (adapter.getData().size() >= position) {
            MsgStatus status = null;
            if (isSuccess) {
                status = MsgStatus.send_success;
            } else {
                status = MsgStatus.send_fail;
            }

            adapter.getItem(position-1).setMsgStatus(status);
            adapter.notifyItemChanged(position-1);
        }
    }
}


