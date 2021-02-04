package uy.com.pepeganga.consumingwsstore.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import uy.com.pepeganga.consumingwsstore.wsdl.purchaseOrders.*;

public class PurchaseOrders extends WebServiceGatewaySupport implements IPurchaseOrders {

    private static final Logger logger = LoggerFactory.getLogger(PurchaseOrders.class);

    public void registerPurchaseOrders(String object){

        CargarPedidoWebPpggExecute request = new CargarPedidoWebPpggExecute();
        SDTPedidoPpggCabezal order = initHead();
        ArrayOfSdtPedidoPpggRenglonesSdtPedPpggRenglon orderList = initDetailsOrders();
        SdtPedidoPpggRespuesta answer = new SdtPedidoPpggRespuesta();

        CargarPedidoWebPpggExecuteResponse response = (CargarPedidoWebPpggExecuteResponse) getWebServiceTemplate()
                .marshalSendAndReceive("http://201.217.140.35/agile/aCargarPedidoWebPpgg.aspx", request);



    }

    private SDTPedidoPpggCabezal initHead(){
        SDTPedidoPpggCabezal order = new SDTPedidoPpggCabezal();
        return order;
    }

    private ArrayOfSdtPedidoPpggRenglonesSdtPedPpggRenglon initDetailsOrders(){

        SdtPedidoPpggRenglonesSdtPedPpggRenglon detailsOrder = new SdtPedidoPpggRenglonesSdtPedPpggRenglon();

        ArrayOfSdtPedidoPpggRenglonesSdtPedPpggRenglon orderList = new ArrayOfSdtPedidoPpggRenglonesSdtPedPpggRenglon();
        orderList.getSdtPedidoPpggRenglonesSdtPedPpggRenglon().add(detailsOrder);
        return orderList;
    }

}
