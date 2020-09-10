package uy.com.pepeganga.consuming_ws_store.services;

import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import uy.com.pepeganga.consuming_ws_store.wsdl.items.CargaArticulosExecute;
import uy.com.pepeganga.consuming_ws_store.wsdl.items.CargaArticulosExecuteResponse;
import uy.com.pepeganga.consuming_ws_store.wsdl.items.ConsSDTArticulosWebParcialArticulos;
import uy.com.pepeganga.consuming_ws_store.wsdl.items.SDTArticulosWebParcial;

public class ItemService extends WebServiceGatewaySupport{

	public CargaArticulosExecuteResponse getItems() {
		 
		 CargaArticulosExecute request = new CargaArticulosExecute();
		 SDTArticulosWebParcial stdItems = new SDTArticulosWebParcial();
		 ConsSDTArticulosWebParcialArticulos consSTD = new ConsSDTArticulosWebParcialArticulos();
		 stdItems.setParte((byte)9);
		 stdItems.setArticulos(consSTD);
		 
		 request.setSdtarticuloswebparcial(stdItems);
		 CargaArticulosExecuteResponse response = (CargaArticulosExecuteResponse) getWebServiceTemplate()
		        .marshalSendAndReceive("http://201.217.140.35/agile15/acargaarticulos.aspx", request);
		    return response;
		  }
}
