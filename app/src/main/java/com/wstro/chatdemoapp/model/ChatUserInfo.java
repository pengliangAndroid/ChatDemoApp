package com.wstro.chatdemoapp.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * ClassName: UserChatInfo
 * Function:
 * Date:     2017/4/26 11:10
 *
 * @author pengl
 * @see
 */
public class ChatUserInfo implements Parcelable {
    /**
     * 极光消息的登录用户名，由loginName组成
     */
    //private String userName;

    /**
     * 用户昵称，可以为空
     */
   // private String nickName;

    private String tenantId = "-1";
    private String groupId;

    private String targetUserName;

    private String targetNickName;

    private String targetUserAvatar = "";

    private String sessionKey;

    private String extra;

    private int sessionId;

    private boolean isCustomer;


    public boolean isCustomer() {
        return isCustomer;
    }

    public void setCustomer(boolean customer) {
        isCustomer = customer;
    }

    public String getTargetUserName() {
        return targetUserName;
    }

    public void setTargetUserName(String targetUserName) {
        this.targetUserName = targetUserName;
    }

    public String getTargetUserAvatar() {
        return targetUserAvatar;
    }

    public void setTargetUserAvatar(String targetUserAvatar) {
        this.targetUserAvatar = targetUserAvatar;
    }

    public String getSessionKey() {
        return sessionKey;
    }

    public void setSessionKey(String sessionKey) {
        this.sessionKey = sessionKey;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    public String getTargetNickName() {
        return targetNickName;
    }

    public void setTargetNickName(String targetNickName) {
        this.targetNickName = targetNickName;
    }


    public int getSessionId() {
        return sessionId;
    }

    public void setSessionId(int sessionId) {
        this.sessionId = sessionId;
    }


    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.tenantId);
        dest.writeString(this.groupId);
        dest.writeString(this.targetUserName);
        dest.writeString(this.targetNickName);
        dest.writeString(this.targetUserAvatar);
        dest.writeString(this.sessionKey);
        dest.writeString(this.extra);
        dest.writeInt(this.sessionId);
        dest.writeByte(this.isCustomer ? (byte) 1 : (byte) 0);
    }

    public ChatUserInfo() {
    }

    protected ChatUserInfo(Parcel in) {
        this.tenantId = in.readString();
        this.groupId = in.readString();
        this.targetUserName = in.readString();
        this.targetNickName = in.readString();
        this.targetUserAvatar = in.readString();
        this.sessionKey = in.readString();
        this.extra = in.readString();
        this.sessionId = in.readInt();
        this.isCustomer = in.readByte() != 0;
    }

    public static final Creator<ChatUserInfo> CREATOR = new Creator<ChatUserInfo>() {
        @Override
        public ChatUserInfo createFromParcel(Parcel source) {
            return new ChatUserInfo(source);
        }

        @Override
        public ChatUserInfo[] newArray(int size) {
            return new ChatUserInfo[size];
        }
    };
}
