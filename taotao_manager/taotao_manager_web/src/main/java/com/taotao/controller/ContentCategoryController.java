package com.taotao.controller;

import com.taotao.pojo.EUDataGridResult;
import com.taotao.pojo.EUTreeNode;
import com.taotao.service.ContentCategoryService;
import com.taotao.utils.TaotaoResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.swing.tree.TreeNode;
import java.util.List;

/**
 *
 * 这个是为了将展示商品有哪些分类的控制器
 */
@Controller
@RequestMapping("/content")
public class ContentCategoryController {


    @Autowired
    private ContentCategoryService contentCategoryService;

    @ResponseBody
    @RequestMapping(value = "/category/list")
    public List<EUTreeNode> getContentCategoryList(@RequestParam(value = "id",defaultValue = "0") Long parentId){
        List<EUTreeNode> list = contentCategoryService.getContentCategoryList(parentId);
        return list;
    }


    //下面这个方法是增加内容分类  由于前台需要我们新添加节点的id
    // 所以我们需要回显之前我们自动生成的id 所以我们待会儿就要改一改ContentCategoryMapper.xml文件

    @ResponseBody
    @RequestMapping(value = "/category/create")
    public TaotaoResult insertContentCategory(Long parentId,String name){
        TaotaoResult result = contentCategoryService.insertContentCategroy(parentId, name);
        return result;
    }




    @ResponseBody
    @RequestMapping(value = "/category/update")
    public TaotaoResult updateContentCategory(Long id,String name){
        TaotaoResult result = contentCategoryService.updateContentCategroy(id, name);
        return result;
    }


    @ResponseBody
    @RequestMapping(value = "/category/delete")
    public TaotaoResult deleteContentCategory(Long parentId,Long id){
        TaotaoResult result = contentCategoryService.deleteContentCategroy(parentId, id);
        return result;
    }


    @ResponseBody
    @RequestMapping(value = "/query/list")
    public EUDataGridResult queryContentList(Long categoryId, int page,int rows){
        EUDataGridResult result = contentCategoryService.queryContentList(categoryId,page,rows);
        return result;
    }
}
