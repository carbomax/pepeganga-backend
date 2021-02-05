package uy.com.pepeganga.consumingwsstore.services;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import uy.com.pepeganga.business.common.models.MeliOrderItemDto;
import uy.com.pepeganga.business.common.models.OrderDto;
import uy.com.pepeganga.business.common.utils.date.DateTimeUtilsBss;
import uy.com.pepeganga.consumingwsstore.repositories.IMeliOrderRepository;
import uy.com.pepeganga.consumingwsstore.wsdl.purchaseOrders.*;

import javax.xml.datatype.DatatypeConfigurationException;
import java.util.ArrayList;
import java.util.List;

public class PurchaseOrders extends WebServiceGatewaySupport implements IPurchaseOrders {

    @Autowired
    IMeliOrderRepository meliOrderRepo;

    private static final Logger logger = LoggerFactory.getLogger(PurchaseOrders.class);

    @Override
    public void registerPurchaseOrders(List<OrderDto> ordersDto){

        CargarPedidoWebPpggExecute request = new CargarPedidoWebPpggExecute();
        List<String> ordersToUpdate = new ArrayList<>();

        ordersDto.forEach(orderDto -> {

            SDTPedidoPpggCabezal order = null;
            try {
                order = initHead(orderDto);
            } catch (DatatypeConfigurationException e) {
                e.printStackTrace();
            }

            ArrayOfSdtPedidoPpggRenglonesSdtPedPpggRenglon orderList = initDetailsOrders(orderDto.getItems());
            SdtPedidoPpggRespuesta answer = new SdtPedidoPpggRespuesta();

            request.setSdtpedidoppggcabezal(order);
            request.setSdtpedidoppggrenglones(orderList);
            request.setSdtpedidoppggrespuesta(answer);

            CargarPedidoWebPpggExecuteResponse response = (CargarPedidoWebPpggExecuteResponse) getWebServiceTemplate()
                    .marshalSendAndReceive("http://201.217.140.35/agile/aCargarPedidoWebPpgg.aspx", request);

            if(response.getSdtpedidoppggrespuesta().getOk() == "S"){
                ordersToUpdate.add(String.valueOf(orderDto.getOrderId()) );
            }

        });

        if(!ordersToUpdate.isEmpty()) {
            meliOrderRepo.updateFieldSentToERPToAll(ordersToUpdate, 1);
        }

    }

    private SDTPedidoPpggCabezal initHead(OrderDto orderDto) throws DatatypeConfigurationException {
        SDTPedidoPpggCabezal order = new SDTPedidoPpggCabezal();
        order.setCedula(orderDto.getCi());
        order.setClienteId((short) orderDto.getSellerId());
        order.setClienteNombre(orderDto.getSellerName());
        order.setIdPedido(Math.toIntExact(orderDto.getOrderId()));
        order.setDepartamento(orderDto.getDepartment());
        order.setDireccion(orderDto.getAddress());
        order.setEMail(orderDto.getEmail());
        order.setFecha(DateTimeUtilsBss.convertToXMLGregorianCalendar(orderDto.getDate()));
        order.setLocalidad(orderDto.getLocation());
        order.setMoneda((byte) orderDto.getCoin());
        order.setObservaciones(orderDto.getObservation());
        order.setRUT(orderDto.getRut());
        order.setUsuario((short) 117);
        return order;
    }

    private ArrayOfSdtPedidoPpggRenglonesSdtPedPpggRenglon initDetailsOrders(List<MeliOrderItemDto> detailOrderList){

        ArrayOfSdtPedidoPpggRenglonesSdtPedPpggRenglon orderList = new ArrayOfSdtPedidoPpggRenglonesSdtPedPpggRenglon();
        detailOrderList.forEach(order -> {
            SdtPedidoPpggRenglonesSdtPedPpggRenglon detailsOrder = new SdtPedidoPpggRenglonesSdtPedPpggRenglon();
            detailsOrder.setArtId(order.getItemId());
            detailsOrder.setCantidad(order.getQuantity());
            detailsOrder.setDescripcion(order.getDescription());
            detailsOrder.setObservaciones(order.getObservations());
            detailsOrder.setPrecio(order.getPrice());
            orderList.getSdtPedidoPpggRenglonesSdtPedPpggRenglon().add(detailsOrder);
        });
        return orderList;
    }


}
