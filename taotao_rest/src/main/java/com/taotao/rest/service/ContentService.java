package com.taotao.rest.service;

import com.taotao.pojo.TbContent;
import com.taotao.utils.TaotaoResult;

import java.util.List;

public interface ContentService {
    List<TbContent> getContentList(Long contentCategoryId);
}
