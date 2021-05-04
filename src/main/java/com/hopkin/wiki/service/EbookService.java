package com.hopkin.wiki.service;


import com.hopkin.wiki.domain.Ebook;
import com.hopkin.wiki.mapper.EbookMapper;
import com.hopkin.wiki.req.EbookReq;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class EbookService {
    @Resource
    private EbookMapper ebookMapper;

    public List<Ebook> list(EbookReq req){
        return ebookMapper.selectByExample(null);
    }
}
