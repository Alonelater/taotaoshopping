package com.taotao.portal.service;

import com.taotao.portal.pojo.TbItem;

/**
 *
 * 展示商品的基本信息
 */
public interface ItemService {
    TbItem getItemBase(Long itemId);
    String getItemDesc(Long itemId);
    String getItemParam(Long itemId);
}
