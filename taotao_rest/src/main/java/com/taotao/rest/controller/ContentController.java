package com.taotao.rest.controller;

import com.taotao.pojo.TbContent;
import com.taotao.rest.service.ContentService;
import com.taotao.utils.ExceptionUtil;
import com.taotao.utils.TaotaoResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/content")
public class ContentController {
    @Autowired
    private ContentService contentService;
//获取对应id下面的广告内容列表
    @ResponseBody
    @RequestMapping("/list/{contentCategoryId}")
    public TaotaoResult getContentList(@PathVariable Long contentCategoryId) {
        try {
            List<TbContent> contentList = contentService.getContentList(contentCategoryId);
            return TaotaoResult.ok(contentList);
        } catch (Exception e) {
            e.getStackTrace();
            return TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
        }

    }
}
