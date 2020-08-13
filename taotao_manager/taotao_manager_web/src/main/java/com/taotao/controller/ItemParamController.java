package com.taotao.controller;

import com.taotao.pojo.EUDataGridResult;
import com.taotao.pojo.TbItemParam;
import com.taotao.service.ItemParamService;
import com.taotao.utils.TaotaoResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

/**
 *
 * 这是控制规格模板的控制器
 */
@Controller
@RequestMapping("/item/param")
public class ItemParamController {
    @Autowired
    private ItemParamService itemParamService;


    @RequestMapping("/query/itemcatid/{itemCatId}")
    @ResponseBody
    public TaotaoResult getItemParamByCId(@PathVariable Long itemCatId){
        TaotaoResult result = itemParamService.getItemParamByCid(itemCatId);
        return result;
    }


    @RequestMapping("/list")
    @ResponseBody
    public EUDataGridResult getItemParamList(Integer page, Integer rows){
        EUDataGridResult result = itemParamService.getItemParamList(page,rows);
        return result;
    }



    /**
     *
     * 添加规格参数模板
     * @param cid  被添加规格参数模板所属的id
     * @param paramData 前台转换好的json字符串
     * @return
     */
    @RequestMapping("/save/{cid}")
    @ResponseBody
    public TaotaoResult insertItemParam(@PathVariable("cid") Long cid,String paramData){
        //由于是单表操作 所以使用逆向工程帮我们插入  我们准备好数据就行了
        TbItemParam itemParam = new TbItemParam();
        itemParam.setItemCatId(cid);
        itemParam.setParamData(paramData);
        itemParam.setCreated(new Date());
        itemParam.setUpdated(new Date());
        TaotaoResult result = itemParamService.insertItemParam(itemParam);
        return result;
    }
}
