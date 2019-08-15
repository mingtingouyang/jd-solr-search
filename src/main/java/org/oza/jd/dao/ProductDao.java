package org.oza.jd.dao;

import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.params.MapSolrParams;
import org.oza.jd.vo.ResultModel;

import java.io.IOException;

public interface ProductDao {
    ResultModel queryProduct(MapSolrParams query) throws SolrServerException, IOException;
}
