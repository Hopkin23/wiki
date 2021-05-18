package com.hopkin.wiki.controller;

import com.hopkin.wiki.req.EbookQueryReq;
import com.hopkin.wiki.req.EbookSaveReq;
import com.hopkin.wiki.resp.CommonResp;
import com.hopkin.wiki.resp.PageResp;
import com.hopkin.wiki.service.EbookService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

@RestController
@RequestMapping("/ebook")
public class EbookController {

    //注入自定义配置项
    @Value("${ebook.hello:TEST}")
    private String ebookHello;

    @Resource
    private EbookService ebookService;

    @PostMapping("/save")
    public CommonResp save(@RequestBody EbookSaveReq req){
        CommonResp resp =  new CommonResp<>();
        ebookService.save(req);
        return resp;
    }

    @GetMapping("/list")
    public CommonResp list(@Valid EbookQueryReq req){
        CommonResp<PageResp> resp =  new CommonResp<>();
        PageResp list = ebookService.list(req);
        resp.setContent(list);
        return resp;
    }

    @DeleteMapping("/delete/{id}")
    public CommonResp delete(@PathVariable Long id){
        CommonResp resp =  new CommonResp<>();
        ebookService.delete(id);
        return resp;
    }
}
