package com.taotao.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.mapper.TbItemParamExtendMapper;
import com.taotao.mapper.TbItemParamMapper;
import com.taotao.pojo.*;
import com.taotao.service.ItemParamService;
import com.taotao.utils.TaotaoResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 这是商品规格项定制模板的实现类
 *
 */
@Service
public class ItemParamServiceImpl implements ItemParamService {

    @Autowired
    private TbItemParamMapper tbItemParamMapper;

    @Autowired
    private TbItemParamExtendMapper tbItemParamExtendMapper;

    /**
     * @param cid  商品类目的编号
     * @return
     */
    @Override
    public TaotaoResult getItemParamByCid(Long cid) {
        TbItemParamExample example = new TbItemParamExample();
        TbItemParamExample.Criteria criteria = example.createCriteria();
        criteria.andItemCatIdEqualTo(cid);
        List<TbItemParam> tbItemParams = tbItemParamMapper.selectByExampleWithBLOBs(example);
        //如果查询到了数据就将数据传给Taotaoresult  如果里面没有数据那么也传一个ok  但是此时里面没有数据所以前台界面判断也不成立

        if (tbItemParams!=null&&tbItemParams.size()>0){
            TaotaoResult taotaoResult = TaotaoResult.ok(tbItemParams.get(0));
            return taotaoResult;

        }
        return TaotaoResult.ok();
    }

    @Override
    public EUDataGridResult getItemParamList(Integer page, Integer rows) {

        PageHelper.startPage(page, rows);
        //自定义了一个mapper类
        List<TbItemParamExtend> list = tbItemParamExtendMapper.selectItemParamExtendList();
        //创建一个返回值对象
        EUDataGridResult result = new EUDataGridResult();
        result.setRows(list);
        //取记录总条数
        //查完了之后创建一个pageInfo对象 然后将我们得到的集合传给pageInfo 这样我们的pageInfo就能得到我们的集合对象 能得出记录数传给result
        PageInfo<TbItemParamExtend> pageInfo = new PageInfo<>(list);
        result.setTotal(pageInfo.getTotal());
        return result;
    }

    @Override
    public TaotaoResult insertItemParam(TbItemParam itemParam) {
        int insert = tbItemParamMapper.insert(itemParam);
        if (insert==1){
            return TaotaoResult.ok();
        }
        return TaotaoResult.ok();
    }
}
