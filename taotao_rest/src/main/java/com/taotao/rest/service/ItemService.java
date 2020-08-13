package com.taotao.rest.service;

import com.taotao.utils.TaotaoResult;

public interface ItemService {
    TaotaoResult getItemBaseInfo(Long itemId);
    TaotaoResult getItemDescInfo(Long itemId);
    TaotaoResult getItemParamInfo(Long itemId);
}
