package com.taotao.service;

import com.taotao.pojo.EUDataGridResult;
import com.taotao.pojo.TbItemParam;
import com.taotao.utils.TaotaoResult;

/**
 *
 * 这是商品规格模板的服务层
 */
public interface ItemParamService {
    //首先定义一个方法根据商品类目用于查询是否已经定制了模板  返回值是一个TaotaoResult
    TaotaoResult getItemParamByCid(Long cid);

    EUDataGridResult getItemParamList(Integer page, Integer rows);

    TaotaoResult insertItemParam(TbItemParam itemParam);
}
