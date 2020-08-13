package com.taotao.portal.service.impl;

import com.taotao.pojo.TbContent;
import com.taotao.portal.service.ContentService;
import com.taotao.utils.HttpClientUtil;
import com.taotao.utils.JsonUtils;
import com.taotao.utils.TaotaoResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 *
 * 调用服务层查询内容列表
 */
@Service
public class ContentServiceImpl implements ContentService {

    @Value("${REST_BASE_URL}")
    private String REST_BASE_URL;
    @Value("${REST_INDEX_AD_URL}")
    private String REST_INDEX_AD_URL;

    @Override
    public String getContentList() {
        //开始调用服务层的服务
        //我们不应该将信息写死  所以准备配置文件
        //里面执行了连接 response = httpclient.execute(httpGet);并且调用了taotao_rest中的控制器
        String url = REST_BASE_URL + REST_INDEX_AD_URL;
        String result = HttpClientUtil.doGet(REST_BASE_URL + REST_INDEX_AD_URL);
        try {
            //把字符串转成taotaoresult
            TaotaoResult taotaoResult = TaotaoResult.formatToList(result, TbContent.class);
            //将数据取出来  然后我们在进行加工生成一个前台认识的map对象的json数据
            List<TbContent> list = (List<TbContent>) taotaoResult.getData();
            List<Map<String,Object>> resultList = new ArrayList<>();
            for (TbContent tbContent : list) {
                Map<String,Object> map = new HashMap<>();
                map.put("src",tbContent.getPic());
                map.put("height",240);
                map.put("width","670");
                map.put("srcB",tbContent.getPic2());
                map.put("widthB",550);
                map.put("heightB",240);
                map.put("href",tbContent.getUrl());
                map.put("alt",tbContent.getSubTitle());
                resultList.add(map);
            }
            return JsonUtils.objectToJson(resultList);
        }catch (Exception e){
            e.printStackTrace();

        }
        return null;
    }
}
