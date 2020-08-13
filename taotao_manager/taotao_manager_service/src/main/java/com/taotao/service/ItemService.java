package com.taotao.service;

import com.taotao.pojo.EUDataGridResult;
import com.taotao.pojo.TbItem;
import com.taotao.utils.TaotaoResult;

public interface ItemService {
    TbItem getItemById(Long itemId);

    EUDataGridResult getItemList(Integer page, Integer rows);
    TaotaoResult creatItem(TbItem tbItem,String desc) throws Exception;
}
