package com.personal.oyl.im.gateway.user;

import java.util.List;

public interface UserMapper {
    List<User> queryAll();

    User queryUser(String loginId);
}
