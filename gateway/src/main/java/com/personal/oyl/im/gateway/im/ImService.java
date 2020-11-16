package com.personal.oyl.im.gateway.im;

import com.personal.oyl.im.gateway.model.message.GroupTextMessage;
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
     * 群聊文本消息
     *
     * @param msgId 客户端消息ID
     * @param textMessage 群聊文本消息对象
     */
    void onGroupTextMessage(String msgId, GroupTextMessage textMessage);

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
     * 查询last N 群聊消息列表
     *
     * @param sender 当前用户ID
     * @param group 群聊ID
     * @param n 消息条数
     * @return 以协议列表形式返回
     */
    List<Protocol> queryLastGroupN(String sender, String group, int n);

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

    /**
     * 置已读状态
     *
     * @param group 群ID
     * @param receiver 接收方ID
     */
    void clearGroupUnRead(String group, String receiver);

    /**
     * 置已读状态
     *
     * @param group 群ID
     * @param receiver 接收方ID
     * @param msgId 消息ID
     */
    void clearGroupUnRead(String group, String receiver, String msgId);

    //void onConnected(String loginId);
}
