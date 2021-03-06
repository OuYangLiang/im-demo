package com.personal.oyl.im.gateway.model.message;

/**
 * @author OuYang Liang
 * @since 2020-09-28
 */
public enum ProtocolType {
    heartbeat,
    heartbeat_ack,
    connect,
    connect_ack,
    online,
    online_ack,
    offline,
    offline_ack,
    business,
    business_ack;

    public ProtocolType getAckType() {
        if (ProtocolType.heartbeat.equals(this)) {
            return ProtocolType.heartbeat_ack;
        }

        if (ProtocolType.connect.equals(this)) {
            return ProtocolType.connect_ack;
        }

        if (ProtocolType.business.equals(this)) {
            return ProtocolType.business_ack;
        }

        if (ProtocolType.online.equals(this)) {
            return ProtocolType.online_ack;
        }

        if (ProtocolType.offline.equals(this)) {
            return ProtocolType.offline_ack;
        }

        throw new IllegalArgumentException();
    }
}
