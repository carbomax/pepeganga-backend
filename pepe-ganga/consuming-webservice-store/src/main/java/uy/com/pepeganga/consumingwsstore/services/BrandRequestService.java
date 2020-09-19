package uy.com.pepeganga.consumingwsstore.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;

import uy.com.pepeganga.consumingwsstore.conversions.ConvertModels;
import uy.com.pepeganga.consumingwsstore.entities.Brand;
import uy.com.pepeganga.consumingwsstore.repositories.IBrandRepository;
import uy.com.pepeganga.consumingwsstore.wsdl.marcas.CargaMarcasExecute;
import uy.com.pepeganga.consumingwsstore.wsdl.marcas.CargaMarcasExecuteResponse;

public class BrandRequestService extends WebServiceGatewaySupport {

	@Autowired
	IBrandRepository brandClient;
	
	public List<Brand> getBrands() {
		 
		 CargaMarcasExecute request = new CargaMarcasExecute();		 
		 CargaMarcasExecuteResponse response = (CargaMarcasExecuteResponse) getWebServiceTemplate()
		        .marshalSendAndReceive("http://201.217.140.35/agile/acargamarcas.aspx", request);
		 
		 List<Brand> responseList = ConvertModels.convetToBrandEntityList(response.getSdtmarcas().getSDTMarcasSDTMarca());
		 return responseList;
    }
	
	/*Implementar aca evento para que esto se ejecute solo cada cierto tiempo*/
	public void storeBrand() {
		List<Brand> brandList = getBrands();		
		brandClient.saveAll(brandList);		
	}
}
