package com.taotao.service.impl;

import com.taotao.mapper.TbItemParamItemMapper;
import com.taotao.pojo.TbItemParamItem;
import com.taotao.pojo.TbItemParamItemExample;
import com.taotao.service.ItemParamItemService;
import com.taotao.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


//这个就是读取某个商品的参数规格项  我们需要向淘宝天猫一样具体展示某个商品的详细参数 之前的ItemParamService 只是为了定制某一类商品的模板
@Service
public class ItemParamItemServiceImpl implements ItemParamItemService {
    @Autowired
    private TbItemParamItemMapper itemParamItemMapper;

    //由于是单表查询 所以不用写dao  直接写service
    /**
     *   展示具体商品规格参数
     *
     * 业务逻辑  接受具体的商品编号 然后查询信息 根据pojo 进行拼接数据生成html 返回给Controller
     *
     * @param itemId  具体商品的id
     * @return
     */
    @Override
    public String getItemParamItemByItemId(Long itemId) {
        //查询商品
        TbItemParamItemExample example = new TbItemParamItemExample();
        TbItemParamItemExample.Criteria criteria = example.createCriteria();
        criteria.andItemIdEqualTo(itemId);
        List<TbItemParamItem> list = itemParamItemMapper.selectByExampleWithBLOBs(example);
        if (list==null||list.size()==0){
            return "";
        }
        //得到规格参数信息
        TbItemParamItem tbItemParamItem = list.get(0);

        //获得json数据转化成java对象进行遍历
        String paramData = tbItemParamItem.getParamData();
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



}

