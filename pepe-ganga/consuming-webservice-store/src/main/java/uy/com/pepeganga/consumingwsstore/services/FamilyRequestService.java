package uy.com.pepeganga.consumingwsstore.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;

import uy.com.pepeganga.consumingwsstore.entities.TempFamily;
import uy.com.pepeganga.consumingwsstore.entities.TempSubFamily;
import uy.com.pepeganga.consumingwsstore.conversions.ConvertModels;
import uy.com.pepeganga.consumingwsstore.repositories.ITempFamilyRepository;
import uy.com.pepeganga.consumingwsstore.wsdl.families.CargaFamiliasExecute;
import uy.com.pepeganga.consumingwsstore.wsdl.families.CargaFamiliasExecuteResponse;


public class FamilyRequestService extends WebServiceGatewaySupport{

	@Autowired
	ITempFamilyRepository tempFamilyClient;
	
	 public List<TempFamily> getFamilies() {

		 CargaFamiliasExecute request = new CargaFamiliasExecute();
		 
		 CargaFamiliasExecuteResponse response = (CargaFamiliasExecuteResponse) getWebServiceTemplate()
		        .marshalSendAndReceive("http://201.217.140.35/agile/acargafamilias.aspx", request);
		 
		 List<TempFamily> familyList = ConvertModels.convetToFamilyEntityList(response.getSdtlineassubflias().getSdtLineasSubFliasSdtLineaSubFlias());
		 return familyList;
	}
	 
	 /*Implementar aca evento para que esto se ejecute solo cada cierto tiempo*/
		public void storeFamilies() {
			boolean perfect = true;
			List<TempFamily> familyList = getFamilies();
			
			for (TempFamily family : familyList) {
				if(tempFamilyClient.save(family) == null)
					perfect = false;
			}		
			// Logear si todo fue almacenado correctamente	
		}
	 	
	 
 	 public Map<String, String> getFamiliesSubFamilies()
	 {
		 Map<String, String> families = new HashMap<String, String>();
		 List<TempFamily> familyList = getFamilies();
		
		 if(!familyList.isEmpty())
		 {		 
			 for (TempFamily element : familyList) {
				 if(element == null)
					 continue;
				 families.put(Short.toString(element.getId()) , element.getDescription()); 
				 
				 if(!element.getSubfamilies().isEmpty()) {	
					 for (TempSubFamily subelement : element.getSubfamilies()) {
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
