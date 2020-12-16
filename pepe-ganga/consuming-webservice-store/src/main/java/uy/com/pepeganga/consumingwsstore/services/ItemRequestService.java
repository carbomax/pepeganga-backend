package uy.com.pepeganga.consumingwsstore.services;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;

import uy.com.pepeganga.business.common.entities.Item;
import uy.com.pepeganga.business.common.entities.UpdatesOfSystem;
import uy.com.pepeganga.business.common.utils.date.DateTimeUtilsBss;
import uy.com.pepeganga.consumingwsstore.ConsumingWebserviceStoreApplication;
import uy.com.pepeganga.consumingwsstore.conversions.ConvertModels;
import uy.com.pepeganga.consumingwsstore.repositories.IItemRepository;
import uy.com.pepeganga.consumingwsstore.repositories.IUpdatesSystemRepository;
import uy.com.pepeganga.consumingwsstore.wsdl.items.CargaArticulosPaginadoExecute;
import uy.com.pepeganga.consumingwsstore.wsdl.items.CargaArticulosPaginadoExecuteResponse;
import uy.com.pepeganga.consumingwsstore.wsdl.items.SDTArticulosWebPagina;


public class ItemRequestService extends WebServiceGatewaySupport{
	
	
	
	private static final Logger logger = LoggerFactory.getLogger(ItemRequestService.class);

	
	@Autowired
	ConsumingWebserviceStoreApplication p;
	
	@Autowired
	IItemRepository itemClient;

	@Autowired
	IUpdatesSystemRepository updateSysRepo;
		
	public void deleteItem() {
		Item item = new Item();
		item.setSku("E0195");
		itemClient.delete(item);
	}
	
	public List<Item> getItems() {
		
		boolean finish = false;
		List<Item> responseList = new ArrayList<>();
		 
		 CargaArticulosPaginadoExecute request = new CargaArticulosPaginadoExecute();
		 SDTArticulosWebPagina stdItems = new SDTArticulosWebPagina();		
		 
		 short part = 1;
		 do {
			 
			 stdItems.setParte(part);
			 stdItems.setCantidad(100);			 
			 request.setSdtarticuloswebpagina(stdItems);
			 
			 CargaArticulosPaginadoExecuteResponse response = (CargaArticulosPaginadoExecuteResponse) getWebServiceTemplate()
			        .marshalSendAndReceive("http://201.217.140.35/agile/aCargaArticulosPaginado.aspx", request);
			
			 List<Item> partialList = ConvertModels.convetToItemEntityList(response.getSdtarticuloswebpagina().getArticulos().getArticulo());
			 responseList.addAll(partialList);
			 part++;

			 if(response.getSdtarticuloswebpagina().getArticulos().getArticulo().size() < 100 )
				 finish = true;
			 
		} while (!finish);
		 		
		 return responseList;
  	}
	
	/*Implementar aca evento para que esto se ejecute solo cada cierto tiempo*/
	public boolean storeItems(UpdatesOfSystem data) {
		try{
			List<Item> itemList = getItems();
			if(itemList == null || itemList.isEmpty()) {
				logger.warn("No se lograron obtener productos del servicio del almacén");
				String data1 = data.getMessage();
				data.setMessage(data1 + " No se lograron obtener productos del servicio del almacén;");
				data.setEndDate(DateTimeUtilsBss.getDateTimeAtCurrentTime().toDate());
				data.setFinishedSync(false);
				updateSysRepo.save(data);
				return false;
			}
			itemClient.saveAll(itemList);
			logger.info("Items saved successfully: {}", itemList.size());
			return true;
		}catch (Exception e){
			logger.error("Error almacenando items del almacén en base datos", e.getMessage());
			String data1 = data.getMessage();
			data.setMessage(data1 + " No se lograron obtener productos del servicio del almacén;");
			data.setEndDate(DateTimeUtilsBss.getDateTimeAtCurrentTime().toDate());
			data.setFinishedSync(false);
			updateSysRepo.save(data);
			return false;
		}


					
		
	}	


}
