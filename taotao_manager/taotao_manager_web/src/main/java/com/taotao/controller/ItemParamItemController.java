package com.taotao.controller;

import com.taotao.service.ItemParamItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

//    这个控制器就是用来接收具体的商品的控制器
@Controller
public class ItemParamItemController {


    @Autowired
    private ItemParamItemService itemParamItemService;

    /**
     *
     *
     * @param itemId  具体商品的id  这样就能找到具体商品的规格
     * @param model  我们将拼接好的数据放在视图层
     * @return
     */

    @RequestMapping("/showitem/{itemId}")
    public String getItemParamItemByItemId(@PathVariable("itemId") Long itemId, Model model) {
        String itemPararItem = itemParamItemService.getItemParamItemByItemId(itemId);
        //将数据放在模型层里面 然后去读取
        model.addAttribute("itemPararItem",itemPararItem);
        return "item";
    }
}
