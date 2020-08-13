package com.taotao.search.service;

import com.taotao.search.pojo.SearchResult;

/**
 *
 * 商品查询的服务层
 */
public interface SearchService {
    SearchResult search(String queryString, Integer page,Integer rows) throws Exception;
}
