package uy.com.pepeganga.consuming_ws_store.services;

import org.springframework.ws.client.core.support.WebServiceGatewaySupport;

import uy.com.pepeganga.consuming_ws_store.wsdl.families.CargaFamiliasExecute;
import uy.com.pepeganga.consuming_ws_store.wsdl.families.CargaFamiliasExecuteResponse;

public class FamilyService extends WebServiceGatewaySupport{

	 public CargaFamiliasExecuteResponse getFamilies() {

		 CargaFamiliasExecute request = new CargaFamiliasExecute();
		 
		 CargaFamiliasExecuteResponse response = (CargaFamiliasExecuteResponse) getWebServiceTemplate()
		        .marshalSendAndReceive("http://201.217.140.35/agile/acargafamilias.aspx", request);
		    return response;
		  }
}
