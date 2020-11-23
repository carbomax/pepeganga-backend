package uy.com.pepeganga.consumingwsstore.services;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;

import uy.com.pepeganga.consumingwsstore.ConsumingWebserviceStoreApplication;
import uy.com.pepeganga.consumingwsstore.Entities.TempItem;
import uy.com.pepeganga.consumingwsstore.conversions.ConvertModels;
import uy.com.pepeganga.consumingwsstore.repositories.ITempItemRepository;
import uy.com.pepeganga.consumingwsstore.wsdl.items.CargaArticulosPaginadoExecute;
import uy.com.pepeganga.consumingwsstore.wsdl.items.CargaArticulosPaginadoExecuteResponse;
import uy.com.pepeganga.consumingwsstore.wsdl.items.SDTArticulosWebPagina;


public class ItemRequestService extends WebServiceGatewaySupport{
	
	
	
	private static final Logger logger = LoggerFactory.getLogger(ItemRequestService.class);

	
	@Autowired
	ConsumingWebserviceStoreApplication p;
	
	@Autowired
	ITempItemRepository tempItemClient;
		
	public void deleteItem() {
		TempItem item = new TempItem();
		item.setSku("E0195");
		tempItemClient.delete(item);
	}
	
	public List<TempItem> getItems() {
		
		boolean finish = false;
		List<TempItem> responseList = new ArrayList<TempItem>();
		 
		 CargaArticulosPaginadoExecute request = new CargaArticulosPaginadoExecute();
		 SDTArticulosWebPagina stdItems = new SDTArticulosWebPagina();		
		 
		 short part = 1;
		 do {
			 
			 stdItems.setParte(part);
			 stdItems.setCantidad(100);			 
			 request.setSdtarticuloswebpagina(stdItems);
			 
			 CargaArticulosPaginadoExecuteResponse response = (CargaArticulosPaginadoExecuteResponse) getWebServiceTemplate()
			        .marshalSendAndReceive("http://201.217.140.35/agile/aCargaArticulosPaginado.aspx", request);
			
			 List<TempItem> partialList = ConvertModels.convetToItemEntityList(response.getSdtarticuloswebpagina().getArticulos().getArticulo());
			 responseList.addAll(partialList);
			 part++;
			 
			 if(partialList.size() < 100 || partialList.isEmpty())
				 finish = true;
			 
		} while (!finish);
		 		
		 return responseList;
  	}
	
	/*Implementar aca evento para que esto se ejecute solo cada cierto tiempo*/
	public void storeItems() {
		try{
			List<TempItem> itemList = getItems();
			tempItemClient.saveAll(itemList);
			logger.info("Items saved successfully: {}", itemList.size());
		}catch (Exception e){
			logger.error("Error almacenando datos", e.getMessage());
		}


					
		
	}	


}
