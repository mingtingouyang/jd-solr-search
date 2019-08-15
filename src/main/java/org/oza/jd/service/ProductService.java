package org.oza.jd.service;

import org.oza.jd.vo.ResultModel;

public interface ProductService {
    ResultModel queryProduct(String queryString, String cateName, String price, String sort, Integer curPage) throws Exception;
}
