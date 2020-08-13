package com.taotao.controller;

import com.taotao.service.PictureService;
import com.taotao.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
public class PictureController {
    @Autowired
    private PictureService pictureService;


    //路径地址参考common.js里面的kingEditorParams : {
    //		//指定上传文件参数名称
    //		filePostName  : "uploadFile",
    //		//指定上传文件请求的url。
    //		uploadJson : '/pic/upload',
    //		//上传类型，分别为image、flash、media、file
    //		dir : "image"
    //	},
    @RequestMapping("/pic/upload")
    @ResponseBody
    public String pictureUpload(MultipartFile uploadFile ){//我们这里的形参名字一定要和common.js里面一样 不然就会注入不进来的


        //在这里我们一定要注意去配置一个文件上传解析器
        Map map = pictureService.uploadPicture(uploadFile);
        //调试过程中发现  在谷歌上面耗时 能够正常上传图片 正常显示上传的图片 但是在ie浏览器或者火狐就不行
        //为了兼容考虑 我们将这里的map用工具类JsonUtils做一个转换  转换成json格式
        String json = JsonUtils.objectToJson(map);
        return json;

    }

}
