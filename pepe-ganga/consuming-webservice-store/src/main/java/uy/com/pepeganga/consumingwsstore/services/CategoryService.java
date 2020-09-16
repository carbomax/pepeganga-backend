package uy.com.pepeganga.consumingwsstore.services;

import java.util.List;

import org.springframework.ws.client.core.support.WebServiceGatewaySupport;

import uy.com.pepeganga.consumingwsstore.conversions.ConvertModels;
import uy.com.pepeganga.consumingwsstore.models.CategoryModelResponse;
import uy.com.pepeganga.consumingwsstore.wsdl.categories.CargaZafrasExecute;
import uy.com.pepeganga.consumingwsstore.wsdl.categories.CargaZafrasExecuteResponse;

public class CategoryService extends WebServiceGatewaySupport {	
	 
	 public List<CategoryModelResponse> getCategories() {
		 
		 CargaZafrasExecute request = new CargaZafrasExecute();		 
		 CargaZafrasExecuteResponse response = (CargaZafrasExecuteResponse) getWebServiceTemplate()
		        .marshalSendAndReceive("http://201.217.140.35/agile/acargazafras.aspx", request);
		 
		 List<CategoryModelResponse> responseList = ConvertModels.convetToCategoryModelList(response.getSdtcategorias().getSdtCategoriasSdtCategoria());
		 return responseList;
		   
	 }
}
