package com.wstro.chatdemoapp.view;

import com.wstro.app.common.mvp.MvpView;
import com.wstro.chatdemoapp.model.ChatRecordInfo;
import com.wstro.chatdemoapp.model.ChatSession;

import java.util.List;


public interface CustomChatView extends MvpView {
    void onGetChatRecordListSuccess(List<ChatRecordInfo> list);

    void onGetChatRecordListFail(String error);

    void onSaveSessionSuccess(ChatSession session);

    void onSaveSessionFail(String error);

    /*void onGetCustomerSuccess(CustomerVO session);

    void onGetCustomerFail(String error);*/

    void onMarkReadSuccess(Object session);

    void onMarkReadFail(String error);

    void onUploadChatRecordSuccess(Object object, int pos);

    void onUploadChatRecordFail(String error, int pos);

    void onUploadChatFileSuccess(Object object, int pos);

    void onUploadChatFileFail(String error, int pos);
}