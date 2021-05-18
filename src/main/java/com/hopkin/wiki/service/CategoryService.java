package com.hopkin.wiki.service;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hopkin.wiki.domain.Category;
import com.hopkin.wiki.domain.CategoryExample;
import com.hopkin.wiki.mapper.CategoryMapper;
import com.hopkin.wiki.req.CategoryQueryReq;
import com.hopkin.wiki.req.CategorySaveReq;
import com.hopkin.wiki.resp.CategoryQueryResp;
import com.hopkin.wiki.resp.PageResp;
import com.hopkin.wiki.utils.CopyUtil;
import com.hopkin.wiki.utils.SnowFlake;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CategoryService {
    @Resource
    private CategoryMapper categoryMapper;

    @Resource
    private SnowFlake snowFlake;

    private static final Logger LOG = LoggerFactory.getLogger(CategoryService.class);

    /**
     *  查询所有数据不分页
     */
    public List<CategoryQueryResp> all() {
        //以下两行为生成Example的固定代码
        CategoryExample categoryExample = new CategoryExample();
        categoryExample.setOrderByClause("sort asc");
        List<Category> categoryList = categoryMapper.selectByExample(categoryExample);

        // 列表复制
        List<CategoryQueryResp> list = CopyUtil.copyList(categoryList, CategoryQueryResp.class);
        return list;
    }

    public PageResp list(CategoryQueryReq req){
        //PageHelper分页
        PageHelper.startPage(req.getPage(), req.getSize());

        //以下两行为生成Example的固定代码
        CategoryExample categoryExample = new CategoryExample();

        List<Category> categoryList = categoryMapper.selectByExample(categoryExample);
        //CopyUtil工具类 列表复制
        List<CategoryQueryResp> resp = CopyUtil.copyList(categoryList, CategoryQueryResp.class);

        PageInfo<Category> pageInfo = new PageInfo<>(categoryList);
        //LOG.info("总行数：{}", pageInfo.getTotal());
        //LOG.info("总页数：{}", pageInfo.getPages());

        PageResp<CategoryQueryResp> objectPageResp = new PageResp<>();
        objectPageResp.setTotal(pageInfo.getTotal());
        objectPageResp.setList(resp);
        return objectPageResp;
    }

    /**
     * 保存,支持新增和更新
     */
    public void save(CategorySaveReq req) {
        Category category = CopyUtil.copy(req, Category.class);
        if(ObjectUtils.isEmpty(req.getId())){
            //新增
            category.setId(snowFlake.nextId());
            categoryMapper.insertSelective(category);
        }else {
            //更新
            Category category1 = categoryMapper.selectByPrimaryKey(req.getId());
            LOG.info("id查找记录{}",category1);
            categoryMapper.updateByPrimaryKey(category);
        }
    }

    /**
     * 删除电子书
     */
    public void delete(Long id) {
        categoryMapper.deleteByPrimaryKey(id);
    }

}
