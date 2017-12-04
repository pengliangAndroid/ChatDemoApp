package com.wstro.chatdemoapp.model;

public enum MsgType {
    text("TEXT"),
    image("IMAGE"),
    voice("VOICE"),
    location("LOCATION"),
    video("VIDEO"),
    custom("CUSTOME"),
    file("FILE");

    String name;

    MsgType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static MsgType getMsgType(String name){
        if(name == null || "".equals(name))
            return null;

        MsgType value = null;
        MsgType[] values = MsgType.values();
        for (int i = 0; i < values.length; i++) {
            if(values[i].getName().equals(name)){
                value = values[i];
                break;
            }
        }

        return value;
    }
}