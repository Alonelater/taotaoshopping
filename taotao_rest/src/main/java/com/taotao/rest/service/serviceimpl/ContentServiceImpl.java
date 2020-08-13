package com.taotao.rest.service.serviceimpl;


import com.taotao.mapper.TbContentMapper;
import com.taotao.pojo.TbContent;
import com.taotao.pojo.TbContentExample;
import com.taotao.rest.dao.JedisClient;
import com.taotao.rest.service.ContentService;
import com.taotao.utils.JsonUtils;
import com.taotao.utils.TaotaoResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContentServiceImpl implements ContentService {
    @Autowired
    private TbContentMapper contentMapper;
    //面向接口编程的好处就是我们传递什么子类就是什么子类在帮我们做事  具体实现细节交给子类
    @Autowired
    private JedisClient jedisClient;
    @Value("${CONTENT_INDEX_REDIX_KEY}")
    private  String HKEY;
    @Override
    public List<TbContent> getContentList(Long contentCategoryId) {
        //由于现在有了缓存了所以我们现在将数据添加进缓存
        //添加缓存的原则就是即使出异常了我们也不能够将原有的功能打乱
        //一进来就应该直接去取  如果命中就不再从数据库中取值
        try {
            String result = jedisClient.hget(HKEY, contentCategoryId + "");
            if (!StringUtils.isBlank(result)){
                List<TbContent> list = JsonUtils.jsonToList(result, TbContent.class);
                return list;
            }

        }catch (Exception e){
            e.printStackTrace();
        }


        TbContentExample example = new TbContentExample();
        TbContentExample.Criteria criteria = example.createCriteria();
        criteria.andCategoryIdEqualTo(contentCategoryId);
        List<TbContent> list = contentMapper.selectByExample(example);
        //添加缓存的原则就是即使出异常了我们也不能够将原有的功能打乱
        //我们就应该将数据添加在缓存里  由于缓存中只能存放字符串  所以我们将得到的集合转成字符串
       try {
           String cacheList = JsonUtils.objectToJson(list);
           long hset = jedisClient.hset(HKEY, contentCategoryId + "", cacheList);
       }catch (Exception e){
           e.printStackTrace();
       }

        return list;


    }
}
