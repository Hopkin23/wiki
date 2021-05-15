package com.hopkin.wiki.service;


import com.hopkin.wiki.domain.Ebook;
import com.hopkin.wiki.domain.EbookExample;
import com.hopkin.wiki.mapper.EbookMapper;
import com.hopkin.wiki.req.EbookReq;
import com.hopkin.wiki.resp.EbookResp;
import com.hopkin.wiki.utils.CopyUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class EbookService {
    @Resource
    private EbookMapper ebookMapper;

    public List<EbookResp> list(EbookReq req){
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
        return resp;
    }
}
