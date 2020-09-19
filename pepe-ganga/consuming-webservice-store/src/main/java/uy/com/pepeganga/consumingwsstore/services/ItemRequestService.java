package uy.com.pepeganga.consumingwsstore.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;

import uy.com.pepeganga.consumingwsstore.ConsumingWebserviceStoreApplication;
import uy.com.pepeganga.consumingwsstore.conversions.ConvertModels;
import uy.com.pepeganga.consumingwsstore.entities.Image;
import uy.com.pepeganga.consumingwsstore.entities.Item;
import uy.com.pepeganga.consumingwsstore.repositories.IImageRepository;
import uy.com.pepeganga.consumingwsstore.repositories.IItemRepository;
import uy.com.pepeganga.consumingwsstore.wsdl.items.CargaArticulosExecute;
import uy.com.pepeganga.consumingwsstore.wsdl.items.CargaArticulosExecuteResponse;
import uy.com.pepeganga.consumingwsstore.wsdl.items.ConsSDTArticulosWebParcialArticulos;
import uy.com.pepeganga.consumingwsstore.wsdl.items.SDTArticulosWebParcial;


public class ItemRequestService extends WebServiceGatewaySupport{
	
	@Autowired
	ConsumingWebserviceStoreApplication p;
	
	@Autowired
	IItemRepository itemClient;
	
	@Autowired
	IImageRepository imageClient;
	
	public List<Item> getItems() {
		 
		 CargaArticulosExecute request = new CargaArticulosExecute();
		 SDTArticulosWebParcial stdItems = new SDTArticulosWebParcial();
		 ConsSDTArticulosWebParcialArticulos consSTD = new ConsSDTArticulosWebParcialArticulos();
		 stdItems.setParte((byte)9);
		 stdItems.setArticulos(consSTD);		 
		 request.setSdtarticuloswebparcial(stdItems);
		 
		 CargaArticulosExecuteResponse response = (CargaArticulosExecuteResponse) getWebServiceTemplate()
		        .marshalSendAndReceive("http://201.217.140.35/agile15/acargaarticulos.aspx", request);
		  
		 List<Item> responseList = ConvertModels.convetToItemEntityList(response.getSdtarticuloswebparcial().getArticulos().getArticulo());
		 return responseList;
  	}
	
	/*Implementar aca evento para que esto se ejecute solo cada cierto tiempo*/
	public void storeItems() {
		List<Item> itemList = getItems();
		List<Item> temporalList = new ArrayList<Item>();
		
		for (Item item : itemList) {
			List<Image>imageList = imageClient.saveAll(item.getImage());
			item.setImage(imageList);
			temporalList.add(item);
		}
		
		itemClient.saveAll(temporalList);		
	}
	
	/*Metodo Temporal*/
	public void storeItemsTemporally() {
		List<Item> itemList = p.getList();
		List<Item> temporalList = new ArrayList<Item>();
		
		for (Item item : itemList) {
			List<Image>imageList = imageClient.saveAll(item.getImage());
			item.setImage(imageList);
			temporalList.add(item);
		}
		
		itemClient.saveAll(temporalList);		
	}
}
