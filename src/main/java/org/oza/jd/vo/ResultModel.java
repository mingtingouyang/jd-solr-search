package org.oza.jd.vo;

import org.oza.jd.pojo.Product;

import java.util.List;



public class ResultModel {
	private List<Product> productList;
	// 商品总数
	private Long recordCount;
	// 总页数
	private int pageCount;
	// 当前页
	private int currentPage;

	public List<Product> getProductList() {
		return productList;
	}

	public void setProductList(List<Product> productList) {
		this.productList = productList;
	}

	public Long getRecordCount() {
		return recordCount;
	}

	public void setRecordCount(Long recordCount) {
		this.recordCount = recordCount;
	}

	public int getPageCount() {
		return pageCount;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

}
