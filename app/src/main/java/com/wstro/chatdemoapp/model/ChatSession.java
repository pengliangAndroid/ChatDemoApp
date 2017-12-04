package com.wstro.chatdemoapp.model;

/**
 * ClassName: ChatSession
 * Function:
 * Date:     2017/10/10 0010 16:08
 *
 * @author Administrator
 * @see
 */
public class ChatSession {


    /**
     * extra : {"delFlag":null,"fileId":"http://wstro.eicp.net:94/ws/img/view/925189624429678592","groupId":1,"id":null,"kefuLogo":"http://wstro.eicp.net:94/ws/img/view/925189624429678592","kefuName":"华龄客服","lastRefreshTime":1509439552315,"nickname":"zslong","online":true,"tenantId":null,"username":"13268266380"}
     * group : null
     * id : 49
     * lastDeleteDate : 1970-01-01 08:00:00
     * lastMessage : {"content":null,"createDate":"2017-10-31 16:21:28","data":"模图图","delFlag":1,"extra":"1","id":null,"receiverId":"13268266380","senderId":"18575599818","sessionKey":null,"sessionType":"CUSTOMER_SERVICE","type":"TEXT","updateDate":null}
     * lastReadDate : 1970-01-01 08:00:00
     * sessionKey : 3982a2c03138fe313060f420bc8c09fd
     * type : CUSTOMER_SERVICE
     * unread : 10
     * user : {"address":null,"avatar":null,"birthday":null,"createDate":null,"delFlag":null,"gender":null,"genderView":"未知","id":64,"nickname":"13268266380","password":null,"signature":null,"tenantId":null,"updateDate":null,"username":"13268266380"}
     */

    private ExtraBean extra;
    private Object group;
    private int id;
    private String lastDeleteDate;
    private ChatRecordInfo lastMessage;
    private String lastReadDate;
    private String sessionKey;
    private String type;
    private int unread;
    private UserBean user;

    public ExtraBean getExtra() {
        return extra;
    }

    public void setExtra(ExtraBean extra) {
        this.extra = extra;
    }

    public Object getGroup() {
        return group;
    }

    public void setGroup(Object group) {
        this.group = group;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLastDeleteDate() {
        return lastDeleteDate;
    }

    public void setLastDeleteDate(String lastDeleteDate) {
        this.lastDeleteDate = lastDeleteDate;
    }

    public ChatRecordInfo getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(ChatRecordInfo lastMessage) {
        this.lastMessage = lastMessage;
    }

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public String getLastReadDate() {
        return lastReadDate;
    }

    public void setLastReadDate(String lastReadDate) {
        this.lastReadDate = lastReadDate;
    }

    public String getSessionKey() {
        return sessionKey;
    }

    public void setSessionKey(String sessionKey) {
        this.sessionKey = sessionKey;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getUnread() {
        return unread;
    }

    public void setUnread(int unread) {
        this.unread = unread;
    }


    public static class ExtraBean {
        /**
         * delFlag : null
         * fileId : http://wstro.eicp.net:94/ws/img/view/925189624429678592
         * groupId : 1
         * id : null
         * kefuLogo : http://wstro.eicp.net:94/ws/img/view/925189624429678592
         * kefuName : 华龄客服
         * lastRefreshTime : 1509439552315
         * nickname : zslong
         * online : true
         * tenantId : null
         * username : 13268266380
         */

        private Object delFlag;
        private String fileId;
        private int groupId;
        private Object id;
        private String kefuLogo;
        private String kefuName;
        private long lastRefreshTime;
        private String nickname;
        private boolean online;
        private Object tenantId;
        private String username;

        public Object getDelFlag() {
            return delFlag;
        }

        public void setDelFlag(Object delFlag) {
            this.delFlag = delFlag;
        }

        public String getFileId() {
            return fileId;
        }

        public void setFileId(String fileId) {
            this.fileId = fileId;
        }

        public int getGroupId() {
            return groupId;
        }

        public void setGroupId(int groupId) {
            this.groupId = groupId;
        }

        public Object getId() {
            return id;
        }

        public void setId(Object id) {
            this.id = id;
        }

        public String getKefuLogo() {
            return kefuLogo;
        }

        public void setKefuLogo(String kefuLogo) {
            this.kefuLogo = kefuLogo;
        }

        public String getKefuName() {
            return kefuName;
        }

        public void setKefuName(String kefuName) {
            this.kefuName = kefuName;
        }

        public long getLastRefreshTime() {
            return lastRefreshTime;
        }

        public void setLastRefreshTime(long lastRefreshTime) {
            this.lastRefreshTime = lastRefreshTime;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public boolean isOnline() {
            return online;
        }

        public void setOnline(boolean online) {
            this.online = online;
        }

        public Object getTenantId() {
            return tenantId;
        }

        public void setTenantId(Object tenantId) {
            this.tenantId = tenantId;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }
    }

    public static class UserBean{

        /**
         * address : null
         * avatar : null
         * birthday : null
         * createDate : null
         * delFlag : null
         * gender : null
         * genderView : 未知
         * id : 64
         * nickname : 13268266380
         * password : null
         * signature : null
         * tenantId : null
         * updateDate : null
         * username : 13268266380
         */

        private Object address;
        private String avatar;
        private Object birthday;
        private Object createDate;
        private Object delFlag;
        private Object gender;
        private String genderView;
        private int id;
        private String nickname;
        private Object password;
        private Object signature;
        private Object tenantId;
        private Object updateDate;
        private String username;

        public Object getAddress() {
            return address;
        }

        public void setAddress(Object address) {
            this.address = address;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public Object getBirthday() {
            return birthday;
        }

        public void setBirthday(Object birthday) {
            this.birthday = birthday;
        }

        public Object getCreateDate() {
            return createDate;
        }

        public void setCreateDate(Object createDate) {
            this.createDate = createDate;
        }

        public Object getDelFlag() {
            return delFlag;
        }

        public void setDelFlag(Object delFlag) {
            this.delFlag = delFlag;
        }

        public Object getGender() {
            return gender;
        }

        public void setGender(Object gender) {
            this.gender = gender;
        }

        public String getGenderView() {
            return genderView;
        }

        public void setGenderView(String genderView) {
            this.genderView = genderView;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public Object getPassword() {
            return password;
        }

        public void setPassword(Object password) {
            this.password = password;
        }

        public Object getSignature() {
            return signature;
        }

        public void setSignature(Object signature) {
            this.signature = signature;
        }

        public Object getTenantId() {
            return tenantId;
        }

        public void setTenantId(Object tenantId) {
            this.tenantId = tenantId;
        }

        public Object getUpdateDate() {
            return updateDate;
        }

        public void setUpdateDate(Object updateDate) {
            this.updateDate = updateDate;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }
    }
}
