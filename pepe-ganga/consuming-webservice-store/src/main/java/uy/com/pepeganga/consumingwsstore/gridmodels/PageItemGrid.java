package uy.com.pepeganga.consumingwsstore.gridmodels;

import org.springframework.data.domain.Sort;

import java.util.List;

public class PageItemGrid {

	private List<ItemGrid> itemsGrid;
	
	private int totalPages;
	
	private long totalElements;
	
	private boolean last;
	
	private boolean first;
	
	private Sort sort;
	
	private int numberOfElements;


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

	public int getNumberOfElements() {
		return numberOfElements;
	}

	public void setNumberOfElements(int numberOfElements) {
		this.numberOfElements = numberOfElements;
	}

	public Sort getSort() {
		return sort;
	}

	public void setSort(Sort sort) {
		this.sort = sort;
	}
	
	
}
