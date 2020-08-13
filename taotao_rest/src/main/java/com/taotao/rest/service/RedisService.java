package com.taotao.rest.service;

import com.taotao.utils.TaotaoResult;

/**
 *
 * 发布redis服务
 */
public interface RedisService {
    //这个表示同步内容
    TaotaoResult syncContent(long contentCid);
}
