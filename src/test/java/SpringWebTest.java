import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.SolrPingResponse;
import org.apache.solr.common.SolrDocumentList;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.oza.jd.config.SpringMvcConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.io.IOException;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration //web项目一定要加这句
@ContextConfiguration(classes = SpringMvcConfig.class)
public class SpringWebTest {

    @Autowired
    private HttpSolrClient httpSolrClient;

    @Test
    public void connectionTest() throws IOException, SolrServerException {
        SolrQuery query = new SolrQuery();
        query.set("q", "音乐盒");
        query.set("df", "product_name");
        query.set("fq", "product_category_name:幽默杂货");
        QueryResponse response = httpSolrClient.query("product", query);
        SolrDocumentList results = response.getResults();
        results.forEach(result -> {
            System.out.println(result.get("product_name"));
        });
    }
}
