package com.wstro.chatdemoapp.model;

/**
 * ClassName: ChatType
 * Function:
 * Date:     2017/10/31 0031 16:48
 *
 * @author pengl
 * @see
 */
public enum ChatType {
    PRIVATE("PRIVATE"),
    CUSTOM_SERVICE("CUSTOMER_SERVICE");

    String type;

    ChatType(String type){
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public static ChatType typeOf(String typeName){
        ChatType[] values = ChatType.values();
        for (int i = 0; i < values.length; i++) {
            if(values[i].getType().equals(typeName)){
                return values[i];
            }
        }

        return null;
    }
}
