package com.wstro.chatdemoapp.model;

import org.greenrobot.greendao.converter.PropertyConverter;

public enum MsgStatus {
    created,
    send_success,
    send_fail,
    send_going,
    send_draft,
    receive_success,
    receive_going,
    receive_fail;


    public static class MsgStatusConverter implements PropertyConverter<MsgStatus, Integer> {
        @Override
        public MsgStatus convertToEntityProperty(Integer databaseValue) {
            if (databaseValue == null) {
                return null;
            }
            for (MsgStatus role : MsgStatus.values()) {
                if (role.ordinal() == databaseValue) {
                    return role;
                }
            }
            return MsgStatus.created;
        }

        @Override
        public Integer convertToDatabaseValue(MsgStatus entityProperty) {
            return entityProperty == null ? null : entityProperty.ordinal();
        }
    }
}