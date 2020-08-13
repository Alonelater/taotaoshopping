package com.taotao.search.controller;

import com.taotao.search.service.ItemService;
import com.taotao.utils.TaotaoResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/manager")
public class ItemController {


    @Autowired
    private ItemService itemService;
    /**
     *
     * 导入所有商品信息到索引库
     */

    @RequestMapping("/importall")
    @ResponseBody
    public TaotaoResult importAllItems(){
        TaotaoResult result = itemService.importAllItems();
        return result;
    }

}
