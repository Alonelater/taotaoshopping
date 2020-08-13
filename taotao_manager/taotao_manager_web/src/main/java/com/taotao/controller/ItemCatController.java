package com.taotao.controller;

import com.taotao.pojo.EUTreeNode;
import com.taotao.service.ItemCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("item/cat")
public class ItemCatController {




    /**
     *
     * 这个是我们商品项目的分类的控制器
     *
     */

    @Autowired
    private ItemCatService itemCatService;

    //点击商品类目选择的时候 根据WEB-INF里面的路径 找到item/cat/list 所以执行这个方法
    @ResponseBody
    @RequestMapping("/list")
    public List<EUTreeNode> getEUTreeNodeList(@RequestParam(value = "id",defaultValue = "0",required = false) long parentId){
        List<EUTreeNode> itemCatList = itemCatService.getItemCatList(parentId);
        return itemCatList;
    }

}
