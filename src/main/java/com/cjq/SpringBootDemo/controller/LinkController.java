package com.cjq.SpringBootDemo.controller;

import com.cjq.SpringBootDemo.domain.LinkUrl;
import com.cjq.SpringBootDemo.domain.LinkType;
import com.cjq.SpringBootDemo.domain.Result;
import com.cjq.SpringBootDemo.repository.LinkTypeRepository;
import com.cjq.SpringBootDemo.repository.LinkUrlRepository;
import com.cjq.SpringBootDemo.util.ResultUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping(value = "link")
public class LinkController {

    @Resource
    private LinkUrlRepository linkUrlRepository;
    @Resource
    private LinkTypeRepository linkTypeRepository;

    @RequestMapping(value = "link")
    public Result link(){
        List<LinkUrl> urls = linkUrlRepository.findAll();
        List<LinkType> types = linkTypeRepository.findAll();
        return ResultUtil.success(urls);
    }
}
