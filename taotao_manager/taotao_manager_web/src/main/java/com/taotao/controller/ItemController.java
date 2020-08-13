package com.taotao.controller;

import com.taotao.pojo.EUDataGridResult;
import com.taotao.pojo.TbItem;
import com.taotao.service.ItemService;
import com.taotao.utils.HttpClientUtil;
import com.taotao.utils.TaotaoResult;
import org.omg.CORBA.PRIVATE_MEMBER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 首先我们做个测试 测试根据id得到商品的信息
 */
@Controller
public class ItemController {


    @Autowired
    private ItemService itemService;

    @Value("${SEARCH_BASE_URL_SOLR}")
    private String SEARCH_BASE_URL_SOLR;


    @RequestMapping("/item/{itemId}")
    @ResponseBody
    public TbItem getTbItemById(@PathVariable Long itemId) {

        //调用service层帮我们
        TbItem tbItem = itemService.getItemById(itemId);

        return tbItem;
    }


       /*

   现在我们是使用easyui的框架写的后台界面  通过路径地址我们可以发现
    地址栏的参数是http://localhost:8080/item/list?page=1&rows=30

    因为是easyui写的框架  所以里面的datagrid都是发送的ajax请求  并且页面一加载就发送了  并且默认带了page(第几页)和rows(记录数)这两个参数的


    所以我们接下来就是写itemController  里面就要处理请求  请求的地址就是item/list


    又因为datagrid 要求返回Json数据。数据格式：
        Easyui中datagrid控件要求的数据格式为：
        {total:”2”,rows:[{“id”:”1”,”name”,”张三”},{“id”:”2”,”name”,”李四”}]}

        所以我们要使用pageHelper的插件
        1.导入maven插件里面的pageHelper
        2.在mybatis-config里面配置一下我们的pageHelper插件

     */


    @ResponseBody
    @RequestMapping("/item/list")
    public EUDataGridResult getItemList(Integer page, Integer rows) {

        EUDataGridResult result = itemService.getItemList(page, rows);
        return result;
    }




    @ResponseBody
    @RequestMapping(value="/item/save",method = RequestMethod.POST)
    public TaotaoResult creatItem(TbItem tbItem,String desc) throws Exception {

        TaotaoResult taotaoResult = itemService.creatItem(tbItem,desc);
        //每增加一个商品就要将索引库更新
        HttpClientUtil.doGet(SEARCH_BASE_URL_SOLR);
        return taotaoResult;
    }

}
