package com.taotao.rest.controller;

import com.taotao.rest.service.ItemService;
import com.taotao.utils.JsonUtils;
import com.taotao.utils.TaotaoResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/item")
public class ItemController {

    @Autowired
    private ItemService itemService;


    @RequestMapping("/base/{itemId}")
    @ResponseBody
    public TaotaoResult getItemBsaeInfo(@PathVariable Long itemId){
        TaotaoResult baseInfo = itemService.getItemBaseInfo(itemId);
        return baseInfo;
    }

    @RequestMapping("/desc/{itemId}")
    @ResponseBody
    public TaotaoResult getItemDescInfo(@PathVariable Long itemId){
        TaotaoResult descInfo = itemService.getItemDescInfo(itemId);
        return descInfo;
    }
    @RequestMapping("/param/{itemId}")
    @ResponseBody
    public TaotaoResult getItemParamInfo(@PathVariable Long itemId){
        TaotaoResult paramInfo = itemService.getItemParamInfo(itemId);
        return paramInfo;
    }
}
