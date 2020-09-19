package uy.com.pepeganga.consumingwsstore.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;

import uy.com.pepeganga.consumingwsstore.conversions.ConvertModels;
import uy.com.pepeganga.consumingwsstore.entities.Family;
import uy.com.pepeganga.consumingwsstore.entities.SubFamily;
import uy.com.pepeganga.consumingwsstore.repositories.IFamilyRepository;
import uy.com.pepeganga.consumingwsstore.repositories.ISubFamilyRepository;
import uy.com.pepeganga.consumingwsstore.wsdl.families.CargaFamiliasExecute;
import uy.com.pepeganga.consumingwsstore.wsdl.families.CargaFamiliasExecuteResponse;


public class FamilyRequestService extends WebServiceGatewaySupport{

	@Autowired
	IFamilyRepository familyClient;
	ISubFamilyRepository subfamilyClient;
	
	 public List<Family> getFamilies() {

		 CargaFamiliasExecute request = new CargaFamiliasExecute();
		 
		 CargaFamiliasExecuteResponse response = (CargaFamiliasExecuteResponse) getWebServiceTemplate()
		        .marshalSendAndReceive("http://201.217.140.35/agile/acargafamilias.aspx", request);
		 
		 List<Family> familyList = ConvertModels.convetToFamilyEntityList(response.getSdtlineassubfamilias().getSdtLineasSubFliasSdtLineaSubFlias());
		 return familyList;
	}
	 
	 /*Implementar aca evento para que esto se ejecute solo cada cierto tiempo*/
		public void storeFamilies() {
			List<Family> familyList = getFamilies();			
			familyClient.saveAll(familyList);		
		}
	 	
	 
 	 public Map<String, String> getFamiliesSubFamilies()
	 {
		 Map<String, String> families = new HashMap<String, String>();
		 List<Family> familyList = getFamilies();		 
		
		 if(!familyList.isEmpty())
		 {		 
			 for (Family element : familyList) {
				 if(element == null)
					 continue;
				 families.put(Short.toString(element.getId()) , element.getDescription()); 
				 
				 if(!element.getSubfamilies().isEmpty()) {	
					 for (SubFamily subelement : element.getSubfamilies()) {
						 if(subelement == null)
							 continue;
						 families.put(Short.toString(element.getId()) + "-" + Short.toString(subelement.getId()), subelement.getDescription()); 
					 }						
				 }
			}		 
		 }
		 return families;
	 }
	 	 
}
