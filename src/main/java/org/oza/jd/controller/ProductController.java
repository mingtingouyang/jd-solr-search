package org.oza.jd.controller;

import org.oza.jd.service.ProductService;
import org.oza.jd.vo.ResultModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @RequestMapping("/list")
    public String search(@RequestParam(required = false) String queryString,
                         @RequestParam(value = "catalog_name", required = false)String cateName,
                         @RequestParam(required = false) String price,
                         @RequestParam(required = false) String sort,
                         @RequestParam(required = false) Integer curPage,
                         Model model){
        try {
            ResultModel resultModel = productService.queryProduct(queryString, cateName, price, sort, curPage);
            //封装显示的数据到 Model
            model.addAttribute("queryString", queryString);
            model.addAttribute("result", resultModel);
            model.addAttribute("catalog_name", cateName);
            model.addAttribute("price", price);
            model.addAttribute("sort", sort);
            model.addAttribute("page", curPage);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "product";
    }
}
