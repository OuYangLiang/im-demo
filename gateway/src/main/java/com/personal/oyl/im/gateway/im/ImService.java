package com.personal.oyl.im.gateway.im;

import com.personal.oyl.im.gateway.model.Protocol;
import com.personal.oyl.im.gateway.model.TextMessage;

import java.util.List;

/**
 * @author OuYang Liang
 * @since 2020-11-01
 */
public interface ImService {
    void onTextMessage(String msgId, TextMessage textMessage);

    void onAck(String msgId);

    List<Protocol> queryLastN(String loginId1, String loginId2, int n);

    //void onConnected(String loginId);
}
