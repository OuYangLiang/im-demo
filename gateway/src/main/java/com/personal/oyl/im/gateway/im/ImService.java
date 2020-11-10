package com.personal.oyl.im.gateway.im;

import com.personal.oyl.im.gateway.model.message.Protocol;
import com.personal.oyl.im.gateway.model.message.TextMessage;

import java.util.List;

/**
 * @author OuYang Liang
 * @since 2020-11-01
 */
public interface ImService {
    /**
     * 文本消息
     *
     * @param msgId 客户端消息ID
     * @param textMessage 文本消息对象
     */
    void onTextMessage(String msgId, TextMessage textMessage);

    /**
     * 消息确认
     *
     * @param msgId 消息ID
     */
    void onAck(String msgId);

    /**
     * 查询last N 消息列表
     *
     * @param loginId1 发送方
     * @param loginId2 接收方
     * @param n 消息条数
     * @return 以协议列表形式返回
     */
    List<Protocol> queryLastN(String loginId1, String loginId2, int n);

    /**
     * 置已读状态
     *
     * @param receiver 接收方ID
     * @param sender 发送方ID
     */
    void clearUnread(String receiver, String sender);

    /**
     * 置已读状态
     *
     * @param receiver 接收方ID
     * @param sender 发送方ID
     * @param msgId 消息ID
     */
    void clearUnRead(String receiver, String sender, String msgId);

    //void onConnected(String loginId);
}
