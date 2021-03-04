package uy.com.pepeganga.consumingwsstore.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import uy.com.pepeganga.business.common.entities.MeliOrders;
import uy.com.pepeganga.business.common.models.MeliOrderItemDto;
import uy.com.pepeganga.business.common.models.OrderDto;
import uy.com.pepeganga.business.common.models.ReasonResponse;
import uy.com.pepeganga.business.common.utils.date.DateTimeUtilsBss;
import uy.com.pepeganga.consumingwsstore.repositories.IMeliOrderRepository;
import uy.com.pepeganga.consumingwsstore.wsdl.purchaseOrders.*;

import javax.xml.datatype.DatatypeConfigurationException;
import java.util.ArrayList;
import java.util.List;

public class PurchaseOrdersService extends WebServiceGatewaySupport implements IPurchaseOrdersService {

    @Autowired
    IMeliOrderRepository meliOrderRepo;

    private static final Logger logger = LoggerFactory.getLogger(PurchaseOrdersService.class);

    @Override
    public List<ReasonResponse> registerPurchaseOrders(List<OrderDto> ordersDto){

        List<ReasonResponse> result = new ArrayList<>();
        CargarPedidoWebPpggExecute request = new CargarPedidoWebPpggExecute();
        List<Long> ordersToUpdate = new ArrayList<>();
        List<Long> ordersFails = new ArrayList<>();

        ordersDto.forEach(orderDto -> {

            SDTPedidoPpggCabezal order = null;
            try {
                order = initHead(orderDto);
                ArrayOfSdtPedidoPpggRenglonesSdtPedPpggRenglon orderList = initDetailsOrders(orderDto.getItems());
                SdtPedidoPpggRespuesta answer = new SdtPedidoPpggRespuesta();

                request.setSdtpedidoppggcabezal(order);
                request.setSdtpedidoppggrenglones(orderList);
                request.setSdtpedidoppggrespuesta(answer);

                try{
                    CargarPedidoWebPpggExecuteResponse response = (CargarPedidoWebPpggExecuteResponse) getWebServiceTemplate()
                            .marshalSendAndReceive("http://201.217.140.35/agile/aCargarPedidoWebPpgg.aspx", request);

                    if(response.getSdtpedidoppggrespuesta().getOk().toUpperCase().trim().equals("S") ){
                        ordersToUpdate.add(orderDto.getOrderId() );
                        result.add(new ReasonResponse(true, " {\"orderId\": " + orderDto.getOrderId() + ", \"message\":\"Enviado\"} "));
                        logger.info("Orden con Id: {} procesado correctamente", orderDto.getOrderId());
                    }else{
                        ordersFails.add(orderDto.getOrderId());
                        result.add(new ReasonResponse(false, " { \"orderId\":" + orderDto.getOrderId() + ", \"message\":\"" + response.getSdtpedidoppggrespuesta().getMensajeError() + " \"}"));
                        logger.warn(String.format("Orden con ID: %d devolvió respuesta incorrecta, MSG: %s ", orderDto.getOrderId(), response.getSdtpedidoppggrespuesta().getMensajeError()));
                    }
                }catch (Exception e){
                    logger.error(String.format("Error Enviando Peticion de orden al servicio, Método: registerPurchaseOrders(), Msg: %s, Error:  ", e.getMessage()), e);
                    ordersFails.add(orderDto.getOrderId());
                    result.add(new ReasonResponse(false, "{orderId:" + orderDto.getOrderId() + "," +
                            "message:" + e.getMessage() + "}"));
                }
            } catch (DatatypeConfigurationException e) {
                logger.error(String.format("Error parsiando datos de la Orden con Id: %s, Método: initHead(), Msg: %s, Error: ", orderDto.getOrderId(), e.getMessage()), e);
                result.add(new ReasonResponse(false, "{orderId:" + orderDto.getOrderId() + "," +
                        "message:" + e.getMessage() + "}"));
            }

        });
        //Se envió bien
        if(!ordersToUpdate.isEmpty()) {
            meliOrderRepo.updateFieldSentToERPToAll(ordersToUpdate, 1, (short) 0);
        }
        //Falló al enviar
        if(!ordersFails.isEmpty()){
            List<MeliOrders> meliOrdersList = meliOrderRepo.findAllById(ordersFails);
            if(meliOrdersList != null || !meliOrdersList.isEmpty() ){
                meliOrdersList.forEach(mo -> {
                    if(mo.getSentToErp() != 2) { //Si no tienen los 3 intentos agotados
                        if (mo.getCountFails() == 2) { //llegó a 3 intentos de falla
                            mo.setCountFails((short) (mo.getCountFails() + 1));
                            mo.setSentToErp(2);
                        }
                        else if(mo.getCountFails() < 2){
                            mo.setCountFails((short) (mo.getCountFails() + 1));
                            mo.setSentToErp(0);
                        }
                    }
                });
                meliOrderRepo.saveAll(meliOrdersList);
            }
        }

        return result;
    }

    private SDTPedidoPpggCabezal initHead(OrderDto orderDto) throws DatatypeConfigurationException {
        SDTPedidoPpggCabezal order = new SDTPedidoPpggCabezal();
        order.setCedula(orderDto.getCi());
        order.setClienteId(Short.parseShort(String.valueOf(orderDto.getSellerId()) ) );
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
            detailsOrder.setArtId(order.getSellerSKU());
            detailsOrder.setCantidad(order.getQuantity());
            detailsOrder.setDescripcion(order.getDescription());
            detailsOrder.setObservaciones(order.getObservations());
            detailsOrder.setPrecio(order.getPrice());
            orderList.getSdtPedidoPpggRenglonesSdtPedPpggRenglon().add(detailsOrder);
        });
        return orderList;
    }


}
