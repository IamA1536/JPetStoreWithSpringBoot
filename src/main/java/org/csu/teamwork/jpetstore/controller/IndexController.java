package org.csu.teamwork.jpetstore.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @email 1694522669@qq.com
 * @author: A
 * @date: 2019/6/11 1:16
 */
@Controller
public class IndexController {
    @GetMapping("/")
    public String viewIndex() {
        return "index";
    }

    @GetMapping("/help")
    public String help() {
        return "help";
    }
}
