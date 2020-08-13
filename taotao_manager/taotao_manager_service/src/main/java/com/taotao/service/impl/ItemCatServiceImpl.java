package com.taotao.service.impl;

import com.taotao.mapper.TbItemCatMapper;
import com.taotao.pojo.EUTreeNode;
import com.taotao.pojo.TbItemCat;
import com.taotao.pojo.TbItemCatExample;
import com.taotao.service.ItemCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class ItemCatServiceImpl implements ItemCatService {
    @Autowired
    private TbItemCatMapper itemCatMapper;


    @Override
    public List<EUTreeNode> getItemCatList(long parentId) {


        //首先我们需要一个example对象 帮我们查询我们的数据
        TbItemCatExample example = new TbItemCatExample();
        TbItemCatExample.Criteria criteria = example.createCriteria().andParentIdEqualTo(parentId);
        //接下来就是帮我们查询数据
        List<TbItemCat> tbItemCats = itemCatMapper.selectByExample(example);

       //我们需要一个集合 用来装我们的EUTreeNode 集合
        List<EUTreeNode> treeNodeList = new ArrayList<>();
        for (TbItemCat itemCat :tbItemCats){
            //接下来我们就是要将每次遍历出来的数据封装在我们EUTreeNode里面 所以我们创建这个对象
            EUTreeNode treeNode = new EUTreeNode();
            treeNode.setId(itemCat.getId());
            treeNode.setText(itemCat.getName());
            treeNode.setState(itemCat.getIsParent()?"closed":"open");
            //将我们得到的放在我们的集合树里面
            treeNodeList.add(treeNode);
        }
        //返回我们的集合树
        return treeNodeList;
    }
}
