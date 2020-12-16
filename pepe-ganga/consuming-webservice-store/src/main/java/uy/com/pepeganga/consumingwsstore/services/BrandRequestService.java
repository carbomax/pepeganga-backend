package uy.com.pepeganga.consumingwsstore.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;

import uy.com.pepeganga.business.common.entities.Brand;
import uy.com.pepeganga.business.common.entities.UpdatesOfSystem;
import uy.com.pepeganga.business.common.utils.date.DateTimeUtilsBss;
import uy.com.pepeganga.consumingwsstore.conversions.ConvertModels;
import uy.com.pepeganga.consumingwsstore.repositories.IBrandRepository;
import uy.com.pepeganga.consumingwsstore.repositories.IUpdatesSystemRepository;
import uy.com.pepeganga.consumingwsstore.wsdl.marcas.CargaMarcasExecute;
import uy.com.pepeganga.consumingwsstore.wsdl.marcas.CargaMarcasExecuteResponse;

public class BrandRequestService extends WebServiceGatewaySupport {

	@Autowired
	IBrandRepository brandClient;

	@Autowired
	IUpdatesSystemRepository updateSysRepo;
	
	public List<Brand> getBrands() {
		 
		 CargaMarcasExecute request = new CargaMarcasExecute();		 
		 CargaMarcasExecuteResponse response = (CargaMarcasExecuteResponse) getWebServiceTemplate()
		        .marshalSendAndReceive("http://201.217.140.35/agile/acargamarcas.aspx", request);
		 
		 List<Brand> responseList = ConvertModels.convetToBrandEntityList(response.getSdtmarcas().getSdtMarcasSdtMarca());
		 return responseList;
    }
	
	/*Implementar aca evento para que esto se ejecute solo cada cierto tiempo*/
	public boolean storeBrand(UpdatesOfSystem data) {
		try {
			boolean perfect = true;
			List<Brand> brandList = getBrands();

			if (brandList == null || brandList.isEmpty()) {
				logger.warn("Lista de Brands del servicio del almacén vacios o nulos ");
				String data1 = data.getMessage();
				data.setMessage(data1 + " Lista de Brands del servicio del almacén vacios o nulos;");
				data.setEndDate(DateTimeUtilsBss.getDateTimeAtCurrentTime().toDate());
				data.setFinishedSync(false);
				updateSysRepo.save(data);
				return false;
			}

			for (Brand brand : brandList) {
				if (brandClient.save(brand) == null)
					perfect = false;
			}
			// Logear si todo fue almacenado correctamente
			if (!perfect) {
				logger.error("Error almacenando Brands del servicio del almacén");
				String data1 = data.getMessage();
				data.setMessage(data1 + " Error almacenando Brands del servicio del almacén;");
				data.setEndDate(DateTimeUtilsBss.getDateTimeAtCurrentTime().toDate());
				data.setFinishedSync(false);
				updateSysRepo.save(data);
				return false;
			}
			return true;
		}catch (Exception e) {
			logger.error("Error almacenando Brands del servicio del almacén");
			String data1 = data.getMessage();
			data.setMessage(data1 + " Error almacenando Brands del servicio del almacén;");
			data.setEndDate(DateTimeUtilsBss.getDateTimeAtCurrentTime().toDate());
			data.setFinishedSync(false);
			updateSysRepo.save(data);
			return false;
		}
	}
}
