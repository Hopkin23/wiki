package com.hopkin.wiki.service;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hopkin.wiki.domain.Ebook;
import com.hopkin.wiki.domain.EbookExample;
import com.hopkin.wiki.mapper.EbookMapper;
import com.hopkin.wiki.req.EbookQueryReq;
import com.hopkin.wiki.req.EbookSaveReq;
import com.hopkin.wiki.resp.EbookQueryResp;
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
public class EbookService {
    @Resource
    private EbookMapper ebookMapper;

    @Resource
    private SnowFlake snowFlake;

    private static final Logger LOG = LoggerFactory.getLogger(EbookService.class);

    public PageResp list(EbookQueryReq req){
        //PageHelper分页
        PageHelper.startPage(req.getPage(), req.getSize());

        //以下两行为生成Example的固定代码
        EbookExample ebookExample = new EbookExample();
        EbookExample.Criteria criteria = ebookExample.createCriteria();
        //动态SQL
        if(!ObjectUtils.isEmpty(req.getName())){
            //模糊查询
            criteria.andNameLike("%"+req.getName()+"%");
        }
        List<Ebook> ebookList = ebookMapper.selectByExample(ebookExample);
        //CopyUtil工具类 列表复制
        List<EbookQueryResp> resp = CopyUtil.copyList(ebookList, EbookQueryResp.class);

        PageInfo<Ebook> pageInfo = new PageInfo<>(ebookList);
        //LOG.info("总行数：{}", pageInfo.getTotal());
        //LOG.info("总页数：{}", pageInfo.getPages());

        PageResp<EbookQueryResp> objectPageResp = new PageResp<>();
        objectPageResp.setTotal(pageInfo.getTotal());
        objectPageResp.setList(resp);
        return objectPageResp;
    }

    /**
     * 保存,支持新增和更新
     */
    public void save(EbookSaveReq req) {
        Ebook ebook = CopyUtil.copy(req, Ebook.class);
        if(ObjectUtils.isEmpty(req.getId())){
            //新增
            ebook.setId(snowFlake.nextId());
            ebookMapper.insertSelective(ebook);
        }else {
            //更新
            Ebook ebook1 = ebookMapper.selectByPrimaryKey(req.getId());
            LOG.info("id查找记录{}",ebook1);
            ebookMapper.updateByPrimaryKey(ebook);
        }
    }

    /**
     * 删除电子书
     */
    public void delete(Long id) {
        ebookMapper.deleteByPrimaryKey(id);
    }
}
