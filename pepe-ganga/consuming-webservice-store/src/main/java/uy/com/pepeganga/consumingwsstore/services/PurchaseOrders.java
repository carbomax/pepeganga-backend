package uy.com.pepeganga.consumingwsstore.services;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import uy.com.pepeganga.business.common.entities.MeliOrders;
import uy.com.pepeganga.business.common.models.MeliOrderItemDto;
import uy.com.pepeganga.business.common.models.OrderDto;
import uy.com.pepeganga.business.common.utils.date.DateTimeUtilsBss;
import uy.com.pepeganga.consumingwsstore.repositories.IMeliOrderRepository;
import uy.com.pepeganga.consumingwsstore.wsdl.purchaseOrders.*;

import javax.xml.datatype.DatatypeConfigurationException;
import java.util.ArrayList;
import java.util.List;

@Service
public class PurchaseOrders extends WebServiceGatewaySupport implements IPurchaseOrders {

    @Autowired
    IMeliOrderRepository meliOrderRepo;

    private static final Logger logger = LoggerFactory.getLogger(PurchaseOrders.class);

    @Override
    public void registerPurchaseOrders(List<OrderDto> ordersDto){

        CargarPedidoWebPpggExecute request = new CargarPedidoWebPpggExecute();
        List<String> ordersToUpdate = new ArrayList<>();
        List<String> ordersFails = new ArrayList<>();

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

                    if(response.getSdtpedidoppggrespuesta().getOk() == "S"){
                        ordersToUpdate.add(String.valueOf(orderDto.getOrderId()) );
                    }else{
                        ordersFails.add(String.valueOf(orderDto.getOrderId()));
                    }
                }catch (Exception e){
                    logger.error(String.format("Error Enviando Peticion de orden al servicio, Método: registerPurchaseOrders(), Msg: %s, Error:  ", e.getMessage()), e);
                    ordersFails.add(String.valueOf(orderDto.getOrderId()));
                }
            } catch (DatatypeConfigurationException e) {
                logger.error(String.format("Error parsiando datos de la Orden con Id: %s, Metodo: initHead(), Msg: %s, Error: ", orderDto.getOrderId(), e.getMessage()), e);
            }

        });
        //Se envió bien
        if(!ordersToUpdate.isEmpty()) {
            meliOrderRepo.updateFieldSentToERPToAll(ordersToUpdate, 1, (short) 0);
        }
        //Falló al enviar
        if(!ordersFails.isEmpty()){
            List<MeliOrders> meliOrdersList = meliOrderRepo.findAllByOrderIds(ordersFails);
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
