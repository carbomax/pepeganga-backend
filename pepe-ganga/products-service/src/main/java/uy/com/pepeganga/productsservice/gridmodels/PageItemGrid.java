package uy.com.pepeganga.productsservice.gridmodels;

import java.util.List;

import org.springframework.data.domain.Sort;

public class PageItemGrid {

	private List<ItemGrid> itemsGrid;
	
	private int totalPages;
	
	private long totalElements;
	
	private boolean last;
	
	private boolean first;
	
	private Sort sort;
	
	private long numberOfElements;

	private long totalProducts;

	public long getTotalProducts() {
		return totalProducts;
	}

	public void setTotalProducts(long totalProducts) {
		this.totalProducts = totalProducts;
	}

	public List<ItemGrid> getItemsGrid() {
		return itemsGrid;
	}

	public void setItemsGrid(List<ItemGrid> itemsGrid) {
		this.itemsGrid = itemsGrid;
	}

	public int getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}
	

	public long getTotalElements() {
		return totalElements;
	}

	public void setTotalElements(long totalElements) {
		this.totalElements = totalElements;
	}


	public boolean isLast() {
		return last;
	}

	public void setLast(boolean last) {
		this.last = last;
	}

	public boolean isFirst() {
		return first;
	}

	public void setFirst(boolean first) {
		this.first = first;
	}

	public long getNumberOfElements() {
		return numberOfElements;
	}

	public void setNumberOfElements(long numberOfElements) {
		this.numberOfElements = numberOfElements;
	}

	public Sort getSort() {
		return sort;
	}

	public void setSort(Sort sort) {
		this.sort = sort;
	}
	
	
}
