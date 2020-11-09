package com.personal.oyl.im.gateway.im;

public enum MessageType {
    text(0),
    read_notice(1),
    read_reply(2),
    ;

    private int code;

    MessageType(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static MessageType init(int code) {
        for (MessageType item : MessageType.values()) {
            if (item.getCode() == code) {
                return item;
            }
        }

        throw new IllegalArgumentException();
    }
}
