package com.hopkin.wiki.controller;

import com.hopkin.wiki.req.EbookReq;
import com.hopkin.wiki.resp.CommonResp;
import com.hopkin.wiki.resp.EbookResp;
import com.hopkin.wiki.service.EbookService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/ebook")
public class EbookController {

    //注入自定义配置项
    @Value("${ebook.hello:TEST}")
    private String ebookHello;

    @Resource
    private EbookService ebookService;

    @PostMapping("/post")
    public String helloPost(String name){
        return "Hello World! Post."+name;
    }

    @GetMapping("/list")
    public CommonResp list(EbookReq req){
        CommonResp<List<EbookResp>> resp =  new CommonResp<>();
        List<EbookResp> list = ebookService.list(req);
        resp.setContent(list);
        return resp;
    }
}
