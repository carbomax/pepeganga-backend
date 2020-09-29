package uy.com.pepeganga.consumingwsstore.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;

import uy.com.pepeganga.consumingwsstore.conversions.ConvertModels;
import uy.com.pepeganga.business.common.entities.*;
import uy.com.pepeganga.consumingwsstore.repositories.ICategoryRepository;
import uy.com.pepeganga.consumingwsstore.wsdl.categories.CargaZafrasExecute;
import uy.com.pepeganga.consumingwsstore.wsdl.categories.CargaZafrasExecuteResponse;


public class CategoryRequestService extends WebServiceGatewaySupport {	
	
	@Autowired
	ICategoryRepository categoryClient;
	
	 public List<Category> getCategories() {
		
		 CargaZafrasExecute request = new CargaZafrasExecute();		 
		 CargaZafrasExecuteResponse response = (CargaZafrasExecuteResponse) getWebServiceTemplate()
		        .marshalSendAndReceive("http://201.217.140.35/agile/acargazafras.aspx", request);
		 
		 List<Category> responseList = ConvertModels.convetToCategoryEntityList(response.getSdtcategorias().getSdtCategoriasSdtCategoria());
		 return responseList;
		   
	 }
	 
	 /*Implementar aca evento para que esto se ejecute solo cada cierto tiempo*/
		public void storeCategories() {
			List<Category> categoryList = getCategories();		
			categoryClient.saveAll(categoryList);		
		}
}
