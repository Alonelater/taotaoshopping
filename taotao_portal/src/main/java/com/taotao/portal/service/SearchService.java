package com.taotao.portal.service;

import com.taotao.portal.pojo.SearchResult;

/**
 *
 * 商品搜索的服务层
 */
public interface SearchService {
    SearchResult query(String queryString, int page);
}
