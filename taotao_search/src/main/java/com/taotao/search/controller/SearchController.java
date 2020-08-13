package com.taotao.search.controller;

import com.taotao.search.pojo.SearchResult;
import com.taotao.search.service.SearchService;
import com.taotao.utils.ExceptionUtil;
import com.taotao.utils.TaotaoResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 商品查询的controller
 */
@Controller
public class SearchController {


    @Autowired
    private SearchService searchService;


    @RequestMapping(value = "/query",method = RequestMethod.GET)
    @ResponseBody
    TaotaoResult search(@RequestParam(value = "q") String queryString,
                        @RequestParam(defaultValue = "1") Integer page,
                        @RequestParam(defaultValue = "60") Integer rows) {

        //首先先对查询关键字进行非空判断
        if (StringUtils.isBlank(queryString)){
            return TaotaoResult.build(400,"查询条件不能为空");
        }
        SearchResult searchList =null;

        try {
            //解决get乱码问题
            //queryString = new String(queryString.getBytes("iso8859-1"),"utf-8");
            searchList = searchService.search(queryString, page, rows);
        } catch (Exception e) {
            e.printStackTrace();
            return TaotaoResult.build(500,ExceptionUtil.getStackTrace(e));
        }

        return TaotaoResult.ok(searchList);
    }

}
