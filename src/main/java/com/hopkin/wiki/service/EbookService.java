package com.hopkin.wiki.service;


import com.hopkin.wiki.domain.Ebook;
import com.hopkin.wiki.mapper.EbookMapper;
import com.hopkin.wiki.req.EbookReq;
import com.hopkin.wiki.resp.EbookResp;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class EbookService {
    @Resource
    private EbookMapper ebookMapper;

    public List<EbookResp> list(EbookReq req){
        ArrayList<EbookResp> respList = new ArrayList<>();
        List<Ebook> ebookList = ebookMapper.selectByExample(null);
        for(Ebook ebook: ebookList){
            EbookResp ebookResp = new EbookResp();
            BeanUtils.copyProperties(ebook,ebookResp);
            respList.add(ebookResp);
        }
        return respList;
    }
}
