package org.oza.jd.dao.impl;

import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.params.MapSolrParams;
import org.oza.jd.dao.ProductDao;
import org.oza.jd.pojo.Product;
import org.oza.jd.vo.ResultModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class ProductDaoImpl implements ProductDao {

    @Autowired
    private HttpSolrClient solrClient;

    @Override
    public ResultModel queryProduct(MapSolrParams query) throws SolrServerException, IOException {
        //页面读取的对象
        ResultModel resultModel = new ResultModel();

        //设置条件到连接对象里面并查询
        QueryResponse response = solrClient.query("product", query);
        //获得结果
        SolrDocumentList documents = response.getResults();
        //将结果转换成 product 集合
        List<Product> producuts = new ArrayList<>();
        //获得高亮信息
        Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();

        //执行转换
        documents.forEach(document -> {
            Product product = new Product();
            //获得高亮数据
            final List<String> highlightWords = highlighting.get(document.get("id")).get("product_name");

            if (null != highlightWords && highlightWords.size() > 0) {
                //这里只需要获得第一条数据
                product.setName(highlightWords.get(0));
            } else {
                product.setName(String.valueOf(document.get("product_name")));
            }

            product.setPid(Integer.valueOf(String.valueOf(document.get("id"))));
            product.setCategoryName(String.valueOf(document.get("product_category_name")));
            product.setPrice(Double.valueOf(String.valueOf(document.get("product_price"))));
            product.setPicture(String.valueOf(document.get("product_picture")));
            product.setDescription(String.valueOf(document.get("product_description")));
            producuts.add(product);
        });

        //添加数据到 resultModel
        resultModel.setProductList(producuts);
        //获取总记录数
        resultModel.setRecordCount(documents.getNumFound());

        return resultModel;
    }
}
