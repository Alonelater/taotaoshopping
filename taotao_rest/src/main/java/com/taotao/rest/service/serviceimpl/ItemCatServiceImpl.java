package com.taotao.rest.service.serviceimpl;

import com.alibaba.druid.sql.dialect.mysql.ast.MysqlForeignKey;
import com.taotao.mapper.TbItemCatMapper;
import com.taotao.pojo.TbItemCat;
import com.taotao.pojo.TbItemCatExample;
import com.taotao.pojo.TbItemParam;
import com.taotao.pojo.TbItemParamItemExample;
import com.taotao.rest.pojo.CatNode;
import com.taotao.rest.pojo.CatResult;

import com.taotao.rest.service.ItemCatService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.StyledEditorKit;
import java.util.ArrayList;
import java.util.List;

@Service
public class ItemCatServiceImpl implements ItemCatService {

    @Autowired
    private TbItemCatMapper itemCatMapper;

    @Override
    public CatResult getItemCatList() {
//      这里我们就负责返回结果 查询让后面的小弟来做
        CatResult result = new CatResult();
        //因为要的是全部数据，我们只知道根节点是没有父节点的 而且他的id为0，
        // 我们使用递归 依次全部查询出来
        result.setData(getCatList(0));

        return result;
    }

    private List<?> getCatList(long parentId) {
        //经过分析 因为是单表查询 所以直接使用逆向工程
        TbItemCatExample example = new TbItemCatExample();
        TbItemCatExample.Criteria criteria = example.createCriteria();
        criteria.andParentIdEqualTo(parentId);
        List<TbItemCat> tbItemCats = itemCatMapper.selectByExample(example);
        //接下来准备一个集合 作为最终接收的返回值
        List<Object> resultList = new ArrayList<>();
        int count =0;
        for (TbItemCat item : tbItemCats) {
            //判断是否为父节点
            if (item.getIsParent()){
                CatNode catNode = new CatNode();
                if (parentId == 0) {
                    catNode.setName("<a href='/products/" + item.getId() + ".html'>" + item.getName() + "</a>");
                } else {
                    catNode.setName(item.getName());
                }
                catNode.setUrl("/products/" + item.getId() + ".html");
                catNode.setItem(getCatList(item.getId()));
                resultList.add(catNode);
                count++;
                if(parentId==0&&count>=14){
                    break;
                }
            }else {
                resultList.add("/products/"+item.getId()+".html|"+item.getName());
            }

        }
        return resultList;
    }
}
