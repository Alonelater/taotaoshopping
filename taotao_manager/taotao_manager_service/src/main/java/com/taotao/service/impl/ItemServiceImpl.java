package com.taotao.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.mapper.TbItemDescMapper;
import com.taotao.mapper.TbItemMapper;
import com.taotao.pojo.EUDataGridResult;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;
import com.taotao.pojo.TbItemExample;
import com.taotao.service.ItemService;
import com.taotao.utils.IDUtils;
import com.taotao.utils.TaotaoResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {


    @Autowired
    private TbItemMapper itemMapper;

    @Autowired
    private TbItemDescMapper tbItemDescMapper;

    @Override

    public TbItem getItemById(Long itemId) {
        return itemMapper.selectByPrimaryKey(itemId);
    }

    @Override
    public EUDataGridResult getItemList(Integer page, Integer rows) {
        //查询商品列表
        TbItemExample example = new TbItemExample();
        //分页处理
        //我们要明白这里的pageHelper就是帮我们在sql语句中添加了分页的字段limit 索引位置和条数 然后啥事没干
        //查还是mapper再查
        PageHelper.startPage(page, rows);
        List<TbItem> list = itemMapper.selectByExample(example);
        //创建一个返回值对象
        EUDataGridResult result = new EUDataGridResult();
        result.setRows(list);
        //取记录总条数
        //查完了之后创建一个pageInfo对象 然后将我们得到的集合传给pageInfo 这样我们的pageInfo就能得到我们的集合对象 能得出记录数传给result
        PageInfo<TbItem> pageInfo = new PageInfo<>(list);
        result.setTotal(pageInfo.getTotal());
        return result;
    }

    //下面我们开始将我们的商品信息存入我们的数据库
    @Override
    public TaotaoResult creatItem(TbItem tbItem, String desc) throws Exception {

        //我们将我们表单没有的值补全就行了
        //首先是商品id
        long itemId = IDUtils.genItemId();
        tbItem.setId(itemId);
        //商品的状态 1表示正常 2.表示下架 3.表示删除
        tbItem.setStatus((byte) 1);
        tbItem.setCreated(new Date());
        tbItem.setUpdated(new Date());
        //保存我们的商品信息  除此之外 我们还要保存我们的商品描述信息 在不同的表 且要支持事务 所以我们要放在
        //我们要将我们的事务放在我们的service  不能放在我们的controller里面  因为那样不是同一个事务了
        int insert = itemMapper.insert(tbItem);
        //调用我们的工具类  帮我们传递信息
        //现在我们插入我们的商品描述到我们的数据库里面
        //我们需要一个其他的方法帮我们做事
        TaotaoResult taotaoResult = insertItemDesc(itemId, desc);
        if (taotaoResult.getStatus()!=200){
            throw new Exception();
        }
        return TaotaoResult.ok();

    }

    private TaotaoResult insertItemDesc(long itemId, String desc) {


        //在这里我们进行商品信息的描述插入
        TbItemDesc tbItemDesc = new TbItemDesc();
        tbItemDesc.setItemDesc(desc);
        tbItemDesc.setItemId(itemId);
        tbItemDesc.setCreated(new Date());
        tbItemDesc.setUpdated(new Date());
        tbItemDescMapper.insert(tbItemDesc);
        return TaotaoResult.ok();
    }
}

