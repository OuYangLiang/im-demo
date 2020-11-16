package com.personal.oyl.im.gateway.im;

import java.util.List;

public interface GroupMessageMapper {
    GroupMessage queryByMsgId(String msgId);

    List<GroupMessage> queryByMsgIds(List<String> list);

    GroupMessage queryByKey(long id);

    void insert(GroupMessage message);

    void insertRead(String groupId, String msgId, String receiverId);

    int onRead(List<Long> list);

    GroupMessageRead queryGroupMessageReadByKey(String receiver, String msgId);

    List<GroupMessageRead> queryUnreadGroupMessageRead(String receiver, String group);
}
