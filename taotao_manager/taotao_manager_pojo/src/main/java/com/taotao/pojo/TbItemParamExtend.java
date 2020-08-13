package com.taotao.pojo;

/**
 * 这个类是规格参数分类项的扩展类  因为在查询规格模板参数的时候
 */
public class TbItemParamExtend extends TbItemParam {


    private String itemCatName;

    public String getItemCatName() {
        return itemCatName;
    }

    public void setItemCatName(String itemCatName) {
        this.itemCatName = itemCatName;
    }
}
