import com.github.pagehelper.parser.SqlServer;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;

import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

import java.io.IOException;

/**
 *
 * 这个类主要用来测试SolrJ的 用来用java代码增删查改索引 */
public class TestSolrJ {


    //增加solr索引
    @Test
    public void insertDocument() throws IOException, SolrServerException {
        //1.创建连接
      SolrServer solrServer = new HttpSolrServer("http://192.168.1.120:8080/solr");
      //创建文档对象
        SolrInputDocument document = new SolrInputDocument();
        document.addField("id","test001");
        document.addField("item_title","测试商品1");
        document.addField("item_price",123456);
        //将文档写入索引库
       solrServer.add(document);
        //提交
        solrServer.commit();

    }

    //修改solr索引

    @Test
    public void updateDocument() throws IOException, SolrServerException {
        //1.创建连接
        SolrServer solrServer = new HttpSolrServer("http://192.168.1.120:8080/solr");
        //创建文档对象
        SolrInputDocument document = new SolrInputDocument();
        document.addField("id","test001");
        document.addField("item_title","测试商品2");
        document.addField("item_price",6543216);
        //将文档写入索引库
        solrServer.add(document);
        //提交
        solrServer.commit();

    }

    //
    @Test
    public void deleteDocument() throws IOException, SolrServerException {
        //1.创建连接
        SolrServer solrServer = new HttpSolrServer("http://192.168.1.120:8080/solr");

        //删除id为testoo1的索引
        solrServer.deleteById("test001");
        //根据条件删除
        //solrServer.deleteByQuery("*:*");
        //提交
        solrServer.commit();
        //或者是sqlServer


    }

    //为了更好灵活的使用solr服务 所以我们再创建一个taotao_search

}
