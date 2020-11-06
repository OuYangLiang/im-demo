package com.personal.oyl.im.gateway.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author OuYang Liang
 * @since 2020-11-05
 */
@Component
public class UserServiceImpl implements UserService {

    private UserMapper userMapper;

    @Override
    public List<User> queryFriends(String loginId) {
        List<User> result = userMapper.queryAll();

        if (null == result || result.isEmpty()) {
            return Collections.emptyList();
        }

        return result.stream().filter((u) -> !u.getLoginId().equalsIgnoreCase(loginId)).collect(Collectors.toList());
    }

    @Override
    public User queryUser(String loginId) {
        return userMapper.queryUser(loginId);
    }

    @Autowired
    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }
}
