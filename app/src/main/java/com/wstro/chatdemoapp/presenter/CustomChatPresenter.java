package com.wstro.chatdemoapp.presenter;

import android.content.Context;

import com.wstro.app.common.mvp.BaseActPresenter;
import com.wstro.app.common.utils.FileUtils;
import com.wstro.chatdemoapp.model.ChatRecordInfo;
import com.wstro.chatdemoapp.model.MsgStatus;
import com.wstro.chatdemoapp.model.MsgType;
import com.wstro.chatdemoapp.view.CustomChatView;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CustomChatPresenter extends BaseActPresenter<CustomChatView> {
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static final String CHAT_TYPE = "PRIVATE";
    public static final String CHAT_CUSTOM_TYPE = "CUSTOMER_SERVICE";

    public static final int PAGE_SIZE = 10;

    private int curPage = 1;
    private boolean hasMoreData = true;

    private String curDate;

    //DataManager dataManager;

    public CustomChatPresenter() {
        //dataManager = DataManager.get();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        curDate = df.format(new Date());
    }

    public boolean isHasMoreData() {
        return hasMoreData;
    }

    public String getCurDate() {
        return curDate;
    }

    public void getChatRecordList(String sessionKey){

    }


    public void getCustomer(String tenantId){

    }


    public void getCustomerByGroupId(String groupId){

    }


    public void saveSession(String targetUserName,String targetNickName,String sessionType){

    }

    public void markIMRead(int sessionId){

    }


    /*public void uploadChatRecord(ChatMsgReq req, final int pos){

    }


    public void updateChatFile(final ChatMsgReq req, File file,final String dirPath, final int position) {
    }*/


    public String getCacheDirPath(Context context){
        String dirPath = FileUtils.getAppDefaultCacheDir(context);
        File dirFile = new File(dirPath);
        if(!dirFile.exists()){
            dirFile.mkdirs();
        }

        return dirPath;
    }

    public ChatRecordInfo buildChatRecordInfo(MsgType type, String username, String content, boolean isReceive) {
        ChatRecordInfo userChatRecord = new ChatRecordInfo();

        userChatRecord.setType(type.getName());
        userChatRecord.setData(content);

        userChatRecord.setCreateDate(dateFormat.format(new Date()));

        if (isReceive) {
            userChatRecord.setSenderId(username);
            userChatRecord.setMsgStatus(MsgStatus.receive_success);
        } else {
            userChatRecord.setMsgStatus(MsgStatus.send_success);
            //userChatRecord.setSenderId(AppData.get().getUserInfo().getLoginName());
            userChatRecord.setSenderId("1");
        }
        return userChatRecord;
    }

    /*public ChatRecordInfo buildChatRecordInfo(Message msg, String username, boolean isReceive) {
        String content = JMessageConverter.convertData(msg);
        MsgType msgType = JMessageConverter.convertMsgType(msg.getContentType());
        return buildChatRecordInfo(msgType,username,content,isReceive);
    }*/

}