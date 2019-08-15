package org.oza.jd.service.impl;

import org.apache.solr.common.params.MapSolrParams;
import org.oza.jd.dao.ProductDao;
import org.oza.jd.service.ProductService;
import org.oza.jd.vo.ResultModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductDao productDao;

    @Override
    public ResultModel queryProduct(String queryString, String cateName, String price, String sort, Integer curPage) throws Exception {
        //构建一个map封装搜索条件
        Map<String, String> queryMap = new HashMap<>();

        //判断关键字是否为空，为空则查询全部
        if (null != queryString && !queryString.trim().equals("")){
            queryMap.put("q", queryString);
        } else {
            queryMap.put("q", "*");
        }

        //指定搜索的域
        queryMap.put("df", "product_name");

        //设置过滤条件-产品类型
        if (null != cateName && !cateName.trim().equals("")) {
            queryMap.put("fq", "product_category_name:" + cateName);
        }

        //设置过滤的条件 - price 范围，格式： 10 -20
        if (null != price && !price.trim().equals("")) {
            String[] priceScope = price.split("-");
            if (queryMap.get("fq") != null) {
                queryMap.put("fq", queryMap.get("fq") + " AND product_price:[" + priceScope[0] + " TO " + priceScope[1] + "]");
            } else {
                queryMap.put("fq", "product_price:[" + priceScope[0] + " TO " + priceScope[1] + "]");
            }
        }

        //设置排序: 1-降序
        if ("1".equals(sort)) {
            queryMap.put("sort", "product_price desc");
        } else {
            queryMap.put("sort", "product_price asc");
        }

        //分页设置
        if (null == curPage) {
            curPage = 1;
        }

        //获取分页后记录
        queryMap.put("start", String.valueOf((curPage -1) * 20));
        queryMap.put("rows", "20");

        //设置高亮
        queryMap.put("hl", "on");
        queryMap.put("hl.fl", "product_name");
        queryMap.put("hl.simple.pre", "<font style=\"color:red\">");
        queryMap.put("hl.simple.post", "</font>");

        //创建 query 对象
        MapSolrParams query = new MapSolrParams(queryMap);
        //检索数据
        ResultModel resultModel = productDao.queryProduct(query);

        //封装数据
        //当前页
        resultModel.setCurrentPage(curPage);
        //总页数
        double ceil = Math.ceil(resultModel.getRecordCount() / 20.0);
        resultModel.setPageCount((int)ceil);

        return resultModel;
    }
}
