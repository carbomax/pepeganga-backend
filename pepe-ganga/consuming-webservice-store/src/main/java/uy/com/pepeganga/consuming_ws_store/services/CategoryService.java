package uy.com.pepeganga.consuming_ws_store.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import uy.com.pepeganga.consuming_ws_store.wsdl.CargaZafrasExecute;
import uy.com.pepeganga.consuming_ws_store.wsdl.CargaZafrasExecuteResponse;

public class CategoryService extends WebServiceGatewaySupport {

	 private static final Logger log = LoggerFactory.getLogger(CategoryService.class);
	 
	 public CargaZafrasExecuteResponse getCategories() {

		 CargaZafrasExecute request = new CargaZafrasExecute();
		 
		 CargaZafrasExecuteResponse response = (CargaZafrasExecuteResponse) getWebServiceTemplate()
		        .marshalSendAndReceive("http://201.217.140.35/agile/acargazafras.aspx", request);
		    return response;
		  }
}
