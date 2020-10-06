package uy.com.pepeganga.productsservice.models;

import java.util.List;

import uy.com.pepeganga.business.common.utils.enums.ActionResult;


public class SelectedProducResponse {

	private List<String> existingProducts;
	private ActionResult codeResult;
	
	public ActionResult getCodeResult() {
		return codeResult;
	}
	public void setCodeResult(ActionResult codeResult) {
		this.codeResult = codeResult;
	}
	public List<String> getExistingProducts() {
		return existingProducts;
	}
	public void setExistingProducts(List<String> existingProducts) {
		this.existingProducts = existingProducts;
	}	
	
}
