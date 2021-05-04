package com.hopkin.wiki.controller;

import com.hopkin.wiki.domain.Test;
import com.hopkin.wiki.service.TestService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class TestController {

    //注入自定义配置项
    @Value("${test.hello:TEST}")
    private String testHello;

    @Resource
    private TestService testService;

    @PostMapping("/hello/post")
    public String helloPost(String name){
        return "Hello World! Post."+name;
    }

    @GetMapping("/test/list")
    public List<Test> list(){
        System.out.println("list");
        return testService.list();
    }
}
