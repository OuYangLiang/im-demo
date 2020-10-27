package com.personal.oyl.im.gateway.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author OuYang Liang
 * @since 2020-10-27
 */
@RestController
public class DefaultController {

    @RequestMapping("/hello")
    public String hello() {
        return "Hello World!!!!!!";
    }
}
