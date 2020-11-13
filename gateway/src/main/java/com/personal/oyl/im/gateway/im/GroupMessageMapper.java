package com.personal.oyl.im.gateway.im;

import java.util.List;

public interface GroupMessageMapper {
    GroupMessage queryByMsgId(String msgId);

    GroupMessage queryByKey(long id);

    void insert(GroupMessage message);

    void insertRead(String groupId, String msgId, String receiverId);

    int onRead(List<Long> list);

    Long queryReadIdByKey(String receiver, String msgId);

    List<Long> queryReadIds(String receiver, String group);
}
