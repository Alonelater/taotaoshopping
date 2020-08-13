package com.taotao.search.service.impl;

import com.taotao.search.dao.SearchDao;
import com.taotao.search.pojo.SearchResult;
import com.taotao.search.service.SearchService;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * 实现searchService的实体类
 */
@Service
public class SearchServiceImpl implements SearchService {
    //在这里面我们要完成根据关键字查询 结果返回 将高亮显示，返回总记录数 当前页数
    @Autowired
    private SearchDao searchDao;

    @Override
    public SearchResult search(String queryString, Integer page, Integer rows) throws Exception {
        //创建一个查询对象
        SolrQuery query = new SolrQuery();
        //将查询条件传入
        //设置查询关键字
        query.setQuery(queryString);
        //设置查询的开始索引
        query.setStart((page-1)*rows);
        //设置每页显示的记录数
        query.setRows(rows);
        //设置默认搜索域
        query.set("df","item_title");
        //设置是否显示高亮
        query.setHighlight(true);
        //设置高亮查询搜索域
        query.addHighlightField("item_title");
        //设置高亮显示前缀
        query.setHighlightSimplePre("<em style=\"color:red\">");
        //设置高亮显示后缀
        query.setHighlightSimplePost("</em>");
        SearchResult result = searchDao.searchByQuery(query);
        //得到结果总页数 并且计算总页数和设置当前页码数
        long recordCount = result.getRecordCount();
        //计算总页数
        result.setPageCount(recordCount%rows==0?recordCount/rows:(recordCount/rows)+1);
        //设置当前页
        result.setCurPage(page);
        return result;


    }
}
