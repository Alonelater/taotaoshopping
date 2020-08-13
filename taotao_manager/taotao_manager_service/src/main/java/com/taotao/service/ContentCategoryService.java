package com.taotao.service;

import com.taotao.pojo.EUDataGridResult;
import com.taotao.pojo.EUTreeNode;
import com.taotao.utils.TaotaoResult;

import java.util.List;

public interface ContentCategoryService {

    List<EUTreeNode> getContentCategoryList(Long parentId);
    TaotaoResult insertContentCategroy(Long parentId,String  name);

    TaotaoResult updateContentCategroy(Long id, String name);
    TaotaoResult deleteContentCategroy(Long parentId, Long id);

    EUDataGridResult queryContentList(Long categoryId, int page, int rows);
}
