package com.taotao.rest.controller;

import com.taotao.rest.pojo.CatResult;
import com.taotao.rest.service.ItemCatService;
import com.taotao.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.awt.*;

@Controller
public class ItemCatController {


    @Autowired
    private ItemCatService itemCatService;
//    由于是跨域进行数据的传递 又因为ajax不支跨域传送数据  所以只能利用js跨域访问js
//    但是这样会有点问题  就是js一旦请求到就得立马执行 所以我们得将我们service得到的数据进行包装
//    将数据放在回调方法里面作为参数 让js自动从回调方法里面区值
//这样得到的数据会乱码  所以我们这样解决produces = MediaType.APPLICATION_JSON_VALUE + ";chartset=utf-8"

    @RequestMapping(value = "itemcat/list",produces = MediaType.APPLICATION_JSON_VALUE + ";charset=utf-8")
    @ResponseBody
    public String getItemCatList(String callback){
        CatResult itemCatList = itemCatService.getItemCatList();
        String json = JsonUtils.objectToJson(itemCatList);
        return callback+"("+json+")";


    }

    /*

    //下面介绍另外一种方法 和上面一模一样 但是需要springmvc4.1以后
    @RequestMapping(value = "itemcat/list")
    @ResponseBody
    public Object getItemCatList(String callback){
        CatResult itemCatList = itemCatService.getItemCatList();
        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(itemCatList);
        mappingJacksonValue.setJsonpFunction(callback);
        return mappingJacksonValue;

    }*/
}
