package uy.com.pepeganga.consumingwsstore.services;

import java.util.HashMap;
import java.util.Map;

import org.springframework.util.CollectionUtils;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;

import uy.com.pepeganga.consumingwsstore.wsdl.families.CargaFamiliasExecute;
import uy.com.pepeganga.consumingwsstore.wsdl.families.CargaFamiliasExecuteResponse;
import uy.com.pepeganga.consumingwsstore.wsdl.families.SdtLineasSubFliasSdtLineaSubFlias;
import uy.com.pepeganga.consumingwsstore.wsdl.families.SdtSubFliasSdtSubFlia;

public class FamilyService extends WebServiceGatewaySupport{

	 public CargaFamiliasExecuteResponse getFamilies() {

		 CargaFamiliasExecute request = new CargaFamiliasExecute();
		 
		 CargaFamiliasExecuteResponse response = (CargaFamiliasExecuteResponse) getWebServiceTemplate()
		        .marshalSendAndReceive("http://201.217.140.35/agile/acargafamilias.aspx", request);
		    return response;
		  }
	 
	 public Map<String, String> getFamiliesSubFamilies()
	 {
		 Map<String, String> families = new HashMap<String, String>();
		 CargaFamiliasExecuteResponse response = getFamilies();		 
		
		 if(response != null && response.getSdtlineassubfamilias() != null && !CollectionUtils.isEmpty(response.getSdtlineassubfamilias().getSdtLineasSubFliasSdtLineaSubFlias()))
		 {		 
			 for (SdtLineasSubFliasSdtLineaSubFlias element : response.getSdtlineassubfamilias().getSdtLineasSubFliasSdtLineaSubFlias()) {
				 if(element == null)
					 continue;
				 families.put(Short.toString(element.getLinId()) , element.getLinDsc()); 
				 
				 if(element.getSdtSubFlias() == null)
					 continue;
				 if(!CollectionUtils.isEmpty(element.getSdtSubFlias().getSdtSubFliasSdtSubFlia()))
					 for (SdtSubFliasSdtSubFlia subelement : element.getSdtSubFlias().getSdtSubFliasSdtSubFlia()) {						
						 families.put(Short.toString(element.getLinId()) + "-" + Short.toString(subelement.getSubFliaId()), subelement.getSubFliaDsc()); 
				 	}
				 }		 
		 }
		 return families;
	 }
	 
}
