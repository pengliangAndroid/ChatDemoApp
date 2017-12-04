package com.wstro.chatdemoapp.model;

/**
 * ClassName: ChatRecordInfo
 * Function:
 * Date:     2017/10/10 0010 15:58
 *
 * @author Administrator
 * @see
 */
public class ChatRecordInfo {

    /**
     * createDate : 2017-09-20 16:48:52
     * data : {"addressLabel":"广东省深圳市南山区朗山路28号5楼","delFlag":null,"latitude":"39.923568","longitude":"116.31142","zoom":123}
     * delFlag : null
     * id : 44
     * receiverId : 246810
     * senderId : 13579
     * sessionKey : 13579$246810
     * sessionType : PRIVATE
     * type : LOCATION
     * updateDate : null
     */

    private String createDate;
    private Object delFlag;
    private int id;
    private String receiverId;
    private String senderId;
    private String sessionKey;
    private String sessionType;
    private String type;
    private Object updateDate;
    private String data;

    private MsgStatus msgStatus;
    private boolean isTip;

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public Object getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Object delFlag) {
        this.delFlag = delFlag;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getSessionKey() {
        return sessionKey;
    }

    public void setSessionKey(String sessionKey) {
        this.sessionKey = sessionKey;
    }

    public String getSessionType() {
        return sessionType;
    }

    public void setSessionType(String sessionType) {
        this.sessionType = sessionType;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Object getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Object updateDate) {
        this.updateDate = updateDate;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public MsgStatus getMsgStatus() {
        return msgStatus;
    }

    public void setMsgStatus(MsgStatus msgStatus) {
        this.msgStatus = msgStatus;
    }

    public boolean isTip() {
        return isTip;
    }

    public void setTip(boolean tip) {
        isTip = tip;
    }
}
