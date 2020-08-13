package com.taotao.portal.service.impl;

import com.taotao.pojo.TbItemDesc;
import com.taotao.pojo.TbItemParamItem;
import com.taotao.portal.pojo.TbItem;
import com.taotao.portal.service.ItemService;
import com.taotao.utils.HttpClientUtil;
import com.taotao.utils.JsonUtils;
import com.taotao.utils.TaotaoResult;

import jdk.nashorn.internal.runtime.ECMAException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ItemServiceImpl implements ItemService {

    @Value("${REST_BASE_URL}")
    private String REST_BASE_URL;

    @Value("${BASE_URL}")
    private String BASE_URL;
    @Value("${DESC_URL}")
    private String DESC_URL;
    @Value("${PARAM_URL}")
    private String PARAM_URL;

    @Override
    public TbItem getItemBase(Long itemId) {
        String json = HttpClientUtil.doGet(REST_BASE_URL + BASE_URL + itemId);
        try {

            TaotaoResult result = TaotaoResult.formatToPojo(json, TbItem.class);
            return (TbItem) result.getData();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 取商品描述的服务
     *
     * @param itemId
     * @return
     */
    @Override
    public String getItemDesc(Long itemId) {
        try {
            String json = HttpClientUtil.doGet(REST_BASE_URL + DESC_URL + itemId);
            TaotaoResult taotaoResult = TaotaoResult.formatToPojo(json, TbItemDesc.class);
            if (taotaoResult.getStatus()==200){
                TbItemDesc result =(TbItemDesc) taotaoResult.getData();
                return result.getItemDesc();
            }
        }catch (Exception e){

            e.printStackTrace();
        }
        return "";
    }

    @Override
    public String getItemParam(Long itemId) {
        try {
            String json = HttpClientUtil.doGet(REST_BASE_URL + PARAM_URL + itemId);
            TaotaoResult taotaoResult = TaotaoResult.formatToPojo(json, TbItemParamItem.class);
            if (taotaoResult.getStatus()==200){
                TbItemParamItem result =(TbItemParamItem) taotaoResult.getData();
                String paramData = result.getParamData();
                List<Map> jsonToList = JsonUtils.jsonToList(paramData, Map.class);
                //接下来就是拼串了
                StringBuffer sb = new StringBuffer();
                sb.append("<table border=\"1\" class=\"tm-tableAttr\">\n");
                sb.append("    <thead>\n");
                sb.append("    <tr>\n");
                sb.append("        <td colspan=\"2\">规格参数</td>\n");
                sb.append("    </tr>\n");
                sb.append("    </thead>\n");
                sb.append("    <tbody>\n");
//        得到大分类
                for (Map map : jsonToList) {
                    sb.append("    <tr class=\"tm-tableAttrSub\">\n");
                    sb.append("        <th colspan=\"2\" data-spm-anchor-id=\"a220o.1000855.0.i2.607c5ba1XBs7mH\">"+map.get("group")+"</th>\n");
                    sb.append("    </tr>\n");
//            得到大分类的数据里面的具体参数
                    List<Map> param = (List<Map>) map.get("params");
                    for (Map map1 : param) {
                        sb.append("    <tr>\n");
                        sb.append("        <th>"+map1.get("k")+"</th>\n");
                        sb.append("        <td>"+map1.get("v")+"</td>\n");
                        sb.append("    </tr>\n");
                    }
                    sb.append("    \n");
                    sb.append("   \n");
                }
                sb.append("    </tr></tbody>\n");
                sb.append("</table>");

                return sb.toString();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return "";
    }
}
