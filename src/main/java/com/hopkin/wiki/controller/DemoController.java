package com.hopkin.wiki.controller;

import com.hopkin.wiki.domain.Demo;
import com.hopkin.wiki.resp.CommonResp;
import com.hopkin.wiki.service.DemoService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/demo")
public class DemoController {

    //注入自定义配置项
    @Value("${demo.hello:TEST}")
    private String demoHello;

    @Resource
    private DemoService demoService;

    @PostMapping("/post")
    public String helloPost(String name){
        return "Hello World! Post."+name;
    }

    @GetMapping("/list")
    public CommonResp list(){
        CommonResp<List<Demo>> resp =  new CommonResp<>();
        List<Demo> list = demoService.list();
        resp.setContent(list);
        return resp;
    }
}
