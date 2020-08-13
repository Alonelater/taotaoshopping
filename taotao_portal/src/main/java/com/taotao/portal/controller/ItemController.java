package com.taotao.portal.controller;

import com.taotao.portal.pojo.TbItem;
import com.taotao.portal.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;



@Controller
public class ItemController {
    @Autowired
    private ItemService itemService;
    @RequestMapping("/item/{itemId}")
    public String showItemBase(@PathVariable Long itemId, Model model){
        TbItem itemBase = itemService.getItemBase(itemId);
        model.addAttribute("item",itemBase);
        return "item";
    }


    //
    @RequestMapping(value = "/item/desc/{itemId}",produces= MediaType.TEXT_HTML_VALUE+";charset=utf-8")
    @ResponseBody
    public String showItemDesc(@PathVariable Long itemId){
        String s = itemService.getItemDesc(itemId);

        return s;

    }
    @RequestMapping(value = "/item/param/{itemId}",produces= MediaType.TEXT_HTML_VALUE+";charset=utf-8")
    @ResponseBody
    public String showItemParam(@PathVariable Long itemId){
        String s = itemService.getItemParam(itemId);

        return s;

    }
}
