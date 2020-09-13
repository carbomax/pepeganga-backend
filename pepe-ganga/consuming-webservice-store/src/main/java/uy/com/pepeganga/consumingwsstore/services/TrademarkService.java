package uy.com.pepeganga.consumingwsstore.services;

import org.springframework.ws.client.core.support.WebServiceGatewaySupport;

import uy.com.pepeganga.consumingwsstore.wsdl.marcas.CargaMarcasExecute;
import uy.com.pepeganga.consumingwsstore.wsdl.marcas.CargaMarcasExecuteResponse;

public class TrademarkService extends WebServiceGatewaySupport {

	public CargaMarcasExecuteResponse getTrademarks() {
		 
		 CargaMarcasExecute request = new CargaMarcasExecute();		 
		 CargaMarcasExecuteResponse response = (CargaMarcasExecuteResponse) getWebServiceTemplate()
		        .marshalSendAndReceive("http://201.217.140.35/agile/acargamarcas.aspx", request);
		    return response;
		  }
}
