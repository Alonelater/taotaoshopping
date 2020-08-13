package com.taotao.portal.service.impl;

import com.taotao.portal.pojo.SearchResult;
import com.taotao.portal.service.SearchService;
import com.taotao.utils.HttpClientUtil;
import com.taotao.utils.JsonUtils;
import com.taotao.utils.TaotaoResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * 这个是service的实现类
 */
@Service
public class SearchServiceImpl implements SearchService {



    //获取配置文件的基础路径
    @Value("${SEARCH_BASE_URL}")
    private String SEARCH_BASE_URL;


    //需要一个查询的文本关键字  还有当前页
    @Override
    public SearchResult query(String queryString, int page) {
        Map<String,String> param = new HashMap<>();
        param.put("q",queryString);
        param.put("page",page+"");
        //调取taotao_search 帮忙查询结果 然后将得到的json重新封装成pojo对象
//        {"itemList":[
//        {"id":"143762175492355","title":"雅培(Abbott) 亲体 金装喜康力幼儿配方<em style=\"color:red\">奶粉</em> 3段（1-3岁幼儿适用）900克","sell_point":"雅培新配方三大亲体科技，国际大奖智锁罐更安心！","price":1510,"image":"http://localhost:9000/images/20150723/1437619645356423.jpg","category_name":null,"item_desc":null},
//        {"id":"143762197412305","title":"雅培(Abbott) 亲体 金装喜康力幼儿配方<em style=\"color:red\">奶粉</em> 3段（1-3岁幼儿适用）900克","sell_point":"雅培新配方三大亲体科技，国际大奖智锁罐更安心！","price":1510,"image":"http://localhost:9000/images/20150723/1437619645356423.jpg","category_name":null,"item_desc":null}],
//        "recordCount":2,"pageCount":1,"curPage":1}}

        try {

            String json = HttpClientUtil.doGet(SEARCH_BASE_URL, param);
            //将查询到的数据封装成SearchResult这个pojo对象
            TaotaoResult taotaoResult = TaotaoResult.formatToPojo(json, SearchResult.class);
            if (taotaoResult.getStatus()==200){
                SearchResult result = (SearchResult) taotaoResult.getData();
                return result;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;


    }

}
