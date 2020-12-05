package uy.com.pepeganga.consumingwsstore.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;

import uy.com.pepeganga.consumingwsstore.entities.TempCategory;
import uy.com.pepeganga.consumingwsstore.conversions.ConvertModels;
import uy.com.pepeganga.consumingwsstore.repositories.ITempCategoryRepository;
import uy.com.pepeganga.consumingwsstore.wsdl.categories.CargaZafrasExecute;
import uy.com.pepeganga.consumingwsstore.wsdl.categories.CargaZafrasExecuteResponse;


public class CategoryRequestService extends WebServiceGatewaySupport {	
	
	@Autowired
	ITempCategoryRepository categoryClient;
	
	 public List<TempCategory> getCategories() {
		
		 CargaZafrasExecute request = new CargaZafrasExecute();		 
		 CargaZafrasExecuteResponse response = (CargaZafrasExecuteResponse) getWebServiceTemplate()
		        .marshalSendAndReceive("http://201.217.140.35/agile/acargazafras.aspx", request);
		 
		 List<TempCategory> responseList = ConvertModels.convetToCategoryEntityList(response.getSdtcategorias().getSdtCategoriasSdtCategoria());
		 return responseList;
		   
	 }
	 
	 /*Implementar aca evento para que esto se ejecute solo cada cierto tiempo*/
		public void storeCategories() {
			boolean perfect = true;
			List<TempCategory> categoryList = getCategories();
			
			for (TempCategory category : categoryList) {
				if(categoryClient.save(category) == null)
					perfect = false;			
			}		
			// Logear si todo fue almacenado correctamente
		}
}
