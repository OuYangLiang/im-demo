package com.personal.oyl.im.gateway.controller;

import com.personal.oyl.im.gateway.model.ConnectionMgr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author OuYang Liang
 * @since 2020-10-27
 */
@RestController
public class DefaultController {

    private static Map<String, String> check = new ConcurrentHashMap<>();

    @Autowired
    private ConnectionMgr connectionMgr;

    @RequestMapping("/hello")
    public String hello() {
        return "Hello World!!!!!!";
    }

    @RequestMapping(value = "/login", method = {RequestMethod.POST})
    public WebResult<?> login(@RequestBody LoginParam param) {

        if (!"OYL".equalsIgnoreCase(param.getUserId())) {
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
        if (!check.containsKey(param.getUserId().toUpperCase())) {
            return WebResult.fail("Error003", "非法访问");
        }

        if (!check.get(param.getUserId().toUpperCase()).equalsIgnoreCase(param.getSec())) {
            return WebResult.fail("Error003", "非法访问");
        }

        return WebResult.success();
    }

    @RequestMapping("/onlineUsers")
    public WebResult<List<String>> onlineUsers() {
        return WebResult.success(connectionMgr.onlineUsers());
    }
}
