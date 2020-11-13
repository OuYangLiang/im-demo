package com.personal.oyl.im.gateway.controller;

import com.personal.oyl.im.gateway.im.ImService;
import com.personal.oyl.im.gateway.model.message.GroupTextMessage;
import com.personal.oyl.im.gateway.model.message.Protocol;
import com.personal.oyl.im.gateway.user.User;
import com.personal.oyl.im.gateway.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * @author OuYang Liang
 * @since 2020-10-27
 */
@RestController
public class DefaultController {

    private static Map<String, String> check = new ConcurrentHashMap<>();

    private UserService userService;
    private ImService imService;

    @RequestMapping("/hello")
    public String hello() {
        return "Hello World!!!!!!";
    }

    @RequestMapping(value = "/login", method = {RequestMethod.POST})
    public WebResult<?> login(@RequestBody LoginParam param) {

        /*if (!"OYL".equalsIgnoreCase(param.getUserId())) {
            return WebResult.fail("Error001", "账号不存在");
        }*/

        User user = userService.queryUser(param.getUserId());

        if (null == user) {
            return WebResult.fail("Error001", "账号不存在");
        }

        if (!"123".equalsIgnoreCase(param.getPwd())) {
            return WebResult.fail("Error002", "密码不正确");
        }

        String sec = UUID.randomUUID().toString();
        check.put(param.getUserId().toUpperCase(), sec);

        return WebResult.success(sec);
    }

    @RequestMapping(value = "/check", method = {RequestMethod.POST})
    public WebResult<?> check(@RequestBody CheckParam param) {
        if (!check.containsKey(param.getLoginId().toUpperCase())) {
            return WebResult.fail("Error003", "非法访问");
        }

        if (!check.get(param.getLoginId().toUpperCase()).equalsIgnoreCase(param.getSec())) {
            return WebResult.fail("Error003", "非法访问");
        }

        User user = userService.queryUser(param.getLoginId());

        if (null == user) {
            return WebResult.fail("Error001", "账号不存在");
        }

        return WebResult.success(UserDto.from(user));
    }

    /*@RequestMapping("/onlineUsers")
    public WebResult<List<String>> onlineUsers() {
        return WebResult.success(connectionMgr.onlineUsers());
    }*/

    @RequestMapping("/queryFriends")
    public WebResult<List<UserDto>> queryFriends(@RequestBody FriendsQueryParam param) {
        List<User> users = userService.queryFriends(param.getLoginId());
        if (null == users || users.isEmpty()) {
            return WebResult.success(Collections.emptyList());
        }

        return WebResult.success(users.stream().map(UserDto::from).collect(Collectors.toList()));
    }

    @RequestMapping("/queryGroups")
    public WebResult<List<GroupDto>> queryGroups(@RequestBody GroupQueryParam param) {
        String groupId = "group";
        GroupDto result = new GroupDto();
        result.setGroupId(groupId);
        result.setGroupName("满帮CRM");
        result.setIcon("group.jpg");

        List<User> users = userService.queryUserByGroup(groupId);
        List<UserDto> members = (null == users || users.isEmpty()) ? Collections.emptyList() :
                users.stream().filter((u)-> !u.getLoginId().equalsIgnoreCase(param.getLoginId())).map(UserDto::from).collect(Collectors.toList());
        result.setMembers(members);

        return WebResult.success(Collections.singletonList(result));
    }

    @RequestMapping("/queryChat")
    public WebResult<List<Protocol>> queryChat(@RequestBody ChatQueryParam param) {
        return WebResult.success(imService.queryLastN(param.getLoginId1(), param.getLoginId2(), param.getN()));
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setImService(ImService imService) {
        this.imService = imService;
    }

}
