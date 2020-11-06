package uy.com.pepeganga.consumingwsstore.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;

import uy.com.pepeganga.business.common.entities.UpdatesOfSystem;
import uy.com.pepeganga.business.common.utils.date.DateTimeUtilsBss;
import uy.com.pepeganga.consumingwsstore.entities.TempFamily;
import uy.com.pepeganga.consumingwsstore.entities.TempSubFamily;
import uy.com.pepeganga.consumingwsstore.conversions.ConvertModels;
import uy.com.pepeganga.consumingwsstore.repositories.ITempFamilyRepository;
import uy.com.pepeganga.consumingwsstore.repositories.IUpdatesSystemRepository;
import uy.com.pepeganga.consumingwsstore.wsdl.families.CargaFamiliasExecute;
import uy.com.pepeganga.consumingwsstore.wsdl.families.CargaFamiliasExecuteResponse;


public class FamilyRequestService extends WebServiceGatewaySupport{

	@Autowired
	ITempFamilyRepository tempFamilyClient;

	@Autowired
	IUpdatesSystemRepository updateSysRepo;
	
	 public List<TempFamily> getFamilies() {

		 CargaFamiliasExecute request = new CargaFamiliasExecute();
		 
		 CargaFamiliasExecuteResponse response = (CargaFamiliasExecuteResponse) getWebServiceTemplate()
		        .marshalSendAndReceive("http://201.217.140.35/agile/acargafamilias.aspx", request);
		 
		 List<TempFamily> familyList = ConvertModels.convetToFamilyEntityList(response.getSdtlineassubflias().getSdtLineasSubFliasSdtLineaSubFlias());
		 return familyList;
	}
	 
	 /*Implementar aca evento para que esto se ejecute solo cada cierto tiempo*/
		public boolean storeFamilies(UpdatesOfSystem data) {
			try {
				boolean perfect = true;
				List<TempFamily> familyList = getFamilies();

				if (familyList == null || familyList.isEmpty()) {
					logger.warn("Lista de familias del servicio del almacén vacios o nulos");
					String data1 = data.getMessage();
					data.setMessage(data1 + " Lista de familias del servicio del almacén vacios o nulos;");
					data.setEndDate(DateTimeUtilsBss.getDateTimeAtCurrentTime().toDate());
					data.setFinishedSync(false);
					updateSysRepo.save(data);
					return false;
				}
				for (TempFamily family : familyList) {
					if (tempFamilyClient.save(family) == null)
						perfect = false;
				}
				// Logear si todo fue almacenado correctamente
				if (!perfect) {
					logger.error("Error almacenando familias del servicio del almacén");
					String data1 = data.getMessage();
					data.setMessage(data1 + " Error almacenando familias del servicio del almacén;");
					data.setEndDate(DateTimeUtilsBss.getDateTimeAtCurrentTime().toDate());
					data.setFinishedSync(false);
					updateSysRepo.save(data);
					return false;
				}
				return true;
			}catch (Exception e) {
				logger.error("Error almacenando familias del servicio del almacén");
				String data1 = data.getMessage();
				data.setMessage(data1 + " Error almacenando familias del servicio del almacén;");
				data.setEndDate(DateTimeUtilsBss.getDateTimeAtCurrentTime().toDate());
				data.setFinishedSync(false);
				updateSysRepo.save(data);
				return false;
			}
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
