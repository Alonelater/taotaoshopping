package com.taotao.rest.service.serviceimpl;

import com.taotao.rest.dao.JedisClient;
import com.taotao.rest.service.RedisService;
import com.taotao.utils.ExceptionUtil;
import com.taotao.utils.TaotaoResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


@Service
public class RedisServiceIpml implements RedisService {

    @Autowired
    private JedisClient jedisClient;
    @Value("${CONTENT_INDEX_REDIX_KEY}")
    private  String HKEY;

    @Override
    public TaotaoResult syncContent(long contentCid) {
        try {
            long hdel = jedisClient.hdel(HKEY, contentCid + "");

        }catch (Exception e){
            e.printStackTrace();
            return TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
        }
        return TaotaoResult.ok();

    }
}
