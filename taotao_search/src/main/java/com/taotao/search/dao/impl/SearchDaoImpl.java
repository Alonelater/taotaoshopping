package com.taotao.search.dao.impl;

import com.taotao.search.dao.SearchDao;
import com.taotao.search.pojo.Item;
import com.taotao.search.pojo.SearchResult;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * 查询的dao层 用来将我们查询到的结果返回
 */
@Repository
public class SearchDaoImpl implements SearchDao {

    @Autowired
    private SolrServer solrServer;


    @Override
    public SearchResult searchByQuery(SolrQuery query) throws Exception {

        SearchResult result = new SearchResult();

        //根据上面传下来的查询条件直接查询 对查询结果做完操作后直接返回
        QueryResponse response = solrServer.query(query);
        //获取查询结果的集合
        SolrDocumentList results = response.getResults();

        //获取查询结果的总记录数  并且封装在SearchResult里面
        result.setRecordCount(results.getNumFound());
        //创建集合 帮我们封装数据
        List<Item> resultList = new ArrayList<>();
        //对商品进行高亮信息显示
        Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();



        for (SolrDocument document : results) {
            Item item = new Item();
            item.setId((String) document.get("id"));

            //接下来对取得的结果进行高亮处理 我们根据测试得到这样的结果 所以我们这样包装  第一层是是id 以此类推
            // "highlighting": {
            //    "536563": {
            //      "item_title": [
            //        "new2 - <em>阿尔卡特</em> (OT-927) 炭黑 联通3G手机 双卡双待"
            //      ]
            //    }
            //这个就相当于得到了{
            //            //      "item_title": [
            //            //        "new2 - <em>阿尔卡特</em> (OT-927) 炭黑 联通3G手机 双卡双待"
            //            //      ]
            //            //    }
            Map<String, List<String>> id = highlighting.get(document.get("id"));
            //得到item_title 对应的集合
            List<String> list= id.get("item_title");
            String title = "";
            if(list!=null&&list.size()>0){
                title= list.get(0);
            }else {
                //可能标题中并没有出现关键词  那就直接展示原有标题 不加高亮效果
                title=(String) document.get("item_title");
            }
            item.setTitle(title);
            item.setImage((String) document.get("item_image"));
            item.setPrice((Long) document.get("item_price"));
            item.setSell_point((String) document.get("item_sell_point"));
            item.setCategory_name((String) document.get("item_categroy_name"));
            item.setItem_desc((String) document.get("item_desc"));
            resultList.add(item);
        }
        result.setItemList(resultList);
        return result ;
    }
}
