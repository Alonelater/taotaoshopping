package com.taotao.rest.service.serviceimpl;

import com.mysql.jdbc.PacketTooBigException;
import com.taotao.mapper.TbItemDescMapper;
import com.taotao.mapper.TbItemMapper;
import com.taotao.mapper.TbItemParamItemMapper;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;
import com.taotao.pojo.TbItemParamItem;
import com.taotao.pojo.TbItemParamItemExample;
import com.taotao.rest.dao.JedisClient;
import com.taotao.rest.service.ItemService;
import com.taotao.utils.JsonUtils;
import com.taotao.utils.TaotaoResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Level;

@Service
public class ItemServiceImpl implements ItemService {

    //为缓存添加可选参数 一个是过期时间 一个是基本信息的键
    @Value("${REDIS_ITEM_KEY}")
    private  String REDIS_ITEM_KEY;
    @Value("${REDIS_ITEM_EXPIRETIME}")
    private  Integer REDIS_ITEM_EXPIRETIME;
    @Autowired
    private TbItemMapper itemMapper;

    @Autowired
    private TbItemDescMapper itemDescMapper;

    @Autowired
    private JedisClient jedisClient;

    @Autowired
    private TbItemParamItemMapper itemParamItemMapper;

    //获取商品基本信息
    @Override
    public TaotaoResult getItemBaseInfo(Long itemId) {

//        添加缓存逻辑
        //从缓存中取
        try {
            String json = jedisClient.get(REDIS_ITEM_KEY + ":" + itemId + ":base");
            if (!StringUtils.isBlank(json)){
                TbItem tbItem = JsonUtils.jsonToPojo(json, TbItem.class);
                return TaotaoResult.ok(tbItem);
            }
        }catch (Exception e){

            e.printStackTrace();
        }
        TbItem item = itemMapper.selectByPrimaryKey(itemId);

        try {
            //将数据存入缓存
            //由于在Redis 中的hash类型中的key 是不能设置过期时间的
            // 所以我们只好在用String 来存储 在key上面设置一个这种方案能够进行分类
            jedisClient.set(REDIS_ITEM_KEY+":"+itemId+":base", JsonUtils.objectToJson(item));
            jedisClient.expire(REDIS_ITEM_KEY+":"+itemId+":base",REDIS_ITEM_EXPIRETIME);

        }catch (Exception e){

            e.printStackTrace();
        }

        return TaotaoResult.ok(item);
    }


    //获取商品基本描述信息
    @Override
    public TaotaoResult getItemDescInfo(Long itemId) {
        //        添加缓存逻辑
        //从缓存中取
        try {
            String json = jedisClient.get(REDIS_ITEM_KEY + ":" + itemId + ":desc");
            if (!StringUtils.isBlank(json)){
                TbItemDesc tbItemDesc = JsonUtils.jsonToPojo(json, TbItemDesc.class);
                return TaotaoResult.ok(tbItemDesc);
            }
        }catch (Exception e){
            e.printStackTrace();
        }


        TbItemDesc itemDesc = itemDescMapper.selectByPrimaryKey(itemId);

        try {
            //将数据存入缓存
            //由于在Redis 中的hash类型中的key 是不能设置过期时间的
            // 所以我们只好在用String 来存储 在key上面设置一个这种方案能够进行分类
            jedisClient.set(REDIS_ITEM_KEY+":"+itemId+":desc", JsonUtils.objectToJson(itemDesc));
            jedisClient.expire(REDIS_ITEM_KEY+":"+itemId+":desc",REDIS_ITEM_EXPIRETIME);

        }catch (Exception e){

            e.printStackTrace();
        }


        return TaotaoResult.ok(itemDesc);
    }

    @Override
    public TaotaoResult getItemParamInfo(Long itemId) {
        //        添加缓存逻辑
        //从缓存中取
        try {
            String json = jedisClient.get(REDIS_ITEM_KEY + ":" + itemId + ":param");
            if (!StringUtils.isBlank(json)){
                TbItemParamItem itemParamItem = JsonUtils.jsonToPojo(json, TbItemParamItem.class);
                return TaotaoResult.ok(itemParamItem);
            }
        }catch (Exception e){
            e.printStackTrace();
        }


        TbItemParamItemExample example = new TbItemParamItemExample();
        TbItemParamItemExample.Criteria criteria = example.createCriteria();
        criteria.andItemIdEqualTo(itemId);
        List<TbItemParamItem> list = itemParamItemMapper.selectByExample(example);
        if (list!=null&&list.size()>0){
            TbItemParamItem itemParamItem = list.get(0);
            try {
                //将数据存入缓存
                //由于在Redis 中的hash类型中的key 是不能设置过期时间的
                // 所以我们只好在用String 来存储 在key上面设置一个这种方案能够进行分类
                jedisClient.set(REDIS_ITEM_KEY+":"+itemId+":param", JsonUtils.objectToJson(itemParamItem));
                jedisClient.expire(REDIS_ITEM_KEY+":"+itemId+":param",REDIS_ITEM_EXPIRETIME);

            }catch (Exception e){

                e.printStackTrace();
            }
            return TaotaoResult.ok(itemParamItem);
        }



        return TaotaoResult.build(400,"无此商品规格");
    }
}
