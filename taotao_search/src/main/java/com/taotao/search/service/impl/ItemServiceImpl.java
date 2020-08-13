package com.taotao.search.service.impl;

import com.taotao.search.mapper.ItemMapper;
import com.taotao.search.pojo.Item;
import com.taotao.search.service.ItemService;
import com.taotao.utils.ExceptionUtil;
import com.taotao.utils.TaotaoResult;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {


    @Autowired
    private ItemMapper itemMapper;

    //这个是我们没有的 所以我们要自己准备 所以我们再spring里面添加一个bean
    @Autowired
    private SolrServer solrServer;

    /**
     * 做一个所有字段的导入
     *
     * @return
     */
    @Override
    public TaotaoResult importAllItems() {
        try {
            List<Item> itemList = itemMapper.getItemList();
            for (Item item : itemList) {
                //遍历集合准备文档
                SolrInputDocument document = new SolrInputDocument();
                document.addField("id", item.getId());
                document.addField("item_title", item.getTitle());
                document.addField("item_sell_point", item.getSell_point());
                document.addField("item_price", item.getPrice());
                document.addField("item_image", item.getImage());
                document.addField("item_category_name", item.getCategory_name());
                document.addField("item_desc", item.getItem_desc());
                //添加至索引库
                UpdateResponse add = solrServer.add(document);
            }
            solrServer.commit();
        } catch (Exception e) {
            e.printStackTrace();
            return TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
        }
        return TaotaoResult.ok();
    }
}
