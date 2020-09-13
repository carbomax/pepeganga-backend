package uy.com.pepeganga.consumingwsstore.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;

import uy.com.pepeganga.consumingwsstore.ConsumingWebserviceStoreApplication;
import uy.com.pepeganga.consumingwsstore.conversions.ConvertModels;
import uy.com.pepeganga.consumingwsstore.models.ItemModelResponse;
import uy.com.pepeganga.consumingwsstore.wsdl.items.CargaArticulosExecute;
import uy.com.pepeganga.consumingwsstore.wsdl.items.CargaArticulosExecuteResponse;
import uy.com.pepeganga.consumingwsstore.wsdl.items.ConsSDTArticulosWebParcialArticulos;
import uy.com.pepeganga.consumingwsstore.wsdl.items.SDTArticulosWebParcial;

public class ItemService extends WebServiceGatewaySupport{
	@Autowired
	ConsumingWebserviceStoreApplication p;
	
	public List<ItemModelResponse> getItemsTemporally(){
		return p.getList();
	}

	public List<ItemModelResponse> getItems() {
		 
		 CargaArticulosExecute request = new CargaArticulosExecute();
		 SDTArticulosWebParcial stdItems = new SDTArticulosWebParcial();
		 ConsSDTArticulosWebParcialArticulos consSTD = new ConsSDTArticulosWebParcialArticulos();
		 stdItems.setParte((byte)9);
		 stdItems.setArticulos(consSTD);		 
		 request.setSdtarticuloswebparcial(stdItems);
		 
		 CargaArticulosExecuteResponse response = (CargaArticulosExecuteResponse) getWebServiceTemplate()
		        .marshalSendAndReceive("http://201.217.140.35/agile15/acargaarticulos.aspx", request);
		  
		 List<ItemModelResponse> responseList = ConvertModels.convetToItemModelList(response.getSdtarticuloswebparcial().getArticulos().getArticulo());
		 return responseList;
  	}
}
