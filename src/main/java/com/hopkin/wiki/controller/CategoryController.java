package com.hopkin.wiki.controller;

import com.hopkin.wiki.req.CategoryQueryReq;
import com.hopkin.wiki.req.CategorySaveReq;
import com.hopkin.wiki.resp.CategoryQueryResp;
import com.hopkin.wiki.resp.CommonResp;
import com.hopkin.wiki.resp.PageResp;
import com.hopkin.wiki.service.CategoryService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Resource
    private CategoryService categoryService;

    @GetMapping("/all")
    public CommonResp all(){
        CommonResp resp =  new CommonResp<>();
        List<CategoryQueryResp> list = categoryService.all();
        resp.setContent(list);
        return resp;
    }

    @PostMapping("/save")
    public CommonResp save(@Valid @RequestBody CategorySaveReq req){
        CommonResp resp =  new CommonResp<>();
        categoryService.save(req);
        return resp;
    }

    @GetMapping("/list")
    public CommonResp list(@Valid CategoryQueryReq req){
        CommonResp<PageResp> resp =  new CommonResp<>();
        PageResp list = categoryService.list(req);
        resp.setContent(list);
        return resp;
    }

    @DeleteMapping("/delete/{id}")
    public CommonResp delete(@PathVariable Long id){
        CommonResp resp =  new CommonResp<>();
        categoryService.delete(id);
        return resp;
    }
}
