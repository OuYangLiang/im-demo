package com.personal.oyl.im.gateway.im;

public enum MessageStatus {
    initial(0),
    delivery(1),
    read(2);

    private int code;

    MessageStatus(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static MessageStatus init(int code) {
        for (MessageStatus item : MessageStatus.values()) {
            if (item.getCode() == code) {
                return item;
            }
        }

        throw new IllegalArgumentException();
    }
}
