package com.personal.oyl.im.gateway.user;

import java.util.List;

/**
 * @author OuYang Liang
 * @since 2020-11-05
 */
public interface UserService {
    List<User> queryFriends(String loginId);
}