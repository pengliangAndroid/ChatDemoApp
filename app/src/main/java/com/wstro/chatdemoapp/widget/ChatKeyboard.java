/*
 * Copyright (c) 2015, 张涛.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.wstro.chatdemoapp.widget;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.wstro.app.common.utils.LogUtil;
import com.wstro.chatdemoapp.ChatFuncFragment;
import com.wstro.chatdemoapp.R;

import io.github.rockerhieu.emojicon.CustomEmojiFragment;
import io.github.rockerhieu.emojicon.EmojiconEditText;
import io.github.rockerhieu.emojicon.emoji.Emojicon;


public class ChatKeyboard extends RelativeLayout implements
        SoftKeyboardStateHelper.SoftKeyboardStateListener,View.OnClickListener {

    public static final int LAYOUT_TYPE_HIDE = 0;
    public static final int LAYOUT_TYPE_EMOJI = 1;
    public static final int LAYOUT_TYPE_MORE = 2;
    public static final int LAYOUT_TYPE_IMAGE = 3;

    public static final int TYPE_TEXT = 10;
    public static final int TYPE_EMOJI = 11;

    private int layoutType = LAYOUT_TYPE_HIDE;

    /**
     * 最上层输入框
     */
    private EmojiconEditText messageEdt;
    private CheckBox faceChb;
    private CheckBox moreChb;
    private Button sendButton;

    private Fragment emojiFragment, toolFragment;
    
    /**
     * 底部面板布局
     */
    private RelativeLayout toolLayout;

    //private List<String> dataList;
    private boolean lastEmptyFlag = true;

    private Context context;
    private OnOperationListener listener;

    private SoftKeyboardStateHelper keyboardStateHelper;

    public ChatKeyboard(Context context) {
        super(context);
        init(context);
    }

    public ChatKeyboard(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ChatKeyboard(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context) {
        this.context = context;
        View root = View.inflate(context, R.layout.chat_tool_bar, null);
        addView(root);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        initKeyboard();
        initViews();
    }

    private void initKeyboard() {
        keyboardStateHelper = new SoftKeyboardStateHelper(((Activity) getContext())
                .getWindow().getDecorView());
        keyboardStateHelper.addSoftKeyboardStateListener(this);
    }

    private void initViews() {
        messageEdt = (EmojiconEditText) findViewById(R.id.toolbox_et_message);
        sendButton = (Button) findViewById(R.id.toolbox_btn_send);
        faceChb = (CheckBox) findViewById(R.id.toolbox_btn_face);
        moreChb = (CheckBox) findViewById(R.id.toolbox_btn_more);
        toolLayout = (RelativeLayout) findViewById(R.id.toolbox_layout_face);

        sendButton.setOnClickListener(this);

        faceChb.setOnClickListener(this);
        moreChb.setOnClickListener(this);
        messageEdt.setOnClickListener(this);

        messageEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.toString().length() != 0){
                    if(lastEmptyFlag) {
                        sendButton.setBackgroundResource(R.drawable.btn_send_radius_selector);
                        lastEmptyFlag = false;
                    }
                }else{
                    lastEmptyFlag = true;
                    sendButton.setBackgroundResource(R.drawable.btn_not_send_shape);
                }
            }
        });

        FragmentTransaction transaction = ((FragmentActivity) getContext()).
                getSupportFragmentManager().beginTransaction();
        if (emojiFragment == null) {
            emojiFragment = CustomEmojiFragment.newInstance(Emojicon.TYPE_PEOPLE);
            transaction.add(R.id.fl_content, emojiFragment);
        }
        transaction.commit();
    }


    public void setBottomBarVisibility(int visibility) {
        ((CustomEmojiFragment)emojiFragment).setBottomTabVisible(visibility);
    }

    public void setFaceBtnInVisible(){
        findViewById(R.id.toolbox_btn_face).setVisibility(View.GONE);
    }

    public void setInputEnable(boolean enable) {
        messageEdt.setEnabled(enable);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.toolbox_btn_send:
                checkNotNull();

                String content = messageEdt.getText().toString();
                if(!content.trim().equals("")) {
                    listener.send(content);
                    messageEdt.setText(null);
                }
                break;

            case R.id.toolbox_et_message:
                hideLayout();
                break;

            case R.id.toolbox_btn_face:
                changeLayout(LAYOUT_TYPE_EMOJI);
                break;

            case R.id.toolbox_btn_more:
                changeLayout(LAYOUT_TYPE_MORE);
                break;
        }

    }

    private void changeLayout(int type) {
        if (isShow() && type == layoutType) {
            hideLayout();
            showKeyboard(context);

            if(listener != null)
                listener.onSoftKeyboardOpened();
        } else {
            createLayout(type);
            showLayout();
            faceChb.setChecked(layoutType == LAYOUT_TYPE_EMOJI);
            moreChb.setChecked(layoutType == LAYOUT_TYPE_MORE);
        }
    }

    private void checkNotNull() {
        if(listener == null)
            throw new IllegalArgumentException("listener is null,have to call setOnOperationListener");
    }


    /**
     * 将所有的Fragment都置为隐藏状态。
     *
     * @param transaction 用于对Fragment执行操作的事务
     */
    private void hideFragments(FragmentTransaction transaction) {
        if (emojiFragment != null) {
            transaction.hide(emojiFragment);
        }
        if (toolFragment != null) {
            transaction.hide(toolFragment);
        }
    }

    private void createLayout(int type) {
        layoutType = type;
        LogUtil.d("changeLayout :"+ type);
        FragmentTransaction transaction = ((FragmentActivity) getContext()).
                getSupportFragmentManager().beginTransaction();
        hideFragments(transaction);

        if(type == LAYOUT_TYPE_EMOJI){
            if (emojiFragment == null) {
                CustomEmojiFragment.newInstance(Emojicon.TYPE_PEOPLE);
                transaction.add(R.id.fl_content, emojiFragment);
            } else {
                transaction.show(emojiFragment);
            }

        }else if(type == LAYOUT_TYPE_MORE){
           if (toolFragment == null) {
               toolFragment = ChatFuncFragment.newInstance();
                transaction.add(R.id.fl_content, toolFragment);
            } else {
                transaction.show(toolFragment);
            }
        }

        transaction.commit();
    }

    @Override
    public void onSoftKeyboardOpened(int keyboardHeightInPx) {
        hideLayout();

        if(listener != null)
            listener.onSoftKeyboardOpened();
    }

    @Override
    public void onSoftKeyboardClosed() {
        if(listener != null)
            listener.onSoftKeyboardClosed();
    }


    public EditText getEditText() {
        return messageEdt;
    }

    public void showLayout() {
        hideKeyboard(this.context);
        // 延迟一会，让键盘先隐藏再显示表情键盘，否则会有一瞬间表情键盘和软键盘同时显示
        postDelayed(new Runnable() {
            @Override
            public void run() {
                toolLayout.setVisibility(View.VISIBLE);
                if(listener != null)
                    listener.onSoftKeyboardOpened();
            }
        }, 50);
    }


    public boolean isShow() {
        return toolLayout.getVisibility() == VISIBLE;
    }

    public void hideLayout() {
        toolLayout.setVisibility(View.GONE);
        if (faceChb.isChecked()) {
            faceChb.setChecked(false);
        }
        if (moreChb.isChecked()) {
            moreChb.setChecked(false);
        }
    }

    /**
     * 隐藏软键盘
     */
    public void hideKeyboard(Context context) {
        Activity activity = (Activity) context;
        if (activity != null) {
            InputMethodManager imm = (InputMethodManager) activity
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm.isActive() && activity.getCurrentFocus() != null) {
                imm.hideSoftInputFromWindow(activity.getCurrentFocus()
                        .getWindowToken(), 0);
            }
        }
    }

    /**
     * 显示软键盘
     */
    public static void showKeyboard(Context context) {
        Activity activity = (Activity) context;
        if (activity != null) {
            InputMethodManager imm = (InputMethodManager) activity
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInputFromInputMethod(activity.getCurrentFocus()
                    .getWindowToken(), 0);
            imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }


    public OnOperationListener getOnOperationListener() {
        return listener;
    }

    public void setOnOperationListener(OnOperationListener onOperationListener) {
        this.listener = onOperationListener;
        //adapter.setOnOperationListener(onOperationListener);
    }

    public interface OnOperationListener {
        void send(String content);

        void onSoftKeyboardOpened();

        void onSoftKeyboardClosed();
    }
}
