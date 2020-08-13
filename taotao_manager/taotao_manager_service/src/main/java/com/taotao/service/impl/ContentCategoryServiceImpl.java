package com.taotao.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sun.org.apache.xerces.internal.xs.LSInputList;
import com.taotao.mapper.TbContentCategoryMapper;
import com.taotao.mapper.TbContentMapper;
import com.taotao.pojo.*;
import com.taotao.service.ContentCategoryService;
import com.taotao.utils.TaotaoResult;
import jdk.nashorn.internal.ir.CatchNode;
import org.apache.commons.io.filefilter.FalseFileFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class ContentCategoryServiceImpl implements ContentCategoryService {

    @Autowired
    private TbContentCategoryMapper contentCategoryMapper;
    @Autowired
    private TbContentMapper contentMapper;


    @Override
    public List<EUTreeNode> getContentCategoryList(Long parentId) {

        //创建查询条件
        TbContentCategoryExample example = new TbContentCategoryExample();
        TbContentCategoryExample.Criteria criteria = example.createCriteria();
        criteria.andParentIdEqualTo(parentId);
        //查询
        List<TbContentCategory> list = contentCategoryMapper.selectByExample(example);
        List<EUTreeNode> resultList = new ArrayList<>();

        for (TbContentCategory contentCategory : list) {
            EUTreeNode treeNode = new EUTreeNode();
            //我们将每次查询出来的信息的id作为下一次点击这个属性下面的判断条件
            //比如现在我们传来的0这个根节点 找到好几个的父节点是0的子节点  然后将
            //我们设置好他们的pojo的属性中的id，作为下次查询的父节点的查询节点。

            treeNode.setId(contentCategory.getId());
            treeNode.setText(contentCategory.getName());
            treeNode.setState(contentCategory.getIsParent() ? "closed" : "open");
            resultList.add(treeNode);
        }
        return resultList;

    }

    @Override
    public TaotaoResult insertContentCategroy(Long parentId, String name) {

        //单表操作 我们直接准备数据  但是我们需要返回主键信息 所以我们就要修改mapper文件
        //  <selectKey keyProperty="id" resultType="long" order="AFTER">
        //  SELECT LAST_INSERT_ID()
        //  </selectKey>
        TbContentCategory contentCategory = new TbContentCategory();
        contentCategory.setParentId(parentId);
        contentCategory.setName(name);
        contentCategory.setStatus(1);
        contentCategory.setSortOrder(1);
        contentCategory.setIsParent(false);
        contentCategory.setCreated(new Date());
        contentCategory.setUpdated(new Date());
        int insert = contentCategoryMapper.insert(contentCategory);
        //上面已经添加好了这个节点 接下来就是查看父节点是不是状态也已经改变了  先将数据查询出来直接更改状态
        TbContentCategory parentContentCategory = contentCategoryMapper.selectByPrimaryKey(parentId);
        if (!parentContentCategory.getIsParent()) {
            parentContentCategory.setIsParent(true);
            contentCategoryMapper.updateByPrimaryKey(parentContentCategory);
        }
        //前台数据还需要遍历数据 所以放在TaotaoResult里放过去
        return TaotaoResult.ok(contentCategory);

    }

    @Override
    public TaotaoResult updateContentCategroy(Long id, String name) {
        TbContentCategory contentCategory = contentCategoryMapper.selectByPrimaryKey(id);
        contentCategory.setName(name);
        int i = contentCategoryMapper.updateByPrimaryKey(contentCategory);
        TaotaoResult result = TaotaoResult.ok(contentCategory);
        return result;
    }

    @Override
    public TaotaoResult deleteContentCategroy(Long parentId, Long id) {

        if(parentId==null){
            TbContentCategory contentCategory = contentCategoryMapper.selectByPrimaryKey(id);
            parentId=contentCategory.getParentId();
        }

        //直接删除该节点
        contentCategoryMapper.deleteByPrimaryKey(id);
        //根据条件查询  看看还有没有节点是以这个parentId  作为父节点的 没有就改变父节点的isParent的状态
        //创建查询条件
        TbContentCategoryExample example = new TbContentCategoryExample();
        TbContentCategoryExample.Criteria criteria = example.createCriteria();
        criteria.andParentIdEqualTo(parentId);
        List<TbContentCategory> list = contentCategoryMapper.selectByExample(example);
        if (list==null||list.size()==0){
            //如果删除了干干净净 那就将他的isParent属性变成false
            TbContentCategory parentContentCategory = contentCategoryMapper.selectByPrimaryKey(parentId);
            parentContentCategory.setIsParent(false);
            contentCategoryMapper.updateByPrimaryKey(parentContentCategory);
        }

        return TaotaoResult.ok();

    }

    @Override
    public EUDataGridResult queryContentList(Long categoryId, int page, int rows) {
        //准备好分页插件
        PageHelper.startPage(page,rows);
        //查询category匹配的所有TbContent项
        TbContentExample contentExample = new TbContentExample();
        TbContentExample.Criteria criteria = contentExample.createCriteria();
        criteria.andCategoryIdEqualTo(categoryId);
        List<TbContent> list = contentMapper.selectByExampleWithBLOBs(contentExample);
        //利用工具类获得总共有多少页
        PageInfo<TbContent> info = new PageInfo<>(list);
        EUDataGridResult result = new EUDataGridResult();
        result.setRows(list);
        result.setTotal(info.getTotal());
        return result;


    }
}
