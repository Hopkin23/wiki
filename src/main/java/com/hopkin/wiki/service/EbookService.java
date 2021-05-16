package com.hopkin.wiki.service;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hopkin.wiki.domain.Ebook;
import com.hopkin.wiki.domain.EbookExample;
import com.hopkin.wiki.mapper.EbookMapper;
import com.hopkin.wiki.req.EbookReq;
import com.hopkin.wiki.resp.EbookResp;
import com.hopkin.wiki.resp.PageResp;
import com.hopkin.wiki.utils.CopyUtil;
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

    private static final Logger LOG = LoggerFactory.getLogger(EbookService.class);

    public PageResp list(EbookReq req){
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
        List<EbookResp> resp = CopyUtil.copyList(ebookList, EbookResp.class);

        PageInfo<Ebook> pageInfo = new PageInfo<>(ebookList);
        //LOG.info("总行数：{}", pageInfo.getTotal());
        //LOG.info("总页数：{}", pageInfo.getPages());

        PageResp<EbookResp> objectPageResp = new PageResp<>();
        objectPageResp.setTotal(pageInfo.getTotal());
        objectPageResp.setList(resp);
        return objectPageResp;
    }
}
