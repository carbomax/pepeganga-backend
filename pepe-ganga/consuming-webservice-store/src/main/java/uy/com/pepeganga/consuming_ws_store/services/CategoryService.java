package uy.com.pepeganga.consuming_ws_store.services;

import org.springframework.ws.client.core.support.WebServiceGatewaySupport;

import uy.com.pepeganga.consuming_ws_store.wsdl.categories.CargaZafrasExecute;
import uy.com.pepeganga.consuming_ws_store.wsdl.categories.CargaZafrasExecuteResponse;

public class CategoryService extends WebServiceGatewaySupport {	
	 
	 public CargaZafrasExecuteResponse getCategories() {
		 
		 CargaZafrasExecute request = new CargaZafrasExecute();		 
		 CargaZafrasExecuteResponse response = (CargaZafrasExecuteResponse) getWebServiceTemplate()
		        .marshalSendAndReceive("http://201.217.140.35/agile/acargazafras.aspx", request);
		    return response;
		  }
}
